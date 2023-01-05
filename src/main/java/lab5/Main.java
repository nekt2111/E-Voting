package lab5;

import lab5.model.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        CentralElectionCommission cec = new CentralElectionCommission();
        ElectionCommission ec1 = new ElectionCommission("Election commission A", 1);
        ElectionCommission ec2 = new ElectionCommission("Election commission B", 2);

        List<ElectionCommission> electionCommissions = List.of(ec1, ec2);

        List<Voter> voters = cec.formVotersList();
        Voter voter1 = voters.get(0);
        Voter voter2 = voters.get(1);
        Voter voter3 = voters.get(2);

        List<Candidate> candidates = cec.formCandidatesList();
        Candidate candidate = candidates.get(0);
        Candidate candidate1 = candidates.get(1);

        Elections elections = cec.createElections(voters, candidates, electionCommissions);;

        voter1.vote(candidate, elections);
        voter2.vote(candidate, elections);
        voter3.vote(candidate1, elections);
        electionCommissions.forEach(ec -> cec.collectSaveMessages(ec.publishResults()));

        cec.endElections(elections);

    }

}
