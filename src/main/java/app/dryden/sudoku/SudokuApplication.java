package app.dryden.sudoku;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static app.dryden.sudoku.SudokuAppController.timeString;

public class SudokuApplication extends Application {

    public static Board board;
    public static SudokuAppController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SudokuApplication.class.getResource("sudoku.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 440, 580);
        scene.setOnKeyTyped(SudokuAppController.keyPressedHandler);

        board = new Board(9,0);
        controller = fxmlLoader.getController();

        stage.setResizable(false);
        stage.setTitle("Sudoku#");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {


        launch();


    }


}