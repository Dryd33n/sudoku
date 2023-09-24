package app.dryden.sudoku;

public class Board extends BoardUtils {
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

        sudokuBoard = BoardUtils.generateSolvedBoard();
        updateBoardModel();
    }

    public void loadNewBoard(){
        sudokuBoard = BoardUtils.generateSolvedBoard();
        updateBoardModel();
    }







    //---------------------------------------------------------------------------

    //                        BOARD CHECKING FUNCTIONS

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

    //                        OTHER HELPER FUNCTIONS

    //---------------------------------------------------------------------------

    private Boolean isValid(int rowNumber, int columnNumber, int containsNumber){
        return !rowContains(rowNumber, containsNumber) && !columnContains(columnNumber, containsNumber) && !subgridContains(rowNumber, columnNumber, containsNumber);
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


    public void updateBoardModel(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                SudokuAppController.boardModel[i][j].set(String.valueOf(sudokuBoard[i][j]));
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
