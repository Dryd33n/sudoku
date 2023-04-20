package app.dryden.sudoku;

import java.util.ArrayList;

public class Util {

    public static ArrayList<Integer> generateRandomNumberArray(int size){
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < numbers.size();) {
            result.add(numbers.remove((int) (Math.random() * numbers.size())));
        }

        return result;
    }

   public static Boolean isPerfectSquare(int x)
    {
        int s = (int) Math.sqrt(x);
        return (s*s == x);
    }
}
