package app.dryden.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
    protected int[][] sudokuBoard;
    protected int boardSize;
    protected int subgridSize;


    public Board(int value, int size){
        boardSize = size;
        subgridSize = (int) Math.sqrt(size);
        sudokuBoard = generateEmptyBoard(value, size);
    }

    public Board(double difficulty, int size){
        boardSize = size;
        subgridSize = (int) Math.sqrt(size);
    }

    public Board(int size){
        subgridSize = (int) Math.sqrt(size);
        boardSize = size;
        int[][] resultBoard = generateEmptyBoard(0, size);

        sudokuBoard = resultBoard;
        System.out.println(this);

        resultBoard = Board.fillSubgridRandom(0,resultBoard);
        resultBoard = Board.fillSubgridRandom(4,resultBoard);
        resultBoard = Board.fillSubgridRandom(8,resultBoard);

        sudokuBoard = resultBoard;
        System.out.println(this);
    }


    /*
    @param rowNumber --> number of row to search starting from the top counting from zero
    @param containsNumber --> the number to check for within the row
    @return returns true if the row contains the number and false otherwise
     */
    Boolean rowContains(int rowNumber, int containsNumber) {
        for(int i=0; i<sudokuBoard[rowNumber].length; i++) {
            if(sudokuBoard[rowNumber][i] == containsNumber){
                return true;
            }
        }
        return false;
    }

    Boolean columnContains(int columnNumber, int containsNumber) {
        for (int[] rows : sudokuBoard) {
            if (rows[columnNumber] == containsNumber) {
                return true;
            }
        }
        return false;
    }

    public static int[][] generateEmptyBoard(int size, int value){
        int[][] board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = value;
            }
        }
        return board;
    }

    Boolean subgridContains(int rowNumber, int columnNumber, int containsNumber) {
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

    Boolean isValid(int rowNumber, int columnNumber, int containsNumber){
        return !rowContains(rowNumber, containsNumber) && !columnContains(columnNumber, containsNumber) && !subgridContains(rowNumber, columnNumber, containsNumber);
    }

    public int[][] getBoard(){
        return sudokuBoard;
    }

    public static int[][] fillSubgridRandom(int subgridNumber, int[][] board){
        //if (subgridNumber < 0 || subgridNumber > board.length-1) throw new IllegalArgumentException("Subgrid number out of range");

        int subgridSize = (int) Math.sqrt(board.length);
        int subgridCurrentCell = 0;
        int[][] resultBoard = new int[board.length][board.length];
        ArrayList<Integer> randomNumString = Util.generateRandomNumberArray(board.length);

        for (int i = Math.floorDiv(subgridNumber, subgridSize)*3; i < Math.floorDiv(subgridNumber,subgridSize)*3 + 3; i++) {
            for (int j = (Math.floorDiv(subgridNumber, subgridSize)-subgridNumber)*3; j < Math.floorDiv(subgridNumber, subgridSize)*3 + 3; j++) {
                resultBoard[i][j] = randomNumString.get(subgridCurrentCell);
                subgridCurrentCell++;
            }
        }

        return resultBoard;
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
