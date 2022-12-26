package lab3.model;

import lombok.Data;

@Data
public class Message {
    private int voterRandomId;
    private int registrationNumber;
    private Bulletin bulletin;
    private byte[] eds;
}
