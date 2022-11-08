package lab1.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Bulletin {
    private Integer voterId;
    private List<Candidate> candidates;
    private Candidate selectedCandidate;

    private long[] eds;

    public Bulletin(Integer voterId,
                    List<Candidate> candidates) {
        this.voterId = voterId;
        this.candidates = candidates;
    }

    public String toPreCodedString() {
        return String.format("%d,%s", voterId, selectedCandidate.getName());
    }
}
