package lab6.model;

import lombok.Data;

@Data
public final class Token {
    public final int electorId;
    public final PublicKey publicKey;

    public Token(int electorId, PublicKey publicKey) {
        this.electorId = electorId;
        this.publicKey = publicKey;
    }
}
