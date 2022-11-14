package lab2.model;

import eds.ElectronicDigitalSignature;
import eds.ElectronicDigitalSignatureRsa;
import lombok.Data;
import message.coder.GammingMessageCoder;
import message.coder.MessageCoder;
import model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Voter {
    private Integer id;
    private Person person;

    private Bulletin bulletin;
    private String codedBulletin;
    private int cecKey;

    private final static int amountOfMessageSets = 10;


    private static MessageCoder messageCoder = new GammingMessageCoder();
    private static ElectronicDigitalSignature electronicDigitalSignature = new ElectronicDigitalSignatureRsa();

    public Voter(Person person) {
        Random random = new Random();
        this.id = random.nextInt(10_000_000);
        this.person = person;
    }

    public List<MessageSet> generateMessageSets(List<Candidate> candidates) {
        List<MessageSet> messageSets = new ArrayList<>();

        for (int i = 0; i < amountOfMessageSets; i++) {
            List<Bulletin> bulletins = createBulletinsForCandidates(candidates);
            MessageSet messageSet = createMessageSetForBulletins(bulletins);
            messageSets.add(messageSet);
        }

        return messageSets;
    }

    public void vote(Elections elections, Candidate candidate) {

    }

    public void signBulletin() {

    }

    public void sendCodedBulletin(Elections elections) {

    }

    private void codeBulletin() {

    }

    private void selectCandidate(Candidate candidate) {

    }

    private List<Bulletin> createBulletinsForCandidates(List<Candidate> candidates) {
        List<Bulletin> bulletins = new ArrayList<>();

        for (Candidate candidate : candidates) {
            Bulletin bulletin = new Bulletin(candidate);
            bulletins.add(bulletin);
        }

        return bulletins;
    }

    private MessageSet createMessageSetForBulletins(List<Bulletin> bulletins) {
        return new MessageSet(id, bulletins);
    }
}
