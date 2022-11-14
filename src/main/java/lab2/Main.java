package lab2;

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

        lab1.model.CentralElectionCommission cec = new CentralElectionCommission();

        Elections elections = cec.createElections(candidates, voters);

        cec.startElections(elections);

        // voters create

    }

}
