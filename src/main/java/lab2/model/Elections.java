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

@Data
public class Elections {

    private List<Candidate> candidates;
    private List<Voter> voters;

    private List<Bulletin> bulletins;

    private Map<Integer, Integer> voterPublicKeyMap;

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

    public void receiveCodedBulletinFromVoter(String codedBulletin, Integer voterId, Key edsPublicKey) {
        String decodedBulletin = messageCoder.decode(codedBulletin, voterPublicKeyMap.get(voterId));
        System.out.println("Received decoded bulletin - " + decodedBulletin);
        if (containsVoterPublicKey(decodedBulletin, voterId)) {
           Bulletin receivedBulletin = deserializeDecodedBulletinStr(decodedBulletin);

           if (isEdsCorrect(receivedBulletin, edsPublicKey) && !wasBulletinFromVoterAlreadyReceived(receivedBulletin)) {
               saveBulletin(receivedBulletin);
           } else if (wasBulletinFromVoterAlreadyReceived(receivedBulletin)) {
               System.out.println("Bulletin was already received!");
           }
           else {
               System.out.println("Bulletin is ignored. Eds is not correct");
           }
        }
    }

    private boolean isEdsCorrect(Bulletin bulletin, Key publicKey) {
        boolean result = electronicDigitalSignature.checkEds(bulletin.toPreCodedString(), bulletin.getEds(), publicKey);
        System.out.println("Checking bulletin's eds with public key - " + publicKey + ". Result - " + result);
        return result;
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
