package lab3.model;

import lombok.Data;

import java.util.List;

@Data
public class Elections {
    private List<Voter> voters;
    private List<Candidate> candidates;
    private RegistrationBuro registrationBuro;
    private CentralElectionCommission cec;

    public Elections(List<Voter> voters,
                     List<Candidate> candidates,
                     RegistrationBuro registrationBuro,
                     CentralElectionCommission cec) {
        this.voters = voters;
        this.candidates = candidates;
        this.registrationBuro = registrationBuro;
        this.cec = cec;
    }
}
