package app.dryden.sudoku;

import java.util.ArrayList;
import java.util.Random;


public class Util {

    static Random random = new Random();


    public static int[][] copyIntMatrix(int[][] matrix){
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int value = matrix[i][j];
                result[i][j] = value;
            }
        }
        return result;
    }


    public static Boolean isPerfectSquare(int x)
    {
        int s = (int) Math.sqrt(x);
        return (s*s == x);
    }


    public static int getRandomCoefficient(){
        return getRandomBool() ? -1 : 1;
    }


    public static boolean getRandomBool(){
        return random.nextBoolean();
    }


    static String[] getBoardColours(String theme){
        switch (theme){
            case "royal-forest":
                return new String[]{"#0F1F1E", "#01121A","#291A29","#080307"};
            case "rusted-car":
                return new String[]{"#1F0F0F", "#1A0101","#1A2229","#030508"};
            case "handsworth":
                return new String[]{"#121233", "#050320","#49401D","#0C0A04"};
            case "cherry-blossom":
                return new String[]{"#542A54","#2F0C37","#163629", "#062F27"};
            case "ocean":
                return new String[]{"#185960","#0D3545","#142F2C","#08200E"};
            case "sunset":
                return new String[]{"#C02222","#AB0030","#8D11A6","#602FC0"};

        }

        return new String[]{"#0F1F1E", "#01121A","#291A29","#080307"};
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
}
