package lab3;

import lab3.model.Candidate;
import lab3.model.Voter;

import java.util.List;

public class MockDataGenerator {

    public static List<Candidate> getCandidates() {
        Candidate candidate = new Candidate("John Biden");
        Candidate candidate1 = new Candidate("Donald Trump");

        return List.of(candidate, candidate1);
    }

    public static List<Voter> voters() {
        Voter voter = new Voter(1);
        Voter voter1 = new Voter(2);
        Voter voter2 = new Voter(3);
        Voter voter3 = new Voter(4);
        Voter voter4 = new Voter(5);
        Voter voter5 = new Voter(6);
        Voter voter6 = new Voter(7);
        Voter voter7 = new Voter(8);
        return List.of(voter, voter1, voter2, voter3, voter4, voter5, voter6, voter7);
    }
}
