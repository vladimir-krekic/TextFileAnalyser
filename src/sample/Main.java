package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * @author Vladimir Krekic
 */

public class Main extends Application {

    static Stage window;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        window = primaryStage;

        GridPane startRoot = new GridPane();
        startRoot.setAlignment(Pos.CENTER);
        startRoot.setHgap(5);
        startRoot.setVgap(15);
        startRoot.setPadding(new Insets(30));
        startRoot.setStyle("-fx-background-color: lightgrey");

        Nodes.generateNodes();

        startRoot.add(Nodes.dirButton, 0, 0);
        startRoot.add(Nodes.pathL, 0, 1);
        startRoot.add(Nodes.dirPath, 1, 1);
        startRoot.add(Nodes.regex1L, 0, 2);
        startRoot.add(Nodes.regex1C, 1, 2);
        startRoot.add(Nodes.regex2L, 0, 3);
        startRoot.add(Nodes.regex2C, 1, 3);
        startRoot.add(Nodes.regex3L, 0, 4);
        startRoot.add(Nodes.regex3C, 1, 4);
        startRoot.add(Nodes.fileFilter, 0, 5);
        startRoot.add(Nodes.fileFilterT, 1, 5);
        startRoot.add(Nodes.dirButtonS, 0, 6);
        startRoot.add(Nodes.pathLS, 0, 7);
        startRoot.add(Nodes.dirPathS, 1, 7);
        startRoot.add(Nodes.button, 0, 8);

        Scene startScene = new Scene(startRoot);
        window.setTitle("Game settings");
        window.setScene(startScene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

