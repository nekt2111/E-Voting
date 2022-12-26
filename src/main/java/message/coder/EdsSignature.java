package message.coder;

import lab3.model.Message;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.*;

@Data
public class EdsSignature {

    private Signature signature;

    public EdsSignature() {
        try {
            this.signature = Signature.getInstance("SHA256WithDSA");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public byte[] sign(String message, PrivateKey privateKey) {
        try {
            signature.initSign(privateKey);
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            return signature.sign();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean checkSign(Message message, PublicKey publicKey) {
        boolean result = false;
        try {
            signature.initVerify(publicKey);
            signature.update(message.toSignString().getBytes(StandardCharsets.UTF_8));
            return signature.verify(message.getEds());
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}
