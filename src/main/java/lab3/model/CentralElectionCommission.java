package lab3.model;

import lombok.Data;

import java.util.List;

@Data
public class CentralElectionCommission {

    private List<Integer> registrationNumbers;

    public Elections createElections(List<Voter> voters,
                                     List<Candidate> candidates,
                                     RegistrationBuro registrationBuro) {
        return new Elections(voters, candidates, registrationBuro, this);
    }

    public void receiveRegistrationNumbersList(List<Integer> registrationNumbers) {
        this.registrationNumbers = registrationNumbers;
    }

    public void startElections(Elections elections) {
        setUpVotersRegistrationNumbers(elections);
        receiveRegistrationNumbersFromBuro(elections.getRegistrationBuro());
    }

    private void setUpVotersRegistrationNumbers(Elections elections) {
        List<Voter> voters = elections.getVoters();
        for (Voter voter : voters) {
            voter.getRegistrationNumber(elections.getRegistrationBuro());
        }
    }

    private void receiveRegistrationNumbersFromBuro(RegistrationBuro registrationBuro) {
        registrationBuro.sendRegistrationNumberToCec(this);
    }

    public void endElections(Elections elections) {

    }
}
