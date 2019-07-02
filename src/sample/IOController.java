package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

/**
 * As simple as possible IO Classes
 * @author Vladimir Krekic
 */

class IOController {

    private static ObservableList<FileContent> list = FXCollections.observableArrayList();

    static void load(String path, String[] regex) throws FileNotFoundException {

        FileContent fileContent = new FileContent();
        File input = new File(path);
        Scanner in = new Scanner(input);

        fileContent.setFileName(input.getName().substring(0, input.getName().indexOf('.')));

        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.isEmpty())
                continue;
            String[] strings = line.split("[ .,;:()!?/\\-*=\\[\\]]"); //All of the characters that I don't want in my search
            for (String key : strings) {
                //Filtering and counting searched sequences in files
                if (key.matches(regex[0]) || key.matches(regex[1]) || key.matches(regex[2])) {
                    if (fileContent.getSearchedSequence().containsKey(key)) {
                        fileContent.getSearchedSequence().put(key, fileContent.getSearchedSequence().get(key) + 1);
                    } else {
                        fileContent.getSearchedSequence().put(key, 1);
                    }
                }
            }
        }

        if (!fileContent.getSearchedSequence().isEmpty()) {
            list.add(fileContent);
        }
    }

    static void save(String pathS) throws FileNotFoundException {

        PrintWriter pw = new PrintWriter(pathS);

        pw.print("Stored procedure name" + "\t" + "Searched sequence" + "\t" + "No. of appearances" + "\n"); //Column names
        list.forEach(p -> {
            //tab separated values
            pw.print(p.getFileName() + "\t" + "\n");
            p.getSearchedSequence().forEach((k, v) ->
                    pw.print("\t" + k + "\t" + v + "\n"));
        });

        pw.close();
    }
}
