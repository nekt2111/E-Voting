package lab3.model;

import lombok.Data;
import message.coder.EdsSignature;
import message.coder.ElGamal;
import message.coder.ElGamalPublicKey;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.*;

@Data
public class CentralElectionCommission {

    private List<Integer> registrationNumbers;

    private List<Integer> registrationNumberWhoVoted;

    private Map<Integer, Candidate> voterIdCandidateMap;

    private Map<Candidate, Integer> candidateVotesMap;

    public Elections createElections(List<Voter> voters,
                                     List<Candidate> candidates,
                                     RegistrationBuro registrationBuro) {

        for (Voter voter : voters) {
            voter.setBulletin(new Bulletin(candidates));
        }

        return new Elections(voters, candidates, registrationBuro, this);
    }

    public void receiveRegistrationNumbersList(List<Integer> registrationNumbers) {
        System.out.println("Registration numbers - " + registrationNumbers);
        this.registrationNumbers = registrationNumbers;
    }

    public void startElections(Elections elections) {
        setUpVotersRegistrationNumbers(elections);
        receiveRegistrationNumbersFromBuro(elections.getRegistrationBuro());
        registrationNumberWhoVoted = new ArrayList<>();
        voterIdCandidateMap = new HashMap<>();
        candidateVotesMap = new HashMap<>();
        for (Candidate candidate : elections.getCandidates()) {
            candidateVotesMap.put(candidate, 0);
        }

    }

    private void setUpVotersRegistrationNumbers(Elections elections) {
        List<Voter> voters = elections.getVoters();
        for (Voter voter : voters) {
            voter.getRegistrationNumber(elections.getRegistrationBuro());
        }
    }

    private void receiveRegistrationNumbersFromBuro(RegistrationBuro registrationBuro) {
        registrationBuro.sendRegistrationNumberToCec(this);
    }

    public void receiveCodedMessageFromVoter(List<List<Integer>> codedMessage, ElGamalPublicKey key, PublicKey publicKey, Elections elections, byte[] eds) {
        String messageStr = decodeMessage(codedMessage, key);
        System.out.println("Decoded message - " + messageStr);
        Message message = deserializeMessage(messageStr, elections, eds);
        System.out.println("Deserialized message - " + message);
        if (isEdsValid(message, publicKey)) {
            processNewMessage(message);
        } else {
            System.out.println("Eds in not valid!");
        }
    }

    private void processNewMessage(Message message) {
        int voterRegistrationNumber = message.getRegistrationNumber();
        System.out.println(voterRegistrationNumber);

        if (isVoterInRegistrationList(voterRegistrationNumber)){
            removeRegistrationNumberFromList(voterRegistrationNumber);
            addRegistrationNumberToListWhoVoted(voterRegistrationNumber);
            countVote(message.getBulletin().getSelectedCandidate(), message.getVoterRandomId());
        } else {
            System.out.println("Voter is not in registration number list - " + message.getRegistrationNumber());
        }
    }

    private void removeRegistrationNumberFromList(int registrationNumber) {
        System.out.println("Removing registration number - " + registrationNumber);
        registrationNumbers.remove(registrationNumbers.indexOf(registrationNumber));
    }

    private void addRegistrationNumberToListWhoVoted(int registrationNumber) {
        System.out.println("Adding registration number - " + registrationNumber + " to list, who already voted");
        registrationNumberWhoVoted.add(registrationNumber);
    }

    private void countVote(Candidate selectedCandidate, int voterId) {
        voterIdCandidateMap.put(voterId, selectedCandidate);
        Integer currentAmountOfVotes = candidateVotesMap.get(selectedCandidate);
        candidateVotesMap.put(selectedCandidate, ++currentAmountOfVotes);
    }

    private Candidate getCandidateWithMostVotes() {
        List<Candidate> candidates = new ArrayList<>(candidateVotesMap.keySet());
        Candidate winner = candidates.get(0);

        for (Candidate candidate: candidateVotesMap.keySet()) {
            if (candidateVotesMap.get(candidate) > candidateVotesMap.get(winner)) {
                winner = candidate;
            }
        }
        return winner;
    }

    private boolean isVoterInRegistrationList(int voterRegistrationNumber) {
        return registrationNumbers.contains(voterRegistrationNumber);
    }

    private String decodeMessage(List<List<Integer>> codedMessage, ElGamalPublicKey key) {
        ElGamal elGamal = new ElGamal();
        elGamal.generateKeyPair();
        return elGamal.decodeWithElGamal(codedMessage, key);
    }

    public Message deserializeMessage(String decodedMessage, Elections elections, byte[] eds) {
        String[] splitedMessage = decodedMessage.split("\\.");
        int randomId = Integer.parseInt(splitedMessage[0]);
        int registrationNumber = Integer.parseInt(splitedMessage[1]);
        Candidate selectedCandidate = findCandidate(elections, splitedMessage[2]);
        Bulletin bulletin = new Bulletin(elections.getCandidates());
        bulletin.setSelectedCandidate(selectedCandidate);
        Message message = new Message();
        message.setVoterRandomId(randomId);
        message.setRegistrationNumber(registrationNumber);
        message.setBulletin(bulletin);
        message.setEds(eds);
        System.out.println(message);
        return message;
    }

    private boolean isEdsValid(Message message, PublicKey publicKey) {
        EdsSignature edsSignature = new EdsSignature();
        return edsSignature.checkSign(message, publicKey);
    }

    private Candidate findCandidate(Elections elections, String name) {
        for (Candidate candidate : elections.getCandidates()) {
            if (Objects.equals(candidate.getName(), name)) {
                return candidate;
            }
        }
        return null;
    }

    public void endElections(Elections elections) {
        System.out.println("Map who voted for whom:");
        System.out.println(voterIdCandidateMap);
        System.out.println("Winner is - " + getCandidateWithMostVotes().getName());
    }
}
