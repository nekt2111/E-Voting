package lab6.model;

import java.util.HashMap;

public class Encryptor {
    private static HashMap<byte[], byte[]> encryptions = new HashMap<>();

    private static byte[] apply(byte[] bytes, Key key) {
        if (encryptions.containsKey(bytes)){
            return encryptions.get(bytes);
        }

        byte[] result = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte)((bytes[i] + key.value[i % key.value.length]) % 256);
        }

        encryptions.put(result, bytes);

        return result;
    }

    public static byte[] apply(byte[] bytes, PrivateKey key) {
        return apply(bytes, (Key)key);
    }

    public static byte[] apply(byte[] bytes, PublicKey key) {
        return apply(bytes, (Key)key);
    }
}

