package message.coder;

import model.Key;
import model.KeyPair;
import util.DataConfiguration;
import util.MathUtils;
import util.RandomPrimaryNumberGenerator;

import java.util.Map;
import java.util.Random;

import static util.MathUtils.eulerFunction;
import static util.MathUtils.gcd;

public class RsaCoder {

    private final char[] alphabet;

    private final int MAX_VALUE_OF_RANDOM_INT = 1_000;

    private Key privateKey;

    public RsaCoder() {
        this.alphabet = DataConfiguration.alphabet;
    }

    public long[] encode(String message) {
        RandomPrimaryNumberGenerator primaryNumberGenerator = new RandomPrimaryNumberGenerator(MAX_VALUE_OF_RANDOM_INT);

        int p = primaryNumberGenerator.getRandomPrimeNumber();
        int q = primaryNumberGenerator.getRandomPrimeNumber();
        int n = p * q;
        int z = (int) eulerFunction(p, q);
        int e;
        int d = 0;

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

        char[] messageCharArray = message.toCharArray();
        long[] encodedCharArray = new long[message.length()];

        for (int i = 0; i < messageCharArray.length; i++) {
            int m = getIndexOfCharInAlphabet(messageCharArray[i]);
            encodedCharArray[i] = MathUtils.modPow(m, e, n);
        }

        return encodedCharArray;
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
