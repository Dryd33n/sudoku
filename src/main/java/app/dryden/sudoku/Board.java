package app.dryden.sudoku;

public class Board extends BoardUtils {
    protected int[][] sudokuBoard;
    protected boolean[][] protectedTiles = BoardUtils.getBoolMatrix(true);
    protected int boardSize;
    protected int subgridSize;




    //---------------------------------------------------------------------------

    //                        CONSTRUCTORS

    //---------------------------------------------------------------------------




    public Board(int value, int size){
        if(!Util.isPerfectSquare(size)) throw new IllegalArgumentException("Size must be a perfect square");
        if(value > size || value < 0) throw new IllegalArgumentException("Value must be between 0 and size");

        boardSize = size;
        subgridSize = (int) Math.sqrt(size);
        sudokuBoard = generateUnsolvedBoard(10);
        protectedTiles = BoardUtils.getBoolMatrix(false);
        updateBoardModel();
    }

    public Board(int size){
        if(!Util.isPerfectSquare(size)) throw new IllegalArgumentException("Size must be a perfect square");

        subgridSize = (int) Math.sqrt(size);
        boardSize = size;

        sudokuBoard = BoardUtils.generateUnsolvedBoard(15);
        getProtectedTiles();
        updateBoardModel();
    }


    public void loadNewBoard(){
        sudokuBoard = BoardUtils.generateUnsolvedBoard(15);
        getProtectedTiles();
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

    public Boolean isProtectedTile(int rowNumber, int columnNumber){
        return protectedTiles[rowNumber][columnNumber];
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

        getProtectedTiles();
    }

    public void getProtectedTiles(){
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
