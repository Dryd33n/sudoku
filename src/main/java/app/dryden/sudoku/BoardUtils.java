package app.dryden.sudoku;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;


public class BoardUtils {
    public static final int subgridSize = 3;

    public static LinkedHashMap<String,String[]> boardStyles = new LinkedHashMap<>();


    //---------------------------------------------------------------------------

    //                           BOARD BUILDERS

    //---------------------------------------------------------------------------

    public static int[][] generateFancyBoard(int style){

        return switch (style) {
            case 0 -> generateDiagonal();
            case 1 -> generateFilledSubgrid();
            case 2 -> generateChecker(Util.randomNumBetween(1,9), Util.randomNumBetween(1,9));
            case 3 -> generateEmptyBoard(9, Util.randomNumBetween(1, 9));
            default -> generateEmptyBoard(9, 0);
        };

    }

    public static int[][] generateDiagonal() {
        int[][] board = new int[9][9];
        int[] numbs = new int[]{9,8,7,6,5,4,3,2,1,2,3,4,5,6,7,8,9};
        int j = 8;

        for (int i = 0; i < 9; i++) {
            System.arraycopy(numbs, j,board[i], 0 , 9);
            j--;
        }

        return board;
    }

    public static int[][] generateChecker(int value1, int value2){
        int[][] board = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if((i+j) % 2 == 0) board[i][j] = value1;
                else board[i][j] = value2;
            }
        }

        return board;
    }

    public static int[][] generateFilledSubgrid(){
        int k = 1;
        int[][] board = new int[9][9];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board = fillSubgrid(board, k, i, j);
                k++;
            }
        }

        return board;
    }

    public static int[][] fillSubgrid(int[][] board, int value, int subgridRow, int subgridCol){
        for (int i = subgridRow * 3; i < subgridRow * subgridSize + subgridSize; i++) {
            for (int j = subgridCol * subgridSize; j < subgridCol * subgridSize + subgridSize; j++) {
                board[i][j] = value;
            }
        }

        return board;
    }


    public static int[][] generateEmptyBoard(int size, int value) {
        int[][] board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = value;
            }
        }
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


        for (int j = 0; j < 25; j++) {
            for (int i = 0; i < 3; i++) {

                if(Util.getRandomBool()){
                    baseBoard = shuffleSubgridRows(baseBoard, i);
                    baseBoard = shuffleSubgridColumns(baseBoard, i);
                }else {
                    baseBoard = shuffleSubgridColumns(baseBoard, i);
                    baseBoard = shuffleSubgridRows(baseBoard, i);
                }


            }
        }

        return baseBoard;
    }

    public static int[][] generateUnsolvedBoard(int startingTiles){
        int[][] board = generateSolvedBoard();
        return pokeHoles(board, 81 - startingTiles);
    }


    public static boolean[][] getBoolMatrix(boolean value){
        boolean[][] boolMatrix = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolMatrix[i][j] = value;
            }
        }

        return boolMatrix;
    }



    //---------------------------------------------------------------------------

    //                      BOARD BUILDER HELPER FUNCTIONS

    //---------------------------------------------------------------------------




    public static int[][] pokeHoles(int[][] board, int holeCount){
        ArrayList<Pair<Integer,Integer>> remainderSubgrids = new ArrayList<>();
        int holesPerSubgrid = Math.floorDiv(holeCount,9);
        int remainder = holeCount % 9;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                remainderSubgrids.add(new Pair<>(i,j));
            }
        }

        Collections.shuffle(remainderSubgrids);

        for (int i = remainderSubgrids.size(); i > remainder; i--) {
            remainderSubgrids.remove(i-1);
        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(remainderSubgrids.contains(new Pair<>(i,j))){
                    board = pokeHolesInSubgrid(board,  holesPerSubgrid + 1,i, j);
                }else{
                    board = pokeHolesInSubgrid(board, holesPerSubgrid, i, j);
                }
            }
        }

        return board;
    }

    public static int[][] pokeHolesInSubgrid(int[][] board, int holeCount, int subgridRow, int subgridCol){
        ArrayList<Pair<Integer,Integer>> tileCoordinates = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3; j++) {
                tileCoordinates.add(new Pair<>(i,j));
            }
        }

        Collections.shuffle(tileCoordinates);

        for (int i = 0; i < holeCount; i++) {
            board[tileCoordinates.get(i).getKey() + subgridRow*3][tileCoordinates.get(i).getValue() + subgridCol*3] = 0;
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


    @SuppressWarnings({"DataFlowIssue", "ReassignedVariable"})
    public static int[][] shuffleSubgridRows(int[][] board, int subgridIndex) {
        board = shuffleRow(board, (3 * subgridIndex) + 1, (3 * subgridIndex) + 1 + Util.getRandomCoefficient());
        board = shuffleRow(board, 3 * subgridIndex, (3 * subgridIndex) + 2);

        if (Util.getRandomBool())
            board = shuffleRow(board, (3 * subgridIndex) + 1, (3 * subgridIndex) + 1 + Util.getRandomCoefficient());

        return board;
    }


    @SuppressWarnings({"DataFlowIssue", "ReassignedVariable"})
    public static int[][] shuffleSubgridColumns(int[][] board, int subgridIndex) {
        board = shuffleColumn(board, (3 * subgridIndex) + 1, (3 * subgridIndex) + 1 + Util.getRandomCoefficient());
        board = shuffleColumn(board, 3 * subgridIndex, (3 * subgridIndex) + 2);

        if (Util.getRandomBool())
            board = shuffleColumn(board, (3 * subgridIndex) + 1, (3 * subgridIndex) + 1 + Util.getRandomCoefficient());

        return board;
    }


    static void addThemeColours(){
        boardStyles.put("royal-forest", new String[]{"#0F1F1E", "#01121A", "#291A29", "#080307"});
        boardStyles.put("rusted-car",new String[]{"#1F0F0F", "#1A0101", "#1A2229", "#030508"});
        boardStyles.put("cherry-blossom",new String[]{"#542A54", "#2F0C37", "#163629", "#062F27"});
        boardStyles.put("ocean",new String[]{"#185960", "#0D3545", "#142F2C", "#08200E"});
        boardStyles.put("sunset", new String[]{"#C02222", "#AB0030", "#8D11A6", "#602FC0"});
    }





//---------------------------------------------------------------------------

//                              GETTERS

//---------------------------------------------------------------------------


    static String[] getBoardColours(String theme){
        return switch (theme) {
            case "rusted-car" -> boardStyles.get("rusted-car");
            case "handsworth" -> boardStyles.get("handsworth");
            case "cherry-blossom" -> boardStyles.get("cherry-blossom");
            case "ocean" -> boardStyles.get("ocean");
            case "sunset" -> boardStyles.get("sunset");
            default -> boardStyles.get("royal-forest");
        };
    }


    static LinkedHashMap<String, String[]> getBoardStyles() {
        return boardStyles;
    }


    static String getStyleString(int size, int rowIndex, int colIndex, String theme) {
        String[] gridColors = getBoardColours(theme); // Odd subgrid Even Tile, Odd subgrid Odd Tile, Even Subgrid Odd Tile, Even Subgrid Even Tile.
        int colorIndex;
        int subGridCount = (int) Math.sqrt(size);
        String styleString;

        if((Math.floorDiv(rowIndex, subGridCount) + Math.floorDiv(colIndex, subGridCount)) % 2 == 1){//is on an odd subgrid
            if(((rowIndex + colIndex) % 2) == 1){//is on an odd tile
                colorIndex = 0;
            }else{
                colorIndex = 1;
            }
        }else{
            if(((rowIndex + colIndex) % 2) == 1){
                colorIndex = 2;
            }else{
                colorIndex = 3;
            }
        }

        styleString = "-fx-background-color: " + gridColors[colorIndex] + ";";

        if(rowIndex == 0 && colIndex == 0) styleString += "-fx-background-radius: 10 0 0 0;";
        else if(rowIndex == 0 && colIndex == size - 1) styleString += "-fx-background-radius: 0 10 0 0;";
        else if(rowIndex == size - 1 && colIndex == 0) styleString += "-fx-background-radius: 0 0 0 10;";
        else if(rowIndex == size - 1 && colIndex == size - 1) styleString += "-fx-background-radius: 0 0 10 0;";
        return styleString;
    }


    static int getGetIndexOfStyleName(String styleName){
        return new ArrayList<>(boardStyles.keySet()).indexOf(styleName);
    }


}









