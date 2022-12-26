package lab3.model;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.Random;

@Data
public class Voter {
    private int id;
    private int registrationNumber;
    private Bulletin bulletin;
    private int randomId;

    public Voter(int id) {
        this.id = id;
    }

    public void getRegistrationNumber(RegistrationBuro registrationBuro) {
        registrationNumber = registrationBuro.generateRegistrationNumberForVoter(id);
    }

    public void setUpRandomId() {
        Random random = new Random();
        randomId = random.nextInt(1_000_000);
    }

    public void vote() {

    }

}
