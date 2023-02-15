package app.dryden.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApplication extends Application {



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SudokuApplication.class.getResource("sudoku.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 440, 580);
        scene.setOnKeyTyped(SudokuAppController.keyPressedHandler);


        stage.setResizable(false);
        stage.setTitle("Sudoku#");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}