package app.dryden.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
    protected int[][] sudokuBoard;
    protected int boardSize;
    protected int subgridSize;


    //CONSTRUCTORS

    public Board(int value, int size){
        if(!Util.isPerfectSquare(size)) throw new IllegalArgumentException("Size must be a perfect square");
        if(value > size || value < 0) throw new IllegalArgumentException("Value must be between 0 and size");

        boardSize = size;
        subgridSize = (int) Math.sqrt(size);
        sudokuBoard = generateEmptyBoard(value, size);
        updateBoardModel();
    }

    public Board(double difficulty, int size){
        if(!Util.isPerfectSquare(size)) throw new IllegalArgumentException("Size must be a perfect square");

        boardSize = size;
        subgridSize = (int) Math.sqrt(size);
        updateBoardModel();
    }

    public Board(int size){
        if(!Util.isPerfectSquare(size)) throw new IllegalArgumentException("Size must be a perfect square");

        subgridSize = (int) Math.sqrt(size);
        boardSize = size;

        generateNewBoard();
        updateBoardModel();
    }

    //BOARD GENERATORS
    public int[][] generateEmptyBoard(int size, int value){
        int[][] board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = value;
            }
        }
        return board;
    }
    public int[][] generateNewBoard() {
        int[][] baseBoard = generateSolvedBoard();

        return baseBoard;
    }

    private int[][] generateSolvedBoard() {
        int[][] resultBoard = generateEmptyBoard(9, 0);

            fillSubgridRandom(0);
            fillSubgridRandom(4);
            fillSubgridRandom(8);

        for (int i = 0; i < boardSize; i++) {

        }

        return resultBoard;
    }

    public void updateBoardModel(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                SudokuAppController.boardModel[i][j].set(String.valueOf(sudokuBoard[i][j]));
            }
        }
    }

    //BOARD CHECKERS

    /*
    @param rowNumber --> number of row to search starting from the top counting from zero
    @param containsNumber --> the number to check for within the row
    @return returns true if the row contains the number and false otherwise
     */
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

    private Boolean isValid(int rowNumber, int columnNumber, int containsNumber){
        return !rowContains(rowNumber, containsNumber) && !columnContains(columnNumber, containsNumber) && !subgridContains(rowNumber, columnNumber, containsNumber);
    }


    public int[][] fillSubgridRandom(int subgridNumber){
        //if (subgridNumber < 0 || subgridNumber > board.length-1) throw new IllegalArgumentException("Subgrid number out of range");

        int subgridCurrentCell = 0;

        int[][] resultBoard = sudokuBoard;
        ArrayList<Integer> randomNumString = Util.generateRandomNumberArray(boardSize);

        for (int i = Math.floorDiv(subgridNumber, subgridSize)*3; i < Math.floorDiv(subgridNumber,subgridSize)*3 + 3; i++) {
            for (int j = (Math.floorDiv(subgridNumber, subgridSize))*3; j < Math.floorDiv(subgridNumber, subgridSize)*3 + 3; j++) {
                resultBoard[i][j] = randomNumString.get(subgridCurrentCell);
                subgridCurrentCell++;
            }
        }

        return resultBoard;
    }

    public int[][] addRandomNumberToSubgridSquareWithLeastPossibleOptions(int subgridNumber){
        int subgridSquareToFill = subgridSquareWithLeastPossibleOptions(subgridNumber);
    }

    private int subgridSquareWithLeastPossibleOptions(int subgridNumber) {
        int subgridCurrentCell = 0;
        int cellWithLowestOptions = -1;
        HashMap<Integer, Integer> squarePossibilityList = new HashMap<>();

        int[][] resultBoard = generateEmptyBoard(subgridSize, 0);
        ArrayList<Integer> randomNumString = Util.generateRandomNumberArray(boardSize);

        for (int i = Math.floorDiv(subgridNumber, subgridSize)*3; i < Math.floorDiv(subgridNumber,subgridSize)*3 + 3; i++) {
            for (int j = (Math.floorDiv(subgridNumber, subgridSize))*3; j < Math.floorDiv(subgridNumber, subgridSize)*3 + 3; j++) {
                squarePossibilityList.put(subgridCurrentCell, getCellValidNumbers(i, j).size());
            }
        }

        for(Map.Entry<Integer, Integer> entry : squarePossibilityList.entrySet()){
            if(cellWithLowestOptions == -1){
                cellWithLowestOptions = entry.getValue();
            } else if(entry.getValue() < squarePossibilityList.get(cellWithLowestOptions)){
                cellWithLowestOptions = entry.getValue();
            }
        }

        return cellWithLowestOptions;
    }

    public ArrayList<Integer> getCellValidNumbers(int rowNumber, int columnNumber){
        ArrayList<Integer> possibleOptions = new ArrayList<Integer>();
        for (int i = 1; i < sudokuBoard.length+1; i++) {
            if (isValid(rowNumber, columnNumber, i)){
                possibleOptions.add(i);
            }
        }
        return possibleOptions;
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
