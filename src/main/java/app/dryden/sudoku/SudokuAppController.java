package app.dryden.sudoku;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SudokuAppController {

    public VBox boardFrame;

    public static Label currentTile = new Label();
    public static StringProperty[][] boardModel;



    public void initialize(){
        int BOARD_SIZE = 9;

        boardModel = new StringProperty[BOARD_SIZE][BOARD_SIZE];

        buildBoardModel(BOARD_SIZE);
        buildBoard(BOARD_SIZE);

    }

    public void generateNewBoard(){
        SudokuApplication.board.loadNewBoard();
    }

    private void buildBoardModel(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardModel[i][j] = new SimpleStringProperty(String.valueOf(size));
            }
        }
    }


    public void buildBoard(int size){

        if (!Util.isPerfectSquare(size)) throw new IllegalArgumentException("Size must be a perfect square");
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
                String styleString;
                cell.setMinSize(cellWidth, cellHeight);
                cell.textProperty().bind(boardModel[i][j]);
                cell.getStyleClass().add("sudoku-tile");
                cell.textProperty().bind(boardModel[i][j]);
                cell.setOnMouseClicked(gameTileClickedHandler);
                cell.setId("gameTile" + i + j);


                if((Math.floorDiv(i,subGridCount) + Math.floorDiv(j,subGridCount)) % 2 == 1){//is on an odd subgrid
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

                styleString = "-fx-background-color: " + gridColors[colorIndex] + ";";

                if(i == 0 && j == 0) styleString += "-fx-background-radius: 10 0 0 0;";
                else if(i == 0 && j == size - 1) styleString += "-fx-background-radius: 0 10 0 0;";
                else if(i == size - 1 && j == 0) styleString += "-fx-background-radius: 0 0 0 10;";
                else if(i == size - 1 && j == size - 1) styleString += "-fx-background-radius: 0 0 10 0;";

                cell.setStyle(styleString);
                row.getChildren().add(cell);

            }
        }
    }


    EventHandler<MouseEvent> gameTileClickedHandler = event -> {
        currentTile.getStyleClass().remove("sudoku-tile-editing");
        currentTile = (Label) event.getSource();
        currentTile.getStyleClass().add("sudoku-tile-editing");

    };

    static EventHandler<KeyEvent> keyPressedHandler = event -> {
        String key = event.getCharacter();
        int currentTileRow = Integer.parseInt(currentTile.getId().substring(8,9));
        int currentTileCol = Integer.parseInt(currentTile.getId().substring(9,10));


        if(event.getCharacter().matches("[1-9]")){
            boardModel[currentTileRow][currentTileCol].set(String.valueOf(event.getCode()));
        }
    };

}
