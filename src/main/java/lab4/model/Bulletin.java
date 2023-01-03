package lab4.model;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Data
public class Bulletin {
    private List<Candidate> candidates;
    private Candidate selectedCandidate;

    private Integer voterId;

    private long[] eds;

    public Bulletin(List<Candidate> candidates, Integer voterId) {
        this.candidates = candidates;
        this.voterId = voterId;
    }

    public void selectCandidate(Candidate candidate) {
        if (candidates.contains(candidate)) {
            selectedCandidate = candidate;
        } else {
            throw new IllegalArgumentException("You cannot vote for that candidate - " + candidate + ". He is not in list");
        }
    }

    public String toPreCodedString() {
        return voterId + "." + selectedCandidate.getName();
    }

    public void removeEds() {
        System.out.println("Removing bulletin eds! " + Arrays.toString(toPreCodedString().getBytes(StandardCharsets.UTF_8)));
    }

}
