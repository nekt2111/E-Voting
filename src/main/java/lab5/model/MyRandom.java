package lab5.model;

import java.util.Arrays;
import java.util.Random;

public class MyRandom {
    private static Random random = new Random();


    public static int[] getRandomMultipliers(int number) {
        int randomInt = random.nextInt(number);

        while (number % randomInt != 0) {
            int temp = random.nextInt(number);
            if (temp != 0 && temp != 1) {
                randomInt = temp;
            }
        }

        int[] numbers = new int[2];
        numbers[0] = randomInt;
        numbers[1] = number / randomInt;

        return numbers;
    }
}
