package app.dryden.sudoku;

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


    public static int randonNumBetween(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }
    public static boolean getRandomBool(){
        return random.nextBoolean();
    }


}
