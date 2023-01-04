package lab1.model;

import eds.ElectronicDigitalSignature;
import eds.ElectronicDigitalSignatureElGamal;
import lombok.Data;
import message.coder.GammingMessageCoder;
import message.coder.MessageCoder;
import model.Person;

import java.util.Arrays;

@Data
public class Voter {
    private Integer id;
    private Person person;

    private Bulletin bulletin;
    private String codedBulletin;
    private int cecKey;


    private static MessageCoder messageCoder = new GammingMessageCoder();
    private static ElectronicDigitalSignature electronicDigitalSignature = new ElectronicDigitalSignatureElGamal();

    public Voter(Integer id, Person person) {
        this.id = id;
        this.person = person;
        this.bulletin = new Bulletin(id);
    }

    public void vote(Elections elections, Candidate candidate) {
        selectCandidate(candidate);
        signBulletin();
        codeBulletin();
        sendCodedBulletin(elections);
    }

    public void signBulletin() {
        long[] eds = electronicDigitalSignature.generateEds(bulletin.toPreCodedString());
        bulletin.setEds(electronicDigitalSignature.generateEds(bulletin.toPreCodedString()));
        System.out.println("Bulletin was signed with eds - " + Arrays.toString(eds));
    }

    public void sendCodedBulletin(Elections elections) {
        if (bulletin != null) {
            elections.receiveCodedBulletinFromVoter(codedBulletin, id, electronicDigitalSignature.getOpenKey());
        } else {
            throw new IllegalStateException("You don't have bulletin to send!");
        }
    }

    private void codeBulletin() {
        String preCodedBulletin = String.format("%d,%s,%d.%s", bulletin.getVoterId(), bulletin.getSelectedCandidate().getName(), cecKey, Arrays.toString(bulletin.getEds()));
        this.codedBulletin = messageCoder.encode(preCodedBulletin, cecKey);
        System.out.println("Coded bulletin - " + this.codedBulletin);
    }

    private void selectCandidate(Candidate candidate) {
        bulletin.setSelectedCandidate(candidate);
    }
}
