package app.dryden.sudoku;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SudokuAppController {

    public VBox boardFrame;

    public static Label currentTile = new Label();
    public static StringProperty[][] boardModel;

    public static StringProperty timeString = new SimpleStringProperty("Time: 0:00");

    public static ArrayList<Pair<Integer,Integer>> protectedTilesList = new ArrayList<>();
    public Label sudokuTimer;

    public static int secondsElapsed = 0;


    public void initialize(){
        int BOARD_SIZE = 9;

        boardModel = new StringProperty[BOARD_SIZE][BOARD_SIZE];

        sudokuTimer.textProperty().bind(timeString);

        buildBoardModel(BOARD_SIZE);
        buildBoard(BOARD_SIZE);
    }

    public void generateNewBoard(){
        SudokuApplication.board.loadNewBoard();
        setProtectedTiles();

        Timer timer = new Timer();
        secondsElapsed = 0;
        timer.purge();
        timer.schedule(updateTimerString, 1000L, 1000L);
    }

    private void buildBoardModel(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardModel[i][j] = new SimpleStringProperty(String.valueOf("0"));
            }
        }
    }


    public void buildBoard(int size){

        if (!Util.isPerfectSquare(size)) throw new IllegalArgumentException("Size must be a perfect square");
        if (size < 4) throw new IllegalArgumentException("Board size must be at least 4");

        boardFrame.applyCss();
        boardFrame.layout();

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

                cell.setId("gameTile" + i + j); // set tile id
                cell.setMinSize(cellWidth, cellHeight); //set tile min size
                cell.textProperty().bind(boardModel[i][j]); //bind tile text to board model
                cell.setOnMouseClicked(gameTileClickedHandler); //set click handler
                cell.getStyleClass().add("sudoku-tile");


                cell.setStyle(Util.getStyleString(size, i, j));
                row.getChildren().add(cell);

            }
        }
    }
    
    public void setProtectedTiles(){
        for ( Pair<Integer,Integer> pair: protectedTilesList) {
            Label label = (Label) boardFrame.lookup("#gameTile"+pair.getKey()+pair.getValue());

            label.getStyleClass().remove("sudoku-tile-protected");
        }

        protectedTilesList = SudokuApplication.board.getProtectedTiles();

        for ( Pair<Integer,Integer> pair: protectedTilesList) {
            Label label = (Label) boardFrame.lookup("#gameTile"+pair.getKey()+pair.getValue());

            label.getStyleClass().add("sudoku-tile-protected");
        }
    }


    static TimerTask updateTimerString = new TimerTask() {





        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    secondsElapsed++;

                    int minutes = Math.floorDiv(secondsElapsed, 60);
                    int seconds = secondsElapsed % 60;
                    String secondsOut;

                    if(String.valueOf(seconds).length() == 1) secondsOut = "0" + String.valueOf(seconds);
                    else secondsOut = String.valueOf(seconds);

                    timeString.set("Time: " + minutes + ":" + secondsOut);
                }
            });
        };
    };

    EventHandler<MouseEvent> gameTileClickedHandler = event -> {
        Label newTile = (Label) event.getSource();

        int currentTileRow = Integer.parseInt(newTile.getId().substring(8,9));
        int currentTileCol = Integer.parseInt(newTile.getId().substring(9,10));

        if(SudokuApplication.board.isProtectedTile(currentTileRow,currentTileCol)) return;

        currentTile.getStyleClass().remove("sudoku-tile-editing");
        currentTile = newTile;
        currentTile.getStyleClass().add("sudoku-tile-editing");
    };

    static EventHandler<KeyEvent> keyPressedHandler = event -> {
        String key = event.getCharacter();
        int currentTileRow = Integer.parseInt(currentTile.getId().substring(8,9));
        int currentTileCol = Integer.parseInt(currentTile.getId().substring(9,10));


        if(event.getCharacter().matches("[1-9]")){
           SudokuApplication.board.setTile(currentTileRow,currentTileCol,Integer.parseInt(key));
        }
    };



}
