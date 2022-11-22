package lab2.model;

import lombok.Data;

import java.util.List;

@Data
public class MessageSet {
    private List<Bulletin> bulletins;
    private Integer number;
    private Integer voterId;

    private long[] eds;

    public MessageSet(Integer voterId, List<Bulletin> bulletins, Integer number) {
        for (Bulletin bulletin : bulletins) {
            if (bulletin.getCandidate() == null) {
                throw new IllegalArgumentException("Bulletin is not valid + " + bulletin);
            }
        }

        this.voterId = voterId;
        this.bulletins = bulletins;
        this.number = number;
    }

    public String toPreCodedString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(voterId).append(".");
        stringBuilder.append(number).append(".");

        for (int i = 0; i < bulletins.size(); i++) {
            stringBuilder.append(bulletins.get(i));
            if (i != bulletins.size() - 1) {
                stringBuilder.append(",");
            }
        }

        return stringBuilder.toString();
    }
}
