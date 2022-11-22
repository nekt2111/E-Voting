package lab2.model;

import eds.ElectronicDigitalSignatureRsa;
import message.coder.RsaCoder;
import model.Key;

import java.util.*;

public class CentralElectionCommission {

    private RsaCoder rsaCoder = new RsaCoder();
    private final ElectronicDigitalSignatureRsa electronicDigitalSignatureRsa = new ElectronicDigitalSignatureRsa();

    private Map<Integer, String> voterIdEdsMessage = new HashMap<>();

    private Map<Candidate, Integer> candidateAmountOfVotesMap = new HashMap<>();

    private Set<Integer> votersIdsWhoSentBulletinsToSign = new HashSet<>();

    private Set<Integer> votersWhoVote = new HashSet<>();

    public Elections createElections(List<Candidate> candidates,
                                     List<Voter> voters) {
        Elections elections = initElections(candidates, voters);
        setUpBulletinsList(elections);

        return elections;
    }

    public void startElections(Elections elections) {

        elections.getCandidates().forEach(candidate -> candidateAmountOfVotesMap.put(candidate, 0));
        elections.getVoters().forEach(voter -> voter.setCentralElectionCommissionToSendVote(this));
    }

    public void receiveMaskedMessageSets(Elections elections, List<long[]> maskedMessageSets, Key key) {

        System.out.println("Received encoded message sets...");

        Random random = new Random();
        int randomMessageSetNumber = random.nextInt(10);
        Integer voterId = 0;

        for (int i = 0; i < 10 ; i++) {
            if (i != randomMessageSetNumber) {
                voterId = openMessageSet(maskedMessageSets.get(i), elections, key);
            }
        }

        votersIdsWhoSentBulletinsToSign.add(voterId);
        long[] eds = electronicDigitalSignatureRsa.generateEds(generateEdsMessageForVoter(voterId));
        long[] messageSetThatWasNotOpen = maskedMessageSets.get(randomMessageSetNumber);

        elections.findVoterById(voterId).receivedMessageSetFromCec(messageSetThatWasNotOpen, eds);
    }

    private Integer openMessageSet(long[] codedMessageSet, Elections elections, Key key) {

        String decodedMessageSet = rsaCoder.decode(codedMessageSet, key);

        System.out.println("Opening message set with number " + decodedMessageSet.split("\\.")[1]);

        checkIfMessageSetAndBulletinsWereCorrectlyFormed(decodedMessageSet, elections);

        Integer voterId = Integer.valueOf(decodedMessageSet.split("\\.")[0]);

        if (doesVoterAlreadySentBulletinsToSign(voterId)) {
            throw new IllegalStateException("Voter with id - " + voterId + " already have voted");
        }

        return voterId;
    }

    public void receiveCodedBulletin(long[] codedBulletin, Key key, long[] eds, Elections elections) {
        System.out.println("Received coded bulletin - " + Arrays.toString(codedBulletin));
        String decodedBulletin = rsaCoder.decode(codedBulletin, key);
        System.out.println("Decoded - " + decodedBulletin);
        Integer voterId = Integer.valueOf(decodedBulletin.split("\\.")[0]);
        String candidateName = decodedBulletin.split("\\.")[1];

        Candidate candidate = elections.getCandidates().stream().filter(c -> Objects.equals(c.getName(), candidateName)).findFirst().get();

        if (!votersIdsWhoSentBulletinsToSign.contains(voterId)){
            throw new IllegalArgumentException("Voter didn't send bulletin to sign!");
        }

        if (votersWhoVote.contains(voterId)) {
            throw new IllegalArgumentException("Voter already vote!");
        }

        String messageForEds = voterIdEdsMessage.get(voterId);

        if (electronicDigitalSignatureRsa.checkEds(messageForEds, eds, electronicDigitalSignatureRsa.getOpenKey())) {
            System.out.println("Eds is correct!");
            Integer currentAmount = candidateAmountOfVotesMap.get(candidate);
            candidateAmountOfVotesMap.replace(candidate, ++currentAmount);
            System.out.println("Increased amount of votes for " + candidateName + " to " + currentAmount);
            votersWhoVote.add(voterId);
        } else {
            throw new IllegalArgumentException("Your EDS is not correct!");
        }
    }

    private Candidate getCandidateWithMostVotes() {
        List<Candidate> candidates = new ArrayList<>(candidateAmountOfVotesMap.keySet());
        Candidate winner = candidates.get(0);

        for (Candidate candidate : candidateAmountOfVotesMap.keySet()) {
            if (candidateAmountOfVotesMap.get(candidate) > candidateAmountOfVotesMap.get(winner)) {
                winner = candidate;
            }
        }
        return winner;
    }

    private void checkIfMessageSetAndBulletinsWereCorrectlyFormed(String decodedMessageSet, Elections elections) {
        Integer voterId;
        try {
            voterId = Integer.valueOf(decodedMessageSet.split("\\.")[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("You message sets are not correctly formed!");
        }
        String[] candidatesInBulletins = decodedMessageSet.split("\\.")[2].split(",");
        System.out.println("Candidates in bulletins - " + Arrays.toString(candidatesInBulletins));
        System.out.println("Voter id - " + voterId);

        if (candidatesInBulletins.length != elections.getCandidates().size()) {
            throw new IllegalArgumentException("You bulletins are not correctly formed!");
        }
    }

    private boolean doesVoterAlreadySentBulletinsToSign(int voterId) {
        return votersIdsWhoSentBulletinsToSign.contains(voterId);
    }


    public void endElections(Elections elections) {
        System.out.println("Votes for candidates - " + candidateAmountOfVotesMap);

        if (allCandidatesHaveSameAmountOfVotes(candidateAmountOfVotesMap)) {
            System.out.println("No one won. Every candidate has same amount of votes");
        } else {
            System.out.println("Winner is - " + getCandidateWithMostVotes().getName());
        }
    }

    private boolean allCandidatesHaveSameAmountOfVotes(Map<Candidate, Integer> candidateVotes) {
        Integer firstCandidateVoteAmount = new ArrayList<>(candidateVotes.values()).get(0);
        return candidateVotes.values().stream().allMatch(amount -> Objects.equals(amount, firstCandidateVoteAmount));
    }

    private Candidate getCandidateWithMostVotes(Map<Candidate, Integer> candidateVotes) {
        return null;
    }

    private String generateEdsMessageForVoter(Integer voterId) {
        Random random = new Random();
        String result = voterId + ".random-hash." + random.nextInt(10_000);
        voterIdEdsMessage.put(voterId, result);
        return result;
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
}
