package lab1.model;

import java.util.*;

public class CentralElectionCommission {

    public Elections createElections(List<Candidate> candidates,
                                   List<Voter> voters) {
        Elections elections = initElections(candidates, voters);
        setUpBulletinsList(elections);

        return elections;
    }

    public void startElections(Elections elections) {
        if (elections != null) {
            sendKeysToVoters(elections);
            sendBulletinsToVoters(elections);
        } else {
             throw new IllegalArgumentException("Elections were not configured!");
        }
    }


    public void endElections(Elections elections) {
        if (elections != null) {
            Map<Candidate, Integer> candidateVotes = new HashMap<>();
            for (Candidate candidate : elections.getCandidates()) {
                candidateVotes.put(candidate, countVotesForCandidate(candidate, elections));
            }

            System.out.println("Amount of votes for candidates - " + candidateVotes);

            if (allCandidatesHaveSameAmountOfVotes(candidateVotes)) {
                System.out.println("No one won. Every candidate has same amount of votes");
            } else {
                System.out.println("Winner is - " + getCandidateWithMostVotes(candidateVotes).getName());
            }

        } else {
            throw new IllegalArgumentException("Elections were not configured!");
        }
    }

    private boolean allCandidatesHaveSameAmountOfVotes(Map<Candidate, Integer> candidateVotes) {
        Integer firstCandidateVoteAmount = new ArrayList<>(candidateVotes.values()).get(0);
        return candidateVotes.values().stream().allMatch(amount -> Objects.equals(amount, firstCandidateVoteAmount));
    }

    private Candidate getCandidateWithMostVotes(Map<Candidate, Integer> candidateVotes) {

        List<Candidate> candidates = new ArrayList<>(candidateVotes.keySet());
        Candidate winner = candidates.get(0);

        for (Candidate candidate : candidateVotes.keySet()) {
            if (candidateVotes.get(candidate) > candidateVotes.get(winner)) {
                winner = candidate;
            }
        }
        return winner;
    }

    private int countVotesForCandidate(Candidate candidate, Elections elections) {

        int amount = 0;

        for (Bulletin bulletin : elections.getBulletins()) {
            if (bulletin.getSelectedCandidate().getName().equals(candidate.getName())) {
                amount++;
            }
        }
        return amount;
    }

    private Elections initElections(List<Candidate> candidates,
                               List<Voter> voters) {

        return new Elections(candidates, voters);
    }

    private void setUpBulletinsList(Elections elections) {
        if (elections != null) {
            elections.setBulletins(createBulletinsList(elections));
        } else {
            throw new IllegalStateException("Elections cannot be null!");
        }
    }

    private List<Bulletin> createBulletinsList(Elections elections) {
        List<Bulletin> bulletins = new ArrayList<>();

        for (Voter voter : elections.getVoters()) {
            bulletins.add(new Bulletin(voter.getId(), elections.getCandidates()));
        }

        return bulletins;
    }

    private void sendBulletinsToVoters(Elections elections) {
        for (Voter voter : elections.getVoters()) {
            Bulletin bulletin = findBulletinByVoterId(voter.getId(), elections);
            sendBulletinToVoter(voter, bulletin);
        }

        emptyBulletinsList(elections);
    }

    private void emptyBulletinsList(Elections elections) {
        elections.setBulletins(new ArrayList<>());
    }

    private static void sendBulletinToVoter(Voter voter, Bulletin bulletin) {
        voter.setBulletin(bulletin);
    }

    private static Bulletin findBulletinByVoterId(Integer id, Elections elections) {
        return elections.getBulletins().stream().filter(bulletin -> bulletin.getVoterId().equals(id)).findFirst().get();
    }

    private void sendKeysToVoters(Elections elections) {

        Random random = new Random();
        Map<Integer, Integer> voterPublicKeyMap = new HashMap<>();

        for (Voter voter : elections.getVoters()) {
            int publicKey = random.nextInt(1_000_000);
            voter.setCecKey(publicKey);
            voterPublicKeyMap.put(voter.getId(), publicKey);
        }

        elections.setVoterPublicKeyMap(voterPublicKeyMap);
    }
}
