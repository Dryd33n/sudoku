package app.dryden.sudoku;

public class BoardUtils {
    //BOARD GENERATORS
    public static int[][] generateEmptyBoard(int size, int value) {
        int[][] board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = value;
            }
        }
        return board;
    }

    public static int[][] shuffleRow(int[][] board, int row_a, int row_b) {
        int[] row = board[row_a];

        board[row_a] = board[row_b];
        board[row_b] = row;

        return board;
    }

    public static int[][] shuffleColumn(int[][] board, int column_a, int column_b) {
        int[] column = new int[9];

        for (int i = 0; i < board.length; i++) {
            column[i] = board[i][column_a];
        }

        for (int i = 0; i < board.length; i++) {
            board[i][column_a] = board[i][column_b];
        }

        for (int i = 0; i < board.length; i++) {
            board[i][column_b] = column[i];
        }

        return board;
    }

    public static int[][] shuffleSubgridRows(int[][] board, int subgridIndex) {
        board = shuffleRow(board, (3 * subgridIndex) + 1, (3 * subgridIndex) + 1 + Util.getRandomCoefficient());
        board = shuffleRow(board, 3 * subgridIndex, (3 * subgridIndex) + 2);

        if (Util.getRandomBool())
            board = shuffleRow(board, (3 * subgridIndex) + 1, (3 * subgridIndex) + 1 + Util.getRandomCoefficient());

        return board;
    }

    public static int[][] shuffleSubgridColumns(int[][] board, int subgridIndex) {
        board = shuffleColumn(board, (3 * subgridIndex) + 1, (3 * subgridIndex) + 1 + Util.getRandomCoefficient());
        board = shuffleColumn(board, 3 * subgridIndex, (3 * subgridIndex) + 2);

        if (Util.getRandomBool())
            board = shuffleColumn(board, (3 * subgridIndex) + 1, (3 * subgridIndex) + 1 + Util.getRandomCoefficient());

        return board;
    }

    public static int[][] generateSolvedBoard() {
        int[][] baseBoard = new int[][]{{8, 2, 7, 1, 5, 4, 3, 9, 6},
                                        {9, 6, 5, 3, 2, 7, 1, 4, 8},
                                        {3, 4, 1, 6, 8, 9, 7, 5, 2},
                                        {5, 9, 3, 4, 6, 8, 2, 7, 1},
                                        {4, 7, 2, 5, 1, 3, 6, 8, 9},
                                        {6, 1, 8, 9, 7, 2, 4, 3, 5},
                                        {7, 8, 6, 2, 3, 5, 9, 1, 4},
                                        {1, 5, 4, 7, 9, 6, 8, 2, 3},
                                        {2, 3, 9, 8, 4, 1, 5, 6, 7}};


        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 3; i++) {
                baseBoard = shuffleSubgridRows(baseBoard, i);
                baseBoard = shuffleSubgridColumns(baseBoard, i);
                baseBoard = shuffleSubgridRows(baseBoard, i);
                baseBoard = shuffleSubgridColumns(baseBoard, i);
            }
        }

        return baseBoard;
    }
}
