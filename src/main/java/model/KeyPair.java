package model;

import lombok.Data;
import model.Key;

@Data
public class KeyPair {
    private Key openKey;
    private Key privateKey;

    public long getGeneralPart() {
        if (openKey != null) {
            return openKey.getGeneralPart();
        } else {
            throw new IllegalStateException("Open key is not defined!");
        }
    }

    public KeyPair(Key openKey, Key privateKey) {
        if (openKey.getGeneralPart() == privateKey.getGeneralPart()) {
            this.openKey = openKey;
            this.privateKey = privateKey;
        } else {
            throw new IllegalArgumentException("General key part is not equal!");
        }
    }
}
