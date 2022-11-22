package lab2.model;

import lombok.Data;

import java.util.List;

@Data
public class Bulletin {

    private Integer voterId;
    private Candidate candidate;

    public Bulletin(Integer voterId, Candidate candidate) {
        this.voterId = voterId;
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return candidate.getName();
    }

    public String toPreCodedString() {
        return voterId + "." + candidate.getName();
    }
}
