package lab5.model;

import lombok.Data;

import java.util.List;

@Data
public class Elections {

    private List<Voter> voters;
    private List<Candidate> candidates;
    private Candidate winner;

    private List<ElectionCommission> electionCommissions;
    private CentralElectionCommission centralElectionCommission;

    public Elections(List<Voter> voters,
                     List<Candidate> candidates) {
        this.voters = voters;
        this.candidates = candidates;
    }

}
