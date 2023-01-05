package lab6.src;

import lab6.model.Candidate;
import lab6.model.ElectionCommission;
import lab6.model.Voter;
import lab6.model.RegistrationOffice;

import java.util.Random;

public class Main {
    private static Voter[] voters = {
            new Voter("Petro"),
            new Voter("Denys"),
            new Voter("Nikita")
    };

    public static Candidate[] candidates = {
            new Candidate("John Biden"),
            new Candidate("Donald Trump")
    };

    public static void main(String[] args) {
        RegistrationOffice.receive(voters);
        for (Voter voter : voters) {
            RegistrationOffice.registrate(voter);
        }
        Random random = new Random();
        for (Voter voter : voters) {
            voter.installProgram();
            Candidate candidate = candidates[random.nextInt(candidates.length)];
            voter.vote(candidate);
        }
        getResults();
    }


    private static void getResults() {
        ElectionCommission.publicateResults();
    }
}
