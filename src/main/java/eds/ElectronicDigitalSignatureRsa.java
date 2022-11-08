package eds;

import eds.ElectronicDigitalSignature;
import model.Key;
import model.KeyPair;
import util.DataConfiguration;
import util.MathUtils;
import util.RandomPrimaryNumberGenerator;
import java.util.Arrays;

public class ElectronicDigitalSignatureRsa implements ElectronicDigitalSignature {

    private Key publicKey;
    private final RandomPrimaryNumberGenerator randomPrimaryNumberGenerator;

    private final char[] alphabet;

    public ElectronicDigitalSignatureRsa() {
        this.randomPrimaryNumberGenerator = new RandomPrimaryNumberGenerator(10_000);
        this.alphabet = DataConfiguration.alphabet;
    }


    @Override
    public long[] generateEds() {
        String message = generateMessage();
        KeyPair keyPair = this.generateKeyPair();

        long[] messageHashes = getHashesFromMessage(message, keyPair.getGeneralPart());

        long[] egs = new long[messageHashes.length];

        for (int i = 0; i < messageHashes.length ; i++) {
            egs[i] = MathUtils.modPow(messageHashes[i], keyPair.getPrivateKey().getMainPart(), keyPair.getGeneralPart());
        }

        return egs;
    }

    private long[] getHashesFromMessage(String message, long module) {
        long h = 0;

        char[] messageChars = message.toCharArray();

        long[] hashedNumbers = new long[message.length()];

        for (int i = 0; i < message.length() ; i++) {
            h = MathUtils.modPow(h + getIndexOfCharInAlphabet(messageChars[i]), 2, module);
            hashedNumbers[i] = h;
        }

        return hashedNumbers;
    }

    private KeyPair generateKeyPair() {
        long firstRandomPrimaryNumber = randomPrimaryNumberGenerator.getRandomPrimeNumber();
        long secondRandomPrimaryNumber = randomPrimaryNumberGenerator.getRandomPrimeNumber();
        long module = firstRandomPrimaryNumber * secondRandomPrimaryNumber;

        long m = MathUtils.eulerFunction(firstRandomPrimaryNumber, secondRandomPrimaryNumber);

        long d = randomPrimaryNumberGenerator.getRandomPrimeNumber();

        long e = d + 1;

        while ((e * d) % m != 1) {
            e++;
        }

        publicKey = new Key(e, module);
        Key privateKey = new Key(d, module);

        return new KeyPair(publicKey, privateKey);
    }

    private int getIndexOfCharInAlphabet(char character) {
        for (int i = 0; i < alphabet.length ; i++) {
            if (alphabet[i] == character) {
                return i;
            }
        }

        throw new IllegalArgumentException("We don't have this character in our alphabet");
    }

    @Override
    public boolean checkEds(long[] eds, Key publicKey) {

        String message = generateMessage();

        long[] messageHashes = getHashesFromMessage(message, publicKey.getGeneralPart());

        long[] edsHashes = new long[eds.length];

        for (int i = 0; i < eds.length ; i++) {
            edsHashes[i] = MathUtils.modPow(eds[i], publicKey.getMainPart(), publicKey.getGeneralPart());
        }

        return Arrays.equals(messageHashes, edsHashes);
    }

    @Override
    public Key getOpenKey() {
        if (publicKey != null) {
            return publicKey;
        } else {
            throw new IllegalStateException("Open key is not generated!");
        }
    }

    private static String generateMessage() {
        return "nikita";
    }
}
