package lab2;

import lab1.mock.data.MockDataGenerator;
import lab1.model.Candidate;
import lab1.model.CentralElectionCommission;
import lab1.model.Elections;
import lab1.model.Voter;
import message.coder.RsaCoder;

import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        String test = "Nikita";
        RsaCoder coder = new RsaCoder();
        long[] codedMsg = coder.encode(test);

        System.out.println(Arrays.toString(codedMsg));
        coder.decode(codedMsg, coder.getPrivateKey());



       /* List<Candidate> candidates = MockDataGenerator.getCandidates();
        List<Voter> voters = MockDataGenerator.getVoters();

        lab1.model.CentralElectionCommission cec = new CentralElectionCommission();

        Elections elections = cec.createElections(candidates, voters);

        cec.startElections(elections);*/

        // voters create

    }

}
