package message.coder;

import model.Key;
import model.KeyPair;
import util.DataConfiguration;
import util.MathUtils;
import util.RandomPrimaryNumberGenerator;

import java.util.Map;
import java.util.Random;

public class RsaCoder {

    private final char[] alphabet;

    private final int MAX_VALUE_OF_RANDOM_INT = 1_000;

    private Key privateKey;

    public RsaCoder() {
        this.alphabet = DataConfiguration.alphabet;
    }

    public long[] encode(String message, int publicKey) {
        RandomPrimaryNumberGenerator primaryNumberGenerator = new RandomPrimaryNumberGenerator(MAX_VALUE_OF_RANDOM_INT);

        int p = primaryNumberGenerator.getRandomPrimeNumber();
        int q = primaryNumberGenerator.getRandomPrimeNumber();

        long n = MathUtils.eulerFunction(p, q);
        long d = primaryNumberGenerator.getRandomPrimeNumber();

        long e = MathUtils.getPublicPartOfKey(d, n);

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

        for (int i = 0; i < encodedMsg.length; i++) {
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
