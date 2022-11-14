package lab2.model;

import java.util.*;

public class CentralElectionCommission {

    public Elections createElections(List<Candidate> candidates,
                                     List<Voter> voters) {
        Elections elections = initElections(candidates, voters);
        setUpBulletinsList(elections);

        return elections;
    }

    public void startElections(Elections elections) {

    }


    public void endElections(Elections elections) {

    }

    private boolean allCandidatesHaveSameAmountOfVotes(Map<Candidate, Integer> candidateVotes) {
        Integer firstCandidateVoteAmount = new ArrayList<>(candidateVotes.values()).get(0);
        return candidateVotes.values().stream().allMatch(amount -> Objects.equals(amount, firstCandidateVoteAmount));
    }

    private Candidate getCandidateWithMostVotes(Map<Candidate, Integer> candidateVotes) {
        return null;
    }

    private int countVotesForCandidate(Candidate candidate, Elections elections) {
        return 0;
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
        return null;
    }

    private void sendBulletinsToVoters(Elections elections) {
        // todo
    }

    private void emptyBulletinsList(Elections elections) {
        elections.setBulletins(new ArrayList<>());
    }

    private static void sendBulletinToVoter(Voter voter, Bulletin bulletin) {
        voter.setBulletin(bulletin);
    }

    private static Bulletin findBulletinByVoterId(Integer id, Elections elections) {
        return null;
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
