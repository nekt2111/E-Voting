package lab5.model;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

public class ElectionCommission {

    private Message message;
    private PublicKey publicKey;
    private String name;

    private Set<Integer> voterIdsWhoSentMessage;

    public ElectionCommission(String name) {
        this.name = name;
        voterIdsWhoSentMessage = new HashSet<>();
    }

    public void receiveMessage(Message message, PublicKey publicKey) {
        this.message = message;
        this.publicKey = publicKey;
        System.out.println(name + " receive a message - " + message);
        System.out.println("Check eds - " + DsaEds.verify(message.toSignStr().getBytes(StandardCharsets.UTF_8), message.getEds(), publicKey));
        //todo: refactor add to set
    }
}
