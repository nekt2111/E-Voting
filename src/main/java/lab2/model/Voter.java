package lab2.model;

import lombok.Data;
import message.coder.RsaCoder;
import model.Key;
import model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
public class Voter {
    private Integer id;
    private Person person;

    private Bulletin bulletin;
    private List<Bulletin> bulletinListToVote;
    private String codedBulletin;

    private long[] cecEds;
    private Key cecKey;
    private List<MessageSet> messageSets;

    private final static int amountOfMessageSets = 10;

    private CentralElectionCommission centralElectionCommissionToSendVote;


    private RsaCoder messageCoder = new RsaCoder();

    public Voter(Person person) {
        Random random = new Random();
        this.id = random.nextInt(10_000_000);
        this.person = person;
    }

    public List<MessageSet> generateMessageSets(List<Candidate> candidates) {
        List<MessageSet> messageSets = new ArrayList<>();

        for (int i = 0; i < amountOfMessageSets; i++) {
            List<Bulletin> bulletins = createBulletinsForCandidates(candidates);
            MessageSet messageSet = createMessageSetForBulletins(bulletins, i);
            messageSets.add(messageSet);
        }

        System.out.println("Generated " + messageSets.size() + " message sets for voter with id - " + id);
        this.messageSets = messageSets;
        return messageSets;
    }

    public void sendMaskedMessageSetsToCec(Elections elections) {
        List<long[]> maskedMessagedSets = getMaskedMessageSets();
        centralElectionCommissionToSendVote.receiveMaskedMessageSets(elections, maskedMessagedSets, messageCoder.getPrivateKey());
    }

    public List<long[]> getMaskedMessageSets() {

        List<long[]> maskedMessageSets = new ArrayList<>();

        for (MessageSet set : messageSets) {
            long[] codedMessageSet = messageCoder.code(set.toPreCodedString());
            maskedMessageSets.add(codedMessageSet);
        }

        System.out.println("Coded message sets:");
        maskedMessageSets.forEach(set -> System.out.print(Arrays.toString(set) + ", "));
        System.out.println("");

        return maskedMessageSets;
    }

    public void receivedMessageSetFromCec(long[] codedMessageSet, long[] eds) {

        System.out.println("Received coded message set with bulletin to vote " + Arrays.toString(codedMessageSet) + " eds - " + Arrays.toString(eds));

        String decodedMessageSet = messageCoder.decode(codedMessageSet, messageCoder.getPrivateKey());

        System.out.println("Decoded bulletin - " + decodedMessageSet);

        Integer numberOfMessageSet = Integer.valueOf(decodedMessageSet.split("\\.")[1]);
        MessageSet messageSet = messageSets.get(numberOfMessageSet);

        bulletinListToVote = messageSet.getBulletins();
        this.cecEds = eds;
    }

    public void vote(Candidate candidate, Elections elections) {
        generateMessageSets(elections.getCandidates());
        sendMaskedMessageSetsToCec(elections);
        selectCandidate(candidate);
        sendEncryptedBulletinToCec(messageCoder.code(bulletin.toPreCodedString()), elections);
    }

    private void selectCandidate(Candidate candidate) {
        if (bulletinListToVote != null) {
            bulletin = bulletinListToVote.stream().filter(bulletin -> bulletin.getCandidate() == candidate).findFirst().get();
        } else {
            throw new IllegalStateException("We don't have bulletin to send!");
        }
    }

    private void sendEncryptedBulletinToCec(long[] codedBulletin, Elections elections) {
        System.out.println("Sending coded bulletin - " + Arrays.toString(codedBulletin));
        this.centralElectionCommissionToSendVote.receiveCodedBulletin(codedBulletin, messageCoder.getPrivateKey(), cecEds, elections);
    }


    private List<Bulletin> createBulletinsForCandidates(List<Candidate> candidates) {
        List<Bulletin> bulletins = new ArrayList<>();

        for (Candidate candidate : candidates) {
            Bulletin bulletin = new Bulletin(id, candidate);
            bulletins.add(bulletin);
        }

        return bulletins;
    }

    private MessageSet createMessageSetForBulletins(List<Bulletin> bulletins, Integer number) {
        return new MessageSet(id, bulletins, number);
    }
}
