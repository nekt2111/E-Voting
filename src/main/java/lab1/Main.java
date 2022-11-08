package lab1;

import lab1.mock.data.MockDataGenerator;
import lab1.model.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Candidate> candidates = MockDataGenerator.getCandidates();
        List<Voter> voters = MockDataGenerator.getVoters();

        CentralElectionCommission cec = new CentralElectionCommission();

        Elections elections = cec.createElections(candidates, voters);

        cec.startElections(elections);

        Candidate firstCandidate = elections.getCandidates().get(0);
        Candidate secondCandidate = elections.getCandidates().get(1);

        Voter voter1 = voters.get(0);
        Voter voter2 = voters.get(1);
        Voter voter3 = voters.get(2);
        voter1.vote(elections, secondCandidate);
        voter2.vote(elections, firstCandidate);
        voter3.vote(elections, firstCandidate);

        // 1. Vote, who cannot
/*
        try {
            Voter testVoter = new Voter(3, new Person("Nikita Linovytskyi"));
            testVoter.vote(elections, elections.getCandidates().get(0));
        }
         catch (Exception e) {
             System.out.println(e.getMessage());
         }*/

        // 2. Trying to vote two times

       /* Voter voter = voters.get(0);
        voter.vote(elections, elections.getCandidates().get(0));
        voter.vote(elections, elections.getCandidates().get(0));*/

        cec.endElections(elections);

    }

}
