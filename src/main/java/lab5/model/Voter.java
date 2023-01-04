package lab5.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Voter {

    private Integer id;
    private PublicKey cecPublicKey;
    private KeyPair keyPair;

    public Voter(Integer id, PublicKey cecPublicKey) {
        this.id = id;
        this.cecPublicKey = cecPublicKey;

        keyPair = DsaEds.generateKeyPair();
    }

    public void vote(Candidate candidate, Elections elections) {
        int[] randomMultipliers = MyRandom.getRandomMultipliers(candidate.getId());

        int bulletin1 = randomMultipliers[0];
        String bulletin1Str = String.valueOf(bulletin1);

        int bulletin2 = randomMultipliers[1];
        String bulletin2Str = String.valueOf(bulletin2);

        byte[] encryptedBulletin1 = MyRsaCipher.encrypt(bulletin1Str, cecPublicKey);
        byte[] encryptedBulletin2 = MyRsaCipher.encrypt(bulletin2Str, cecPublicKey);

        Message message1 = new Message(encryptedBulletin1, id);
        Message message2 = new Message(encryptedBulletin2, id);

        String message1Str = message1.toSignStr();
        String message2Str = message2.toSignStr();

        byte[] edsForMessage1 = DsaEds.sign(message1Str.getBytes(StandardCharsets.UTF_8), keyPair.getPrivate());
        byte[] edsForMessage2 = DsaEds.sign(message2Str.getBytes(StandardCharsets.UTF_8), keyPair.getPrivate());

        message1.setEds(edsForMessage1);
        message2.setEds(edsForMessage2);
        List<Message> messages = List.of(message1, message2);

        for (int i = 0; i < messages.size(); i++) {
            elections.getElectionCommissions().get(i).receiveMessage(messages.get(i), keyPair.getPublic());
        }
    }
}
