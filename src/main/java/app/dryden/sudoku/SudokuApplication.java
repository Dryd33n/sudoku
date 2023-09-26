package app.dryden.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SudokuApplication extends Application {

    public static Board board;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SudokuApplication.class.getResource("sudoku.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 440, 580);
        scene.setOnKeyTyped(SudokuAppController.keyPressedHandler);

        board = new Board(1,0);

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png"))));
        stage.setResizable(false);
        stage.setTitle("Sudoku #");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}