package message.coder;

import model.Key;
import util.DataConfiguration;
import util.MathUtils;
import util.RandomPrimaryNumberGenerator;

import java.util.ArrayList;
import java.util.Arrays;

import static util.MathUtils.eulerFunction;
import static util.MathUtils.gcd;

public class RsaCoder {

    private final char[] alphabet;

    private final int MAX_VALUE_OF_RANDOM_INT = 1_000;

    private int p, q, n, z, e, d, r;
    private ArrayList<Integer> alphabetIndexesOfChars;

    private Key privateKey;

    public RsaCoder() {
        this.alphabet = DataConfiguration.alphabet;
        this.alphabetIndexesOfChars = new ArrayList<>();
        generateKeys();
    }

    public long[] code(String message) {
        char[] messageCharArray = message.toCharArray();
        long[] encodedCharArray = new long[message.length()];

        for (int i = 0; i < messageCharArray.length; i++) {
            int m = getIndexOfCharInAlphabet(messageCharArray[i]);
            encodedCharArray[i] = MathUtils.modPow(m, e, n);
        }

        return encodedCharArray;
    }

    public void generateKeys() {
        RandomPrimaryNumberGenerator primaryNumberGenerator = new RandomPrimaryNumberGenerator(MAX_VALUE_OF_RANDOM_INT);

        p = primaryNumberGenerator.getRandomPrimeNumber();
        q = primaryNumberGenerator.getRandomPrimeNumber();
        n = p * q;
        z = (int) eulerFunction(p, q);
        d = 0;

        for (e = 2; e < z; e++) {

            // e is for public key exponent
            if (gcd(e, z) == 1) {
                break;
            }
        }
        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * z);

            // d is for private key exponent
            if (x % e == 0) {
                d = x / e;
                break;
            }
        }

        privateKey = new Key(d, n);
    }

    public String decode(long[] encodedMsg, Key privateKey) {
        char[] decodedMsg = new char[encodedMsg.length];

        for (int i = 0; i < decodedMsg.length ; i++) {
            int indexInAlphabet = (int) MathUtils.modPow(encodedMsg[i], privateKey.getMainPart(), privateKey.getGeneralPart());
            decodedMsg[i] = alphabet[indexInAlphabet];
        }

        return new String(decodedMsg);
    }

    public Key getPrivateKey() {
        return privateKey;
    }

    private int getIndexOfCharInAlphabet(char character) {

        for (int i = 0; i < alphabet.length ; i++) {
            if (alphabet[i] == character) {
                return i;
            }
        }

        throw new IllegalArgumentException("We don't have this character in our alphabet - " + character);
    }
}
