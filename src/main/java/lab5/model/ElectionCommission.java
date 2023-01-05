package lab5.model;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.*;

public class ElectionCommission {

    private Message message;
    private PublicKey publicKey;
    private String name;

    private Set<Integer> voterIdsWhoSentMessage;
    private ArrayList<SavedMessage> savedMessages;

    private Integer id;

    public ElectionCommission(String name, Integer id) {
        this.name = name;
        this.id = id;
        voterIdsWhoSentMessage = new HashSet<>();
        savedMessages = new ArrayList<>();
    }

    public void receiveMessage(Message message, PublicKey publicKey) {
        System.out.println(name + " received a message - " + message);

        this.message = message;
        this.publicKey = publicKey;
        Integer voterId = message.getVoterId();
        byte[] encryptedBulletin = message.getEncryptedBulletin();;

        byte[] messageEds = message.getEds();
        byte[] messageBytes = message.toSignStr().getBytes(StandardCharsets.UTF_8);
        System.out.println("Verifying eds - " + Arrays.toString(messageEds));

        boolean resultOfEdsVerify = DsaEds.verify(messageBytes, messageEds, publicKey);
        System.out.println("Result of verifying - " + resultOfEdsVerify);

        if (!voterIdsWhoSentMessage.contains(voterId)) {
            voterIdsWhoSentMessage.add(voterId);

            SavedMessage savedMessage = new SavedMessage(voterId, encryptedBulletin, id);
            savedMessages.add(savedMessage);
        }
    }

    public List<SavedMessage> publishResults() {
        System.out.println(savedMessages);
        return savedMessages;
    }
}
