package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author Vladimir Krekic
 */

class Nodes {

    private static String path;
    private static String pathS;
    static Label pathL;
    static Label regex1L;
    static Label regex2L;
    static Label regex3L;
    static Label fileFilter;
    static Label pathLS;
    static TextField dirPath;
    static TextField fileFilterT;
    static TextField dirPathS;
    static Button dirButton;
    static Button dirButtonS;
    static Button button;
    static ComboBox<String> regex1C;
    static ComboBox<String> regex2C;
    static ComboBox<String> regex3C;
    private static ObservableList<String> regexList = FXCollections.observableArrayList();

    static void generateNodes() throws FileNotFoundException {

        loadRegexList();

        pathL = new Label("Path: ");
        formatLabel(pathL);

        dirPath = new TextField();
        dirPath.setMinWidth(300);

        dirButton = new Button("Choose folder");
        dirButton.setMinWidth(100);
        dirButton.setAlignment(Pos.CENTER);
        dirButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            path = selectedDirectory.getAbsolutePath();
            dirPath.setText(path);
        });

        regex1L = new Label("Regx 1: ");
        formatLabel(regex1L);

        regex1C = new ComboBox<>();
        formatCBox(regex1C, regexList);

        regex2L = new Label("Regx 2: ");
        formatLabel(regex2L);

        regex2C = new ComboBox<>();
        formatCBox(regex2C, regexList);

        regex3L = new Label("Regx 3: ");
        formatLabel(regex3L);

        regex3C = new ComboBox<>();
        formatCBox(regex3C, regexList);

        fileFilter = new Label("File filter: ");
        formatLabel(fileFilter);

        fileFilterT = new TextField();
        fileFilterT.setText(".*");
        fileFilterT.setMinWidth(100);

        pathLS = new Label("Path: ");
        formatLabel(pathLS);

        dirPathS = new TextField();
        dirPathS.setMinWidth(300);

        dirButtonS = new Button("Choose Save folder");
        dirButtonS.setMinWidth(100);
        dirButtonS.setAlignment(Pos.CENTER);
        dirButtonS.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            //.xls so excel will open it even if it is not regular excel file but tab separated values file
            fileChooser.setInitialFileName("Report.xls");
            File selectedFile = fileChooser.showSaveDialog(null);
            pathS = selectedFile.getAbsolutePath();
            dirPathS.setText(pathS);
        });


        button = new Button("Search and report");
        button.setMinWidth(100);
        button.setAlignment(Pos.CENTER);
        button.setOnAction(event -> {
            Path directory = FileSystems.getDefault().getPath(path);
            String[] regArray= {regex1C.getSelectionModel().getSelectedItem(),
                                regex2C.getSelectionModel().getSelectedItem(),
                                regex3C.getSelectionModel().getSelectedItem()};
            try {
                addNewRegex(regArray);
            } catch (FileNotFoundException e) {
                System.out.println("addNewRegex() " + e.getMessage());;
            }
            try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory)) {
                for (Path file : contents) {
                    //Filters files by specific regex for file names
                    if (file.getFileName().toString().matches(fileFilterT.getText())) {
                        IOController.load(file.toString(),regArray);
                    }
                }
            } catch (IOException e) {
                System.out.println("No directory Err " + e.getMessage());
            }

            Main.window.close();
            try {
                IOController.save(pathS);
            } catch (FileNotFoundException e) {
                System.out.println("File not found " + e.getMessage());
            }
        });
    }

    /**
     * Common formatting fro Combo Boxes
     * @param cBox - ComboBox<String>
     * @param regexList - ObservableList<String>: ComboBox content
     */
    private static void formatCBox(ComboBox<String> cBox, ObservableList<String> regexList){
        cBox.setEditable(true);
        cBox.getItems().addAll(regexList);
        cBox.setVisibleRowCount(5);
        cBox.getSelectionModel().selectFirst();
    }

    /**
     * Common formating for Labels
     * @param label - Label
     */
    private static void formatLabel(Label label){
        label.setMinWidth(100);
        label.setAlignment(Pos.CENTER_RIGHT);
    }

    /**
     * Reads ComboBox content from file into a ObservableList<String>
     */
    private static void loadRegexList() throws FileNotFoundException {
        File input = new File("regexFile.txt");
        Scanner in = new Scanner(input);
        while (in.hasNext()) {
            regexList.add(in.next());
        }
    }

    /**
     * Adds new regex in regexList if added in ComboBox
     * @param regArray - array of chosen regex from all three ComboBoxes
     */
    private static void addNewRegex(String[] regArray) throws FileNotFoundException {
        for(String reg : regArray){
            if(!regexList.contains(reg))
                regexList.add(1, reg);
        }
        saveRegexList(regexList);
    }

    /**
     * Saves regexList into a txt file
     * @param regexList - updated regexList
     */
    private static void saveRegexList(ObservableList<String> regexList) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter("regexFile.txt");
        regexList.forEach(p ->
            pw.print(p + " "));
        pw.close();
    }
}
