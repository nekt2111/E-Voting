package lab2.data;

import lab2.model.Candidate;
import lab2.model.Voter;
import model.Person;

import java.util.List;

public class MockDataGenerator {

    public static List<Candidate> getCandidates() {
        Candidate candidate1 = new Candidate("John Biden");
        Candidate candidate2 = new Candidate("Donald Trump");

        return List.of(candidate1, candidate2);
    }

    public static List<Voter> getVoters() {
        Voter voter1 = new Voter( new Person("Lara Croft"));
        Voter voter2 = new Voter( new Person("Erling Haaland"));
        Voter voter3 = new Voter( new Person("Lionel Messi"));
        Voter voter4 = new Voter( new Person("Cristiano Ronaldo"));
        Voter voter5 = new Voter( new Person("Kilian Mbappe"));
        Voter voter6 = new Voter( new Person("Lis san Heyk"));
        Voter voter7 = new Voter( new Person("Steve Jobs"));
        Voter voter8 = new Voter( new Person("Bill Gates"));

        return List.of(voter1, voter2, voter3, voter4, voter5, voter6, voter7, voter8);
    }


}
