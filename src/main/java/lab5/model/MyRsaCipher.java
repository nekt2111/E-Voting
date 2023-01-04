package lab5.model;

import util.DataConfiguration;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class MyRsaCipher {

    private static HashMap<byte[], String> stringHashMap = new HashMap<>();
    private static char[] alphabet = DataConfiguration.alphabet;

    public static byte[] encrypt(String string, PublicKey publicKey) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            stringBuilder.append(alphabet[random.nextInt(alphabet.length)]);
        }

        byte[] result =  stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        stringHashMap.put(result, string);
        System.out.println(string + " was encrypted to " + Arrays.toString(result));

        return result;
    }

    public static String decrypt(byte[] bytes, PrivateKey privateKey) {
        String result = stringHashMap.get(bytes);
        System.out.println("Was decrypted to " + result);
        return result;
    }

}
