package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPrimaryNumberGenerator {

    private final List<Integer> primeNumbers;

    public RandomPrimaryNumberGenerator(Integer amountOfRandomPrimeNumbers) {
        this.primeNumbers = generateListOfPrimeNumbers(amountOfRandomPrimeNumbers);
    }

    public int getRandomPrimeNumber() {
        Random random = new Random();
        int randomInt = random.nextInt(0, primeNumbers.size() - 1);
        int randomPrime = primeNumbers.get(randomInt);
        System.out.println("Random prime number - "  + randomPrime);
        return randomPrime;
    }

    private static List<Integer> generateListOfPrimeNumbers(Integer amountOfRandomPrimeNumbers) {
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        primeNumbers.add(2);

        for (int number = 3; number < amountOfRandomPrimeNumbers; number++) {

            boolean wasDivided = false;

            for (Integer primeNumber : primeNumbers) {
                if (number % primeNumber == 0) {
                    wasDivided = true;
                    break;
                }
            }

            if (!wasDivided) {
                primeNumbers.add(number);
            }
        }

        return primeNumbers;
    }
}
