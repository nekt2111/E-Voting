package lab4;

import lab4.model.Candidate;
import lab4.model.Voter;

import java.util.List;

public class MockDataGenerator {

    public static List<Candidate> candidateList() {
        Candidate candidate = new Candidate("John Biden");
        Candidate candidate1 = new Candidate("Donald Trump");

        return List.of(candidate, candidate1);
    }

    public static List<Voter> voterList() {
        Voter voterA = new Voter(1, "A");
        Voter voterB = new Voter(2, "B");
        Voter voterC = new Voter(3, "C");
        Voter voterD = new Voter(4, "D");

        return List.of(voterA, voterB, voterC, voterD);
    }
}
