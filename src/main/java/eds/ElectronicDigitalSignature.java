package eds;

import model.Key;

public interface ElectronicDigitalSignature {

    long[] generateEds(String message);

    boolean checkEds(String message, long[] eds, Key openKey);

    Key getOpenKey();

}
