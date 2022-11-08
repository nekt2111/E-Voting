package lab1.model;

import lombok.Data;

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

    public void receiveBulletin(Bulletin bulletin) {

    }

    public void endElections(Elections elections) {
        if (elections != null) {

        } else {
            throw new IllegalArgumentException("Elections were not configured!");
        }
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

    private static Voter findVoterById(Integer id, Elections elections) {
        return elections.getVoters().stream().filter(voter -> voter.getId().equals(id)).findFirst().get();
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
