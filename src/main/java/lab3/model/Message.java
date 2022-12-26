package lab3.model;

import lombok.Data;

import java.nio.charset.StandardCharsets;


@Data
public class Message {
    private int voterRandomId;
    private int registrationNumber;
    private Bulletin bulletin;
    private byte[] eds;

    public String toSignString() {
        return voterRandomId + "." + registrationNumber + "." + bulletin.getSelectedCandidate().getName();
    }

    public String toPreCodedString() {
        return voterRandomId + "." + registrationNumber + "." + bulletin.getSelectedCandidate().getName() + "." + new String(eds, StandardCharsets.UTF_8) ;
    }
}
