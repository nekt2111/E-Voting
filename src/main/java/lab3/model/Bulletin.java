package lab3.model;

import lombok.Data;

import java.util.List;

@Data
public class Bulletin {
    private List<Candidate> candidates;
    private Candidate selectedCandidate;

    public Bulletin(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public void selectCandidate(Candidate candidate) {
        if (candidates.contains(candidate)) {
            selectedCandidate = candidate;
        } else {
            throw new IllegalArgumentException("You cannot vote for that candidate - " + candidate + ". He is not in list");
        }
    }
}

