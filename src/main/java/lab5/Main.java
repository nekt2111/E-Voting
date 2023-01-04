package lab5;

import lab5.model.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        CentralElectionCommission cec = new CentralElectionCommission();
        ElectionCommission ec1 = new ElectionCommission("Election commission number 1");
        ElectionCommission ec2 = new ElectionCommission("Election commission number 2");

        List<ElectionCommission> electionCommissions = List.of(ec1, ec2);

        List<Voter> voters = cec.formVotersList();
        Voter voter = voters.get(0);

        List<Candidate> candidates = cec.formCandidatesList();
        Candidate candidate = candidates.get(0);
        Candidate candidate1 = candidates.get(1);

        Elections elections = cec.createElections(voters, candidates, electionCommissions);;

        voter.vote(candidate, elections);

    }

}
