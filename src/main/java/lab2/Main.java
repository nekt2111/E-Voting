package lab2;

import lab2.data.MockDataGenerator;
import lab2.model.Candidate;
import lab2.model.CentralElectionCommission;
import lab2.model.Elections;
import lab2.model.Voter;
import message.coder.RsaCoder;

import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        List<Candidate> candidates = MockDataGenerator.getCandidates();
        List<Voter> voters = MockDataGenerator.getVoters();

        CentralElectionCommission cec = new CentralElectionCommission();

        Elections elections = cec.createElections(candidates, voters);

        cec.startElections(elections);
        Voter voter1 = voters.get(0);
        voter1.vote(candidates.get(1), elections);

        cec.endElections(elections);

        // voters create

    }

}
