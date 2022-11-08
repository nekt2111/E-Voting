package lab1.mock.data;

import lab1.model.Candidate;
import lab1.model.Person;
import lab1.model.Voter;

import java.util.ArrayList;
import java.util.List;

public class MockDataGenerator {

    public static List<Candidate> getCandidates() {
        Candidate candidate1 = new Candidate("John Biden");
        Candidate candidate2 = new Candidate("Donald Trump");

        return List.of(candidate1, candidate2);
    }

    public static List<Voter> getVoters() {
        Voter voter1 = new Voter(1, new Person("Lara Croft"));
        Voter voter2 = new Voter(2, new Person("Erling Haaland"));
        Voter voter3 = new Voter(3, new Person("Lionel Messi"));
        Voter voter4 = new Voter(4, new Person("Cristiano Ronaldo"));
        Voter voter5 = new Voter(5, new Person("Kilian Mbappe"));
        Voter voter6 = new Voter(6, new Person("Lis san Heyk"));
        Voter voter7 = new Voter(7, new Person("Steve Jobs"));
        Voter voter8 = new Voter(8, new Person("Bill Gates"));

        return List.of(voter1, voter2, voter3, voter4, voter5, voter6, voter7, voter8);
    }


}
