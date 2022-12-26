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

<<<<<<< HEAD
        List<Candidate> candidates = MockDataGenerator.getCandidates();
=======
        String test = "Nikita";
        RsaCoder coder = new RsaCoder();
        long[] codedMsg = coder.encode(test);

        System.out.println(Arrays.toString(codedMsg));
        coder.decode(codedMsg, coder.getPrivateKey());



       /* List<Candidate> candidates = MockDataGenerator.getCandidates();
>>>>>>> 50c5e4637aa5ca06631f5a3566f1b62609c5d6db
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
