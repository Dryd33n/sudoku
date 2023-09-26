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


    static String getStyleString(int size, int rowIndex, int colIndex) {
        String[] gridColors = new String[]{"#0F1F1E", "#01121A","#291A29","#080307"};
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
