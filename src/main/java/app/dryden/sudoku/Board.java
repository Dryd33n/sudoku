package app.dryden.sudoku;

public class Board {
    protected int[][] sudokuBoard;

    public Board(){
        sudokuBoard = new int[][]
               {{0, 0, 0,  0, 0, 0,  0, 0, 0},
                {0, 0, 0,  0, 0, 0,  0, 0, 0},
                {0, 0, 0,  0, 0, 0,  0, 0, 0},

                {0, 0, 0,  0, 0, 0,  0, 0, 0},
                {0, 0, 0,  0, 0, 0,  0, 0, 0},
                {0, 0, 0,  0, 0, 0,  0, 0, 0},

                {0, 0, 0,  0, 0, 0,  0, 0, 0},
                {0, 0, 0,  0, 0, 0,  0, 0, 0},
                {0, 0, 0,  0, 0, 0,  0, 0, 0}};
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

    public int[][] getBoard(){
        return sudokuBoard;
    }
}
