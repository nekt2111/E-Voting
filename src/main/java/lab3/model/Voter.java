package lab3.model;

import lombok.Data;
import message.coder.EdsSignature;
import message.coder.ElGamal;
import message.coder.ElGamalKeyPair;
import message.coder.ElGamalPublicKey;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
public class Voter {
    private int id;
    private int registrationNumber;
    private Bulletin bulletin;
    private int randomId;

    private PublicKey signPublicKey;
    private byte[] eds;

    private ElGamal elGamal;
    private ElGamalKeyPair elGamalKeyPair;

    public Voter(int id) {
        this.id = id;
        elGamal = new ElGamal();
        elGamalKeyPair = elGamal.generateKeyPair();
        this.randomId = new Random().nextInt(1_000_000);

    }

    public void getRegistrationNumber(RegistrationBuro registrationBuro) {
        registrationNumber = registrationBuro.generateRegistrationNumberForVoter(id);
    }

    public void setUpRandomId() {
        Random random = new Random();
        randomId = random.nextInt(1_000_000);
    }

    public void vote(Candidate candidate, Elections elections) {
        selectCandidate(candidate);
        Message message = createMessage();
        signMessage(message);
        System.out.println(message);
        List<List<Integer>> coded = codeMessage(message);
        System.out.println("Coded message - " + coded);
        sendCodedMessageToCec(coded, elections.getCec(), elections);
    }

    public void selectCandidate(Candidate candidate) {
        this.bulletin.selectCandidate(candidate);
    }

    public Message createMessage() {
        Message message = new Message();
        message.setBulletin(this.bulletin);
        message.setVoterRandomId(randomId);
        message.setRegistrationNumber(registrationNumber);
        return message;
    }

    public Message signMessage(Message message) {
        EdsSignature edsSignature = new EdsSignature();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.signPublicKey = keyPair.getPublic();
            System.out.println(message.toSignString());
            message.setEds(edsSignature.sign(message.toSignString(), keyPair.getPrivate()));
            this.eds = message.getEds();
            System.out.println("Message was signed with eds - " + Arrays.toString(this.eds));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return message;
    }

    public List<List<Integer>> codeMessage(Message message) {
        return elGamal.codeWithElGamal(message.toPreCodedString(), elGamal.generateKeyPair().getPrivateKey());
    }

    public void sendCodedMessageToCec(List<List<Integer>> codedMessage, CentralElectionCommission cec, Elections elections) {
        cec.receiveCodedMessageFromVoter(codedMessage, elGamalKeyPair.getPublicKey(), signPublicKey, elections, eds);
    }

}
