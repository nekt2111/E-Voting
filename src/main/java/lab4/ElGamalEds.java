package lab4;

import eds.ElectronicDigitalSignatureRsa;
import model.Key;

import javax.print.DocFlavor;
import java.security.PublicKey;

public class ElGamalEds {

    private static ElectronicDigitalSignatureRsa electronicDigitalSignatureRsa = new ElectronicDigitalSignatureRsa();

    public static long[] generateEds(String message) {
        return electronicDigitalSignatureRsa.generateEds(message);
    }

    public static boolean checkEds(String message, long[] eds, PublicKey publicKey) {
        return true;
    }

}
