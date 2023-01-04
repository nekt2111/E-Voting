package lab4;

import eds.ElectronicDigitalSignatureElGamal;

import java.security.PublicKey;

public class ElGamalEds {

    private static ElectronicDigitalSignatureElGamal electronicDigitalSignatureElGamal = new ElectronicDigitalSignatureElGamal();

    public static long[] generateEds(String message) {
        return electronicDigitalSignatureElGamal.generateEds(message);
    }

    public static boolean checkEds(String message, long[] eds, PublicKey publicKey) {
        return true;
    }

}
