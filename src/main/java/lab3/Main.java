package lab3;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException {
        Signature signature = Signature.getInstance("DSA");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
    }
}
