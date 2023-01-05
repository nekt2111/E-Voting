package message.coder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElGamalPublicKey {
    private int p;
    private int g;
    private int y;
}
