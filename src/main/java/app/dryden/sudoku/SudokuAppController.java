package app.dryden.sudoku;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class SudokuAppController {



    public static Label currentTile = new Label();
    public static StringProperty[][] boardModel;

    public static StringProperty completionString = new SimpleStringProperty("Completion: 0/81");

    public static Boolean completionShown = true;

    public static ArrayList<Pair<Integer,Integer>> protectedTilesList = new ArrayList<>();

    public ArrayList<MenuItem> themeSelectorButtons = new ArrayList<>();

    public static StringProperty timeString = new SimpleStringProperty("Time: 0:00");
    public static Boolean timeShown = true;
    public Label completionLabel;
    public AnchorPane mainPane;
    public Label openSolverButtonLabel;
    public static Boolean solverShown = false;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static int secondsElapsed = 0;

    public VBox boardFrame;
    public AnchorPane settingsPanel;
    public Pane difficultyButton0;
    public Pane difficultyButton1;
    public Pane difficultyButton2;
    public MenuButton themeMenuButton;
    public Label sudokuTimer;





    //---------------------------------------------------------------------------

    //                            BOARD CONTROL

    //---------------------------------------------------------------------------



    public void generateNewBoard(){
        SudokuApplication.board.loadNewBoard();
        BoardUtils.updateCompletion();
        setProtectedTiles();
        startTimer();
    }


    public void resetBoard(){
        SudokuApplication.board.resetBoard();
        BoardUtils.updateCompletion();
        setProtectedTiles();
        startTimer();
    }






    //---------------------------------------------------------------------------

    //                      USER INTERFACE CONSTRUCTION

    //---------------------------------------------------------------------------

    public void initialize(){
        int BOARD_SIZE = 9;

        boardModel = new StringProperty[BOARD_SIZE][BOARD_SIZE];

        sudokuTimer.textProperty().bind(timeString);
        completionLabel.textProperty().bind(completionString);

        BoardUtils.addThemeColours();
        buildThemeMenu("royal-forest");

        closeSettingsPanel();

        buildBoardModel(BOARD_SIZE);
        buildBoard(BOARD_SIZE);
    }


    private void buildBoardModel(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardModel[i][j] = new SimpleStringProperty("0");
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


                cell.setStyle(BoardUtils.getStyleString(size, i, j, "royal-forest"));
                row.getChildren().add(cell);

            }
        }
    }


    public void reSkinBoard(String theme){
        buildThemeMenu(theme);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Label cell = (Label) boardFrame.lookup("#gameTile" + i + j);
                String styleString = BoardUtils.getStyleString(9, i, j, theme);
                cell.setStyle(styleString);
            }
        }
    }


    public void setProtectedTiles(){
        for ( Pair<Integer,Integer> pair: protectedTilesList) {//remove styling of old protected tiles
            Label label = (Label) boardFrame.lookup("#gameTile"+pair.getKey()+pair.getValue());

            label.getStyleClass().remove("sudoku-tile-protected");
        }

        protectedTilesList = SudokuApplication.board.getProtectedTiles();//get updated list of protected tiles

        for ( Pair<Integer,Integer> pair: protectedTilesList) {//add styling to new protected tiles
            Label label = (Label) boardFrame.lookup("#gameTile"+pair.getKey()+pair.getValue());

            label.getStyleClass().add("sudoku-tile-protected");
        }
    }


    public void buildThemeMenu(String theme){
        themeMenuButton.getItems().clear();
        themeSelectorButtons.clear();

        for (int i = 0; i < BoardUtils.getBoardStyles().size(); i++) {
            String themeName = (String) BoardUtils.getBoardStyles().keySet().toArray()[i];
            int SelectedIndex = BoardUtils.getGetIndexOfStyleName(theme);
            MenuItem item = new MenuItem(themeName);



            if(i == SelectedIndex) item.getStyleClass().add("theme-button-selected");
            if(i == 0) item.getStyleClass().add("first");
            if(i == BoardUtils.getBoardStyles().size()-1) item.getStyleClass().add("last");

            item.setOnAction(event -> reSkinBoard(themeName));
            item.getStyleClass().add("themeButton");
            item.setId("themeSelectButton"+i);

            themeMenuButton.getItems().add(item);
            themeSelectorButtons.add(item);
        }
    }






    //---------------------------------------------------------------------------

    //               USER INTERFACE COMPONENT ( SETTINGS MENU )

    //---------------------------------------------------------------------------




    public void openSettingsPanel(){
        settingsPanel.setDisable(false);
        settingsPanel.setVisible(true);
    }


    public void closeSettingsPanel(){
        settingsPanel.setDisable(true);
        settingsPanel.setVisible(false);
    }


    public void setDifficultyEasy(){
        setDifficulty(0);
    }


    public void setDifficultyMedium(){
        setDifficulty(1);
    }


    public void setDifficultyHard(){
        setDifficulty(2);
    }


    public void setDifficulty(int difficulty){

        difficultyButton0.getStyleClass().clear();
        difficultyButton1.getStyleClass().clear();
        difficultyButton2.getStyleClass().clear();


        difficultyButton0.getStyleClass().add("gameButton");
        difficultyButton1.getStyleClass().add("gameButton");
        difficultyButton2.getStyleClass().add("gameButton");




        switch (difficulty) {
            case (0) -> {
                difficultyButton0.getStyleClass().remove("gameButton");
                difficultyButton0.getStyleClass().add("difficulty-button-selected");
                SudokuApplication.board.setDifficulty(0);
            }
            case (1) -> {
                difficultyButton1.getStyleClass().remove("gameButton");
                difficultyButton1.getStyleClass().add("difficulty-button-selected");
                SudokuApplication.board.setDifficulty(1);
            }
            case (2) -> {
                difficultyButton2.getStyleClass().remove("gameButton");
                difficultyButton2.getStyleClass().add("difficulty-button-selected");
                SudokuApplication.board.setDifficulty(2);
            }
            default -> {
            }
        }
    }


    public void toggleTimerVisibility(){
        sudokuTimer.setVisible(!timeShown);

        timeShown = !timeShown;
    }


    public void toggleCompletionVisibility(){
        completionLabel.setVisible(!completionShown);

        completionShown = !completionShown;
    }





    //---------------------------------------------------------------------------

    //               USER INTERFACE COMPONENT ( SOLVER )

    //---------------------------------------------------------------------------

    public void toggleSolverMenu(){
        if(solverShown) closeSolverMenu();
        else openSolverMenu();
    }


    public void openSolverMenu(){
        final int newStageWidth = 456;
        final int newStageHeight = 687+40;

        SudokuApplication.resizeStage(newStageWidth,newStageHeight);
        mainPane.setPrefSize(newStageWidth,newStageHeight);
        openSolverButtonLabel.setText("⮝ Close Solver ⮝");
        solverShown = true;
    }

    public void closeSolverMenu(){
        final int newStageWidth = 456;
        final int newStageHeight = 687+40-100;

        SudokuApplication.resizeStage(newStageWidth,newStageHeight);
        mainPane.setPrefSize(newStageWidth,newStageHeight);
        openSolverButtonLabel.setText("⮟  Open Solver  ⮟");
        solverShown = false;
    }



    //---------------------------------------------------------------------------

    //                              TIMER

    //---------------------------------------------------------------------------




    public void startTimer(){
        scheduler.shutdown();
        scheduler = Executors.newScheduledThreadPool(1);

        secondsElapsed = 0;
        timeString.set("Time: 0:00");

        scheduler.scheduleAtFixedRate(new subTimer(), 1000L, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    private static class subTimer extends TimerTask {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    secondsElapsed++;

                    int minutes = Math.floorDiv(secondsElapsed, 60);
                    int seconds = secondsElapsed % 60;
                    String secondsOut;

                    if (String.valueOf(seconds).length() == 1) secondsOut = "0" + seconds;
                    else secondsOut = String.valueOf(seconds);

                    timeString.set("Time: " + minutes + ":" + secondsOut);
                });
            }
    }






    //---------------------------------------------------------------------------

    //                          EVENT HANDLERS

    //---------------------------------------------------------------------------





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

        System.out.println(event.getCharacter());

        if(event.getCharacter().matches("[1-9]")){
           SudokuApplication.board.setTile(currentTileRow,currentTileCol,Integer.parseInt(key));
        }else {
            SudokuApplication.board.setTile(currentTileRow,currentTileCol,0);
        }

         BoardUtils.updateCompletion();
     };



}
