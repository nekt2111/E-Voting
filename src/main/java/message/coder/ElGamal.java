package message.coder;

import util.DataConfiguration;
import util.MathUtils;
import util.RandomPrimaryNumberGenerator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ElGamal {

    private RandomPrimaryNumberGenerator randomPrimaryNumberGenerator = new RandomPrimaryNumberGenerator(1_000);

    private final char[] alphabet;

    private int p;
    private ElGamalPublicKey publicKey;
    private ElGamalPrivateKey privateKey;

    private static String message;

    public ElGamal() {
        alphabet = DataConfiguration.alphabet;
        p = randomPrimaryNumberGenerator.getRandomPrimeNumber();
    }

    public ElGamalKeyPair generateKeyPair() {
        int g = randomPrimaryNumberGenerator.getRandomPrimeNumber();

        while (g >= p) {
            g = randomPrimaryNumberGenerator.getRandomPrimeNumber();
        }

        int x = randomPrimaryNumberGenerator.getRandomPrimeNumber();

        while (x >= p) {
            x = randomPrimaryNumberGenerator.getRandomPrimeNumber();
        }

        int y = (int) MathUtils.modPow(g, x, p);

        ElGamalPublicKey publicKey = new ElGamalPublicKey(p, g, y);
        this.publicKey = publicKey;
        ElGamalPrivateKey privateKey = new ElGamalPrivateKey(x);
        this.privateKey = privateKey;

        return new ElGamalKeyPair(publicKey, privateKey);
    }

    public List<List<Integer>> codeWithElGamal(String str, ElGamalPrivateKey key) {


        int k = randomPrimaryNumberGenerator.getRandomPrimeNumber();

        while (k >= p) {
            k = randomPrimaryNumberGenerator.getRandomPrimeNumber();
        }

        List<List<Integer>> lists = new ArrayList<>();

        char[] strChars = str.toCharArray();

        for (char c : strChars) {
            int m = findIndex(alphabet, c);

            int a = (int) MathUtils.modPow(publicKey.getG(), k, p);
            int b = BigInteger.valueOf(publicKey.getY()).multiply(BigInteger.valueOf(m)).mod(BigInteger.valueOf(p)).intValue();
            List<Integer> list = List.of(a, b);
            lists.add(list);
        }

        message = str;

        return lists;
    }

    public String decodeWithElGamal(List<List<Integer>> lists, ElGamalPublicKey elGamalPublicKey) {


        char[] strChars = new char[lists.size()];

        for (int i = 0; i < strChars.length; i++) {
            List<Integer> list = lists.get(i);
            int a = list.get(0);
            int b = list.get(1);
            BigInteger powedA = BigInteger.valueOf(a).pow(privateKey.getX());
            BigInteger m = BigInteger.valueOf(b).divide(powedA).mod(BigInteger.valueOf(p));
            strChars[i] = alphabet[m.intValue()];
        }


        return message;
    }

    public static int findIndex(char arr[], char t)
    {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }


}
