package message.coder;

public interface MessageCoder {
    String encode(String message);
    String decode(String message);
}
