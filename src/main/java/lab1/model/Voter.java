package lab1.model;

import eds.ElectronicDigitalSignature;
import eds.ElectronicDigitalSignatureRsa;
import lombok.Data;
import message.coder.GammingMessageCoder;
import message.coder.MessageCoder;

@Data
public class Voter {
    private Integer id;
    private Person person;

    private Bulletin bulletin;
    private String codedBulletin;
    private int cecKey;


    private static MessageCoder messageCoder = new GammingMessageCoder();
    private static ElectronicDigitalSignature electronicDigitalSignature = new ElectronicDigitalSignatureRsa();

    public Voter(Integer id, Person person) {
        this.id = id;
        this.person = person;
    }

    public void vote(Elections elections, Candidate candidate) {
        selectCandidate(candidate);
        signBulletin();
        codeBulletin();
        sendCodedBulletin(elections);
    }

    public void signBulletin() {
        bulletin.setEds(electronicDigitalSignature.generateEds());
    }

    public void sendCodedBulletin(Elections elections) {
        if (bulletin != null) {
            elections.receiveCodedBulletinFromVoter(codedBulletin, id);
        } else {
            throw new IllegalStateException("You don't have bulletin to send!");
        }
    }

    private void codeBulletin() {
        String preCodedBulletin = String.format("%d,%s,%d", bulletin.getVoterId(), bulletin.getSelectedCandidate().getName(), cecKey);
        this.codedBulletin = messageCoder.encode(preCodedBulletin, cecKey);
        System.out.println(this.codedBulletin);
    }

    private void selectCandidate(Candidate candidate) {
        bulletin.setSelectedCandidate(candidate);
    }
}
