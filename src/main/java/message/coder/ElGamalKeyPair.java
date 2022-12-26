package message.coder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElGamalKeyPair {
    private ElGamalPublicKey publicKey;
    private ElGamalPrivateKey privateKey;
}
