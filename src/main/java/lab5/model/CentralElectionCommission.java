package lab5.model;

import lombok.Data;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class CentralElectionCommission {

    private Elections currentElections;
    private KeyPair keyPair;

    private List<SavedMessage> savedMessages;

    private static Random random = new Random();

    private final int MAX_AMOUNT_FOR_ID = 1_000_000;

    private Map<Integer, Candidate> voterIdCandidateIdMap;
    private Map<Candidate, Integer> candidateIdVotesAmountMap;


    public CentralElectionCommission() {
        savedMessages = new ArrayList<>();
        voterIdCandidateIdMap = new HashMap<>();
        candidateIdVotesAmountMap = new HashMap<>();
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (Exception e) {
            System.out.println("Exception occurred - " + e);
        }
    }

    public List<Voter> formVotersList() {

        Voter voter = new Voter(1, getPublicKey());
        Voter voter1 = new Voter(2, getPublicKey());
        Voter voter2 = new Voter(3, getPublicKey());
        Voter voter3 = new Voter(4, getPublicKey());
        Voter voter4 = new Voter(5, getPublicKey());
        Voter voter5 = new Voter(6, getPublicKey());
        Voter voter6 = new Voter(7, getPublicKey());
        Voter voter7 = new Voter(8, getPublicKey());
        return List.of(voter, voter1, voter2, voter3, voter4, voter5, voter6, voter7);
    }

    public List<Candidate> formCandidatesList() {
        Candidate candidate = new Candidate(random.nextInt(MAX_AMOUNT_FOR_ID), "John Biden");
        System.out.println("Candidate with name " + candidate.getName() + " and id - " + candidate.getId() + " was formed");
        Candidate candidate1 = new Candidate(random.nextInt(MAX_AMOUNT_FOR_ID), "Donald Trump");
        System.out.println("Candidate with name " + candidate1.getName() + " and id - " + candidate1.getId() + " was formed");

        return List.of(candidate, candidate1);
    }

    public Elections createElections(List<Voter> voters,
                                     List<Candidate> candidates,
                                     List<ElectionCommission> electionCommissions) {
        Elections elections = new Elections(voters, candidates);
        elections.setCentralElectionCommission(this);
        elections.setElectionCommissions(electionCommissions);
        this.currentElections = elections;
        return elections;
    }

    public void collectSaveMessages(List<SavedMessage> receivedMessages) {
        this.savedMessages.addAll(receivedMessages);

        if (this.savedMessages.size() > 1 && haveTwoMessagesWithAnyOneVoter()) {
            System.out.println("Received two messages with same voter id. Starting to process!");
            processSavedMessages();
        }

    }

    public void processSavedMessages() {
        List<SavedMessage> savedMessagesWithOneVoter;

        for (SavedMessage savedMessage : savedMessages) {
            Integer voterId = savedMessage.getVoterId();

            if (haveTwoMessagesWithOneVoter(voterId)) {
                savedMessagesWithOneVoter = savedMessages.stream()
                        .filter(sm -> Objects.equals(sm.getVoterId(), voterId))
                        .collect(Collectors.toList());

                processSavedMessagesWithOneVoter(savedMessagesWithOneVoter);

                removeSavedMessagesWithVoterId(voterId);
            }
        }
    }

    public void removeSavedMessagesWithVoterId(Integer voterId) {
        this.savedMessages = this.savedMessages.stream()
                .filter(sv -> !Objects.equals(sv.getVoterId(), voterId))
                .collect(Collectors.toList());
    }

    public void processSavedMessagesWithOneVoter(List<SavedMessage> savedMessages) {
        System.out.println(savedMessages);
        Integer voterId = savedMessages.get(0).getVoterId();
        System.out.println("Processing messages with voter id - " + voterId);
        byte[] firstPart;
        byte[] secondPart;

        if (savedMessages.get(0).getElectionCommissionId() == 1) {
            firstPart = savedMessages.get(0).getCodedBulletin();
            secondPart = savedMessages.get(1).getCodedBulletin();
        } else {
            firstPart = savedMessages.get(1).getCodedBulletin();
            secondPart = savedMessages.get(0).getCodedBulletin();
        }

        String firstPartStr = MyRsaCipher.decrypt(firstPart, keyPair.getPrivate());
        String secondPartStr = MyRsaCipher.decrypt(secondPart, keyPair.getPrivate());

        Integer firstMultiplier = Integer.valueOf(firstPartStr);
        Integer secondMultiplier = Integer.valueOf(secondPartStr);

        Integer selectedCandidateId = firstMultiplier * secondMultiplier;

        Candidate selectedCandidate = currentElections.getCandidateWithId(selectedCandidateId);
        System.out.println("Multiply result - " + selectedCandidateId + " is candidate id of " + selectedCandidate.getName());

        System.out.println(selectedCandidate);

        voterIdCandidateIdMap.put(voterId, selectedCandidate);

        if (candidateIdVotesAmountMap.get(selectedCandidate) == null) {
            candidateIdVotesAmountMap.put(selectedCandidate, 1);
        } else {
            Integer voteAmount = candidateIdVotesAmountMap.get(selectedCandidate);
            candidateIdVotesAmountMap.put(selectedCandidate, ++voteAmount);
        }
    }

    public void endElections(Elections elections) {
        System.out.println("Results:");
        System.out.println(candidateIdVotesAmountMap);
        System.out.println(voterIdCandidateIdMap);
        System.out.println(getCandidateWithTheMostVotes().getName() + " is a winner with " + candidateIdVotesAmountMap.get(getCandidateWithTheMostVotes()) +" votes!");
    }

    public Candidate getCandidateWithTheMostVotes() {
        List<Candidate> candidates = new ArrayList<>(candidateIdVotesAmountMap.keySet());
        Candidate winner = candidates.get(0);

        for (Candidate candidate: candidateIdVotesAmountMap.keySet()) {
            if (candidateIdVotesAmountMap.get(candidate) > candidateIdVotesAmountMap.get(winner)) {
                winner = candidate;
            }
        }
        return winner;
    }

    public boolean haveTwoMessagesWithOneVoter(Integer voterId) {
        return savedMessages.stream()
                .filter(savedMessage -> Objects.equals(savedMessage.getVoterId(), voterId))
                .count() > 1;
    }


    public boolean haveTwoMessagesWithAnyOneVoter() {
        return savedMessages.stream()
                .map(SavedMessage::getVoterId)
                .count() > 1;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

}
