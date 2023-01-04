package lab5.model;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class DsaEds {

    public static KeyPair generateKeyPair() {
        KeyPair keyPair = null;
        try {
            keyPair = KeyPairGenerator.getInstance("DSA").generateKeyPair();

        } catch (Exception e) {
            System.out.println(e);
        }
        return keyPair;
    }

    public static byte[] sign(byte[] toSign, PrivateKey privateKey) {
        byte[] result = null;
        try {
            Signature signature = Signature.getInstance("SHA256WithDSA");
            signature.initSign(privateKey);
            signature.update(toSign);
            result = signature.sign();

        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public static boolean verify(byte[] toSign, byte[] eds, PublicKey publicKey) {
        boolean result = false;

        try {
            Signature signature = Signature.getInstance("SHA256WithDSA");
            signature.initVerify(publicKey);
            signature.update(toSign);
            result = signature.verify(eds);
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

}
