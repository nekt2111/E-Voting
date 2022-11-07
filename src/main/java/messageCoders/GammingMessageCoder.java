package messageCoders;

import messageCoders.MessageCoder;

import java.util.Random;

public class GammingMessageCoder implements MessageCoder {

    private final char[] alphabet;
    private final int key;

    public GammingMessageCoder(char[] alphabet, int key) {
        this.alphabet = alphabet;
        this.key = key;
    }

    @Override
    public String encode(String message) {

        char[] messageCharArray = message.toCharArray();
        char[] randomCharArray = generatePseudoRandomSequence(message);

        char[] resultedCharArray = new char[message.length()];

        for (int i = 0; i < message.length() ; i++) {

            int indexOfCharInMessageArray = getIndexOfCharInAlphabet(messageCharArray[i]);
            int indexOfCharInRandomArray =  getIndexOfCharInAlphabet(randomCharArray[i]);

            int resultedIndex = (indexOfCharInMessageArray + indexOfCharInRandomArray) % alphabet.length;

            resultedCharArray[i] = alphabet[resultedIndex];
        }

        return String.valueOf(resultedCharArray);
    }

    @Override
    public String decode(String codedMessage) {
        char[] codedMessageCharArray = codedMessage.toCharArray();
        char[] randomCharArray = generatePseudoRandomSequence(codedMessage);

        char[] resultedCharArray = new char[codedMessage.length()];

        for (int i = 0; i < codedMessage.length() ; i++) {

            int indexOfCharInCodedMessageArray = getIndexOfCharInAlphabet(codedMessageCharArray[i]);
            int indexOfCharInRandomArray =  getIndexOfCharInAlphabet(randomCharArray[i]);

            int resultedIndex = (indexOfCharInCodedMessageArray + alphabet.length - indexOfCharInRandomArray) % alphabet.length;

            resultedCharArray[i] = alphabet[resultedIndex];
        }

        return String.valueOf(resultedCharArray);
    }

    private char[] generatePseudoRandomSequence(String message) {
        Random random = new Random(key);

        char[] generatedChars = new char[message.length()];

        for (int i = 0; i < message.length() ; i++) {
            generatedChars[i] = alphabet[random.nextInt(0, alphabet.length)];
        }

        return generatedChars;
    }

    private int getIndexOfCharInAlphabet(char character) {

        for (int i = 0; i < alphabet.length ; i++) {
            if (alphabet[i] == character) {
                return i;
            }
        }

        throw new IllegalArgumentException("We don't have this character in our alphabet");
    }
}
