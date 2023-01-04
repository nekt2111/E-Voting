package lab5.model;

import lombok.Data;

import java.nio.charset.StandardCharsets;

@Data
public class Message {
    private byte[] encryptedBulletin;
    private Integer voterId;
    private byte[] eds;

    public Message(byte[] encryptedBulletin,
                   Integer voterId) {
        this.encryptedBulletin = encryptedBulletin;
        this.voterId = voterId;
    }

    public String toSignStr() {
        return new String(encryptedBulletin, StandardCharsets.UTF_8) + "." + voterId;
    }
}
