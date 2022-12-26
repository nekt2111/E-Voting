package lab3;

import lab3.model.*;
import message.coder.ElGamal;
import message.coder.ElGamalKeyPair;

import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.List;

public class Main {

    public static void main(String[] args)  {

        List<Candidate> candidates = MockDataGenerator.getCandidates();
        List<Voter> voters = MockDataGenerator.voters();
        RegistrationBuro registrationBuro = new RegistrationBuro();
        CentralElectionCommission cec = new CentralElectionCommission();

        Elections elections = cec.createElections(voters, candidates, registrationBuro);

        cec.startElections(elections);

        Voter voter = voters.get(0);
        voter.vote(candidates.get(0), elections);

        cec.endElections(elections);


    }
}
