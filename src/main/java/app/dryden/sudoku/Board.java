package app.dryden.sudoku;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Board extends BoardUtils {

    protected int[][] sudokuBoard;//current sudoku board
    int[][] generatedBoard = BoardUtils.generateEmptyBoard(9,1);//recent board used for reset
    protected boolean[][] protectedTiles = BoardUtils.getBoolMatrix(true);//locked tiles that should not be changeable
    protected int boardSize = 9;
    protected int subgridSize = 3;


    protected int difficulty = 0; // 0 = easy, 1 = medium, 2 = hard
    protected ArrayList<Integer> difficultyConfig = new ArrayList<>(Arrays.asList(35, 25, 15)); // number of tiles to start with for each difficulty






    //---------------------------------------------------------------------------

    //                        CONSTRUCTORS

    //---------------------------------------------------------------------------




    public Board(int value){
        sudokuBoard = generateEmptyBoard(9,value );
        protectedTiles = BoardUtils.getBoolMatrix(false);
        updateBoardModel();
    }

    public Board(){
        sudokuBoard = BoardUtils.generateUnsolvedBoard(difficultyConfig.get(difficulty));
        updateProtectedTiles();
        updateBoardModel();
    }

    public Board(int style, int nothing){
        sudokuBoard = BoardUtils.generateFancyBoard(Util.randomNumBetween(0,3));
        protectedTiles = BoardUtils.getBoolMatrix(false);
        updateBoardModel();
    }








    //---------------------------------------------------------------------------

    //                        BOARD MANAGEMENT FUNCTIONS

    //---------------------------------------------------------------------------






    public void loadNewBoard(){
        sudokuBoard = BoardUtils.generateUnsolvedBoard(difficultyConfig.get(difficulty));
        generatedBoard = Util.copyIntMatrix(sudokuBoard);
        updateProtectedTiles();
        updateBoardModel();
    }

    public void resetBoard(){
        sudokuBoard = generatedBoard;
        generatedBoard = Util.copyIntMatrix(sudokuBoard);
        updateProtectedTiles();
        updateBoardModel();
    }




    //---------------------------------------------------------------------------

    //                        BOARD VALIDATION FUNCTIONS

    //---------------------------------------------------------------------------

    private Boolean isValidBoard(){
        boolean isValid = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!isValidTile(i,j)) isValid = false;
            }
        }

        return isValid;
    }


    private Boolean isValidTile(int rowNumber, int columnNumber){
        boolean isValid = true;
        int tileValue = sudokuBoard[rowNumber][columnNumber];

        if (rowContains(rowNumber, tileValue)) isValid = false;
        if (columnContains(columnNumber, tileValue)) isValid = false;
        if (subgridContains(columnNumber, rowNumber, tileValue)) isValid = false;

        return isValid;
    }


    private Boolean rowContains(int rowNumber, int containsNumber) {
        for(int i=0; i<boardSize; i++) {
            if(sudokuBoard[rowNumber][i] == containsNumber){
                return true;
            }
        }
        return false;
    }


    private Boolean columnContains(int columnNumber, int containsNumber) {
        for (int[] rows : sudokuBoard) {
            if (rows[columnNumber] == containsNumber) {
                return true;
            }
        }
        return false;
    }


    private Boolean subgridContains(int rowNumber, int columnNumber, int containsNumber) {
            int subgridRow = rowNumber / subgridSize;
            int subgridColumn = columnNumber / subgridSize;

            for (int i = subgridRow * subgridSize; i < subgridRow * subgridSize + subgridSize; i++) {
                for (int j = subgridColumn * subgridSize; j < subgridColumn * subgridSize + subgridSize; j++) {
                    if (sudokuBoard[i][j] == containsNumber) {
                        return true;
                    }
                }
            }
            return false;
        }






    //---------------------------------------------------------------------------

    //                        GETTERS AND SETTERS

    //---------------------------------------------------------------------------




    public void setTile(int rowNumber, int columnNumber, int value){
        sudokuBoard[rowNumber][columnNumber] = value;
        SudokuAppController.boardModel[rowNumber][columnNumber].set(String.valueOf(value));
    }


    public Boolean isProtectedTile(int rowNumber, int columnNumber){
        return protectedTiles[rowNumber][columnNumber];
    }


    public ArrayList<Pair<Integer, Integer>> getProtectedTiles(){

        ArrayList<Pair<Integer, Integer>> protectedTilesList = new ArrayList<>();

        for (int i = 0; i < protectedTiles.length; i++) {
            for (int j = 0; j < protectedTiles[0].length; j++) {
                if(protectedTiles[i][j]) protectedTilesList.add(new Pair<>(i,j));
            }
        }

        return protectedTilesList;
    }


    public void setDifficulty(int difficulty){
            this.difficulty = difficulty;
        }






    //---------------------------------------------------------------------------

    //                        OTHER HELPER FUNCTIONS

    //---------------------------------------------------------------------------




    public void updateBoardModel(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if(sudokuBoard[i][j] == 0) SudokuAppController.boardModel[i][j].set("");
                else SudokuAppController.boardModel[i][j].set(String.valueOf(sudokuBoard[i][j]));
            }
        }

        updateProtectedTiles();
    }


    public void updateProtectedTiles(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                protectedTiles[i][j] = sudokuBoard[i][j] != 0;
            }
        }
    }


    public void printBoard(){
        for(int i = 0; i < sudokuBoard.length; i++){
            for(int j = 0; j < sudokuBoard.length; j++){
                System.out.print(sudokuBoard[i][j] + " ");

                if(j == 2 || j == 5){
                    System.out.print("| ");
                }

                if(j == 8){
                    System.out.println();
                }

                if(i == 2 || i == 5){
                    if(j == 8){
                        System.out.println("---------------------");
                    }
                }


            }
        }

    }


    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();

        for (int i = 0; i < sudokuBoard.length; i++) {
            for (int j = 0; j < sudokuBoard.length; j++) {
                boardString.append(sudokuBoard[i][j]).append(" ");
                if((j + 1) % 3 == 0) boardString.append("  ");
            }

            boardString.append("\n");
            if((i + 1) % 3 == 0) boardString.append("\n");
        }

        return boardString.toString();
    }

}
