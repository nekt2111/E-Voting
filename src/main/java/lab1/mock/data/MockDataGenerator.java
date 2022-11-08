package lab1.mock.data;

import lab1.model.Candidate;
import lab1.model.Person;
import lab1.model.Voter;

import java.util.ArrayList;
import java.util.List;

public class MockDataGenerator {

    public static List<Candidate> getCandidates() {
        List<Candidate> candidates = new ArrayList<>();

        Candidate candidate1 = new Candidate("John Biden");
        Candidate candidate2 = new Candidate("Donald Trump");

        return List.of(candidate1, candidate2);
    }

    public static List<Voter> getVoters() {
        List<Voter> voters = new ArrayList<>();

        Voter voter1 = new Voter(1, new Person("Lara Croft"));
        Voter voter2 = new Voter(2, new Person("Erling Haaland"));

        return List.of(voter1, voter2);
    }


}
