package app.dryden.sudoku;

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

    public int[][] shuffleRow(int[][] board, int row_a, int row_b){
        int[] row = board[row_a];

        board[row_a] = board[row_b];
        board[row_b] = board[row_a];

        return board;
    }

    public int[][] shuffleColumn(int[][] board)
    public int[][] generateNewBoard() {
        int[][] baseBoard = new int[][]{{8 ,2 ,7 ,1 ,5 ,4 ,3 ,9 ,6 },
                                        {9 ,5 ,5 ,3 ,2 ,7 ,1 ,4 ,8 },
                                        {3 ,4 ,1 ,6 ,8 ,9 ,7 ,5 ,2 },
                                        {5 ,9 ,3 ,4 ,6 ,8 ,2 ,7 ,1 },
                                        {4 ,7 ,2 ,5 ,1 ,3 ,6 ,8 ,9 },
                                        {6 ,1 ,8 ,9 ,7 ,2 ,4 ,3 ,5 },
                                        {7 ,8 ,6 ,2 ,3 ,5 ,9 ,1 ,4 },
                                        {1 ,5 ,4 ,7 ,9 ,6 ,8 ,2 ,3 },
                                        {2 ,3 ,9 ,8 ,4 ,1 ,5 ,6 ,7 }};

        return baseBoard;
    }

    private boolean generateSolvedBoard() {
        int[][] solvedBoard = generateEmptyBoard(9,0);


        return resultBoard;
    }


    public void updateBoardModel(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                SudokuAppController.boardModel[i][j].set(String.valueOf(sudokuBoard[i][j]));
            }
        }
    }

    //---------------------------------------------------------------------------

    //                        BOARD CHECKING FUNCTIONS

    //---------------------------------------------------------------------------

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
