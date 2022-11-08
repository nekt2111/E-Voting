package message.coder;

public interface MessageCoder {
    String encode(String message, int publicKey);
    String decode(String message, int publicKey);
}
