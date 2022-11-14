package lab2.model;

import lombok.Data;

import java.util.List;

@Data
public class MessageSet {
    private List<Bulletin> bulletins;
    private Integer voterId;

    public MessageSet(Integer voterId, List<Bulletin> bulletins) {
        for (Bulletin bulletin : bulletins) {
            if (bulletin.getCandidate() == null) {
                throw new IllegalArgumentException("Bulletin is not valid + " + bulletin);
            }
        }

        this.voterId = voterId;
        this.bulletins = bulletins;
    }
}
