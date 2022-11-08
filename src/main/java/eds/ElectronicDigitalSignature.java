package eds;

import model.Key;

public interface ElectronicDigitalSignature {

    long[] generateEds();

    boolean checkEds(long[] eds, Key openKey);

    Key getOpenKey();

}
