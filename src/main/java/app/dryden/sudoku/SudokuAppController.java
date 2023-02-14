package app.dryden.sudoku;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SudokuAppController {

    public VBox boardFrame;
    public StringProperty[][] boardModel;



    public void initialize(){
        int BOARD_SIZE = 9;

        boardModel = new StringProperty[BOARD_SIZE][BOARD_SIZE];

        buildBoardModel(BOARD_SIZE);
        buildBoard(BOARD_SIZE);
    }

    private void buildBoardModel(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardModel[i][j] = new SimpleStringProperty(String.valueOf(size));
            }
        }
    }

    public void updateBoardModel(int[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                boardModel[i][j].set(String.valueOf(board[i][j]));
            }
        }
    }

    public void buildBoard(int size){

        if (Math.sqrt((double) size) % 1 != 0) throw new IllegalArgumentException("Size must be a perfect square");
        if (size < 4) throw new IllegalArgumentException("Board size must be at least 4");

        int subGridCount = (int) Math.sqrt(size);
        String[] gridColors = new String[]{"#0F1F1E", "#01121A","#291A29","#080307"};

        boardFrame.applyCss();
        boardFrame.layout();

        System.out.println(boardFrame);

        double boardWidth = 378;// boardFrame.getWidth();
        double boardHeight = 378;//boardFrame.getHeight();
        double cellWidth = boardWidth / size;
        double cellHeight = boardHeight / size;

        for (int i = 0; i < size; i++) {
            HBox row = new HBox();
            row.setPrefSize(boardWidth, cellHeight);
            boardFrame.getChildren().add(row);

            for (int j = 0; j < size; j++) {
                Label cell = new Label();
                int colorIndex;
                cell.setMinSize(cellWidth, cellHeight);
                cell.textProperty().bind(boardModel[i][j]);
                cell.getStyleClass().add("sudoku-tile");
                cell.textProperty().bind(boardModel[i][j]);


                if((Math.floorDiv(i,3) + Math.floorDiv(j,3)) % 2 == 1){//is on an odd subgrid
                    if(((i+j) % 2) == 1){//is on an odd tile
                        colorIndex = 0;
                    }else{
                        colorIndex = 1;
                    }
                }else{
                    if(((i+j) % 2) == 1){
                        colorIndex = 2;
                    }else{
                        colorIndex = 3;
                    }
                }

                cell.setStyle("-fx-background-color: " + gridColors[colorIndex] + ";");
                row.getChildren().add(cell);

            }
        }
    }
}