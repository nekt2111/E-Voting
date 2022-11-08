package lab1;

import lab1.mock.data.MockDataGenerator;
import lab1.model.Candidate;
import lab1.model.CentralElectionCommission;
import lab1.model.Elections;
import lab1.model.Voter;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Candidate> candidates = MockDataGenerator.getCandidates();
        List<Voter> voters = MockDataGenerator.getVoters();

        CentralElectionCommission cec = new CentralElectionCommission();

        Elections elections = cec.createElections(candidates, voters);

        System.out.println(elections.getBulletins());

        cec.startElections(elections);

        Voter voter = voters.get(0);
        System.out.println(voter);
        voter.vote(elections, elections.getCandidates().get(0));
        System.out.println(elections.getBulletins());

    }

}
