package lab2.model;

import eds.ElectronicDigitalSignature;
import eds.ElectronicDigitalSignatureRsa;
import lombok.Data;
import message.coder.GammingMessageCoder;
import message.coder.MessageCoder;
import model.Key;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class Elections {

    private List<Candidate> candidates;
    private List<Voter> voters;

    private List<Bulletin> bulletins;

    private Map<Integer, Integer> voterPublicKeyMap;

    private List<Integer> votedIds;

    private static int MIN_AMOUNT_OF_CANDIDATES = 2;
    private static int MIN_AMOUNT_OF_VOTERS = 8;

    private static MessageCoder messageCoder = new GammingMessageCoder();

    private static ElectronicDigitalSignature electronicDigitalSignature = new ElectronicDigitalSignatureRsa();

    public Elections(List<Candidate> candidates,
                     List<Voter> voters) {

        if (isCandidatesListValid(candidates) && isVotersListValid(voters)) {
            this.candidates = candidates;
            this.voters = voters;
        } else {
            throw new IllegalArgumentException("Candidates/voters lists is not valid");
        }
    }

    public Voter findVoterById(Integer voterId) {
        return this.voters.stream().filter(voter -> Objects.equals(voter.getId(), voterId)).findFirst().get();
    }


    private boolean isEdsCorrect(Bulletin bulletin, Key publicKey) {
        return true;
    }

    private boolean wasBulletinFromVoterAlreadyReceived(Bulletin receivedBulletin) {
        return false;
    }

    private void saveBulletin(Bulletin bulletin) {
        bulletins.add(bulletin);
        System.out.println("Bulletin was saved");
    }

    private boolean containsVoterPublicKey(String decodedBulletin, Integer voterId) {
       String publicKey = String.valueOf(voterPublicKeyMap.get(voterId));
       return decodedBulletin.contains(publicKey);
    }

    private Bulletin deserializeDecodedBulletinStr(String decodedBulletin) {
        return null;
    }

    private long[] getEdsFromDecodedBulletin(String decodedBulletin) {
        String edsArray = decodedBulletin.split("\\.")[1];
        String edsSequence = edsArray.replace("[", "").replace("]","").replace(" ", "");
        return Arrays.stream(edsSequence.split(",")).mapToLong(Long::parseLong).toArray();
    }

    private static boolean isCandidatesListValid(List<Candidate> candidates) {
        return candidates != null && candidates.size() >= MIN_AMOUNT_OF_CANDIDATES;
    }

    private static boolean isVotersListValid(List<Voter> voters) {
        return voters != null && voters.size() >= MIN_AMOUNT_OF_VOTERS;
    }
}
