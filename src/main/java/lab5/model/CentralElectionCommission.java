package lab5.model;

import lombok.Data;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.List;
import java.util.Random;

@Data
public class CentralElectionCommission {

    private Elections currentElections;
    private KeyPair keyPair;

    private static Random random = new Random();

    private final int MAX_AMOUNT_FOR_ID = 1_000_000;


    public CentralElectionCommission() {
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (Exception e) {
            System.out.println("Exception occurred - " + e);
        }
    }

    public List<Voter> formVotersList() {

        Voter voter = new Voter(1, getPublicKey());
        Voter voter1 = new Voter(2, getPublicKey());
        Voter voter2 = new Voter(3, getPublicKey());
        Voter voter3 = new Voter(4, getPublicKey());
        Voter voter4 = new Voter(5, getPublicKey());
        Voter voter5 = new Voter(6, getPublicKey());
        Voter voter6 = new Voter(7, getPublicKey());
        Voter voter7 = new Voter(8, getPublicKey());
        return List.of(voter, voter1, voter2, voter3, voter4, voter5, voter6, voter7);
    }

    public List<Candidate> formCandidatesList() {
        Candidate candidate = new Candidate(random.nextInt(MAX_AMOUNT_FOR_ID), "John Biden");
        Candidate candidate1 = new Candidate(random.nextInt(MAX_AMOUNT_FOR_ID), "Donald Trump");

        return List.of(candidate, candidate1);
    }

    public Elections createElections(List<Voter> voters,
                                     List<Candidate> candidates,
                                     List<ElectionCommission> electionCommissions) {
        Elections elections = new Elections(voters, candidates);
        elections.setCentralElectionCommission(this);
        elections.setElectionCommissions(electionCommissions);
        this.currentElections = elections;
        return elections;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

}
