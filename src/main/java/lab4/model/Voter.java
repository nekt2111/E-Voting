package lab4.model;

import lab4.ElGamalEds;
import lab4.MyRsaCipher;
import lombok.Data;
import util.DataConfiguration;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.*;

@Data
public class Voter {

    private KeyPair keyPair;

    private String name;

    private static final char[] alphabet = DataConfiguration.alphabet;

    private Cipher cipher;

    private static final Random random = new Random();

    private static final int amountOfCharsInAddedMsg = 5;

    private Integer id;

    private static List<String> randomAddedStrings;

    private Bulletin bulletin;

    public static List<Bulletin> bulletins = new ArrayList<>();

    private static List<String> messages = new ArrayList<>();
    private static List<byte[]> codedMessages = new ArrayList<>();

    private String codedMessage;

    private byte[] codedBytesD;
    private byte[] codedBytesB;
    private byte[] codedBytesC;
    private byte[] codedBytesA;

    private String message;

    public Voter(Integer id, String name) {
        this.id = id;
        this.name = name;
        randomAddedStrings = new ArrayList<>();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1028);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            System.out.println("Exception occurred - " + e);
        }
    }

    public static void removeAllRandomStringFromBulletins() {
        randomAddedStrings.clear();
        bulletins.forEach(bulletin -> System.out.println("Bulletin with removed random str - " + bulletin.toPreCodedString()));
    }

    public void formBulletin(List<Candidate> candidateList, Candidate selectedCandidate) {
        bulletin = new Bulletin(candidateList, id);
        bulletin.selectCandidate(selectedCandidate);
        System.out.println("Bulletin was formed for voter " + name);
    }

    public void prepareToSend(List<Voter> voters) {
        String stringToCode = getBulletinStrWithAddedRandomStr();
        message = stringToCode;
        bulletins.add(bulletin);

        List<PublicKey> publicKeys = new ArrayList<>();
        voters.forEach(voter -> publicKeys.add(voter.getPublicKey()));

        byte[] firstResult = processCodingWithPublicKeysWithNoAddingRandomString(stringToCode, publicKeys);
        byte[] secondResult = processCodingWithPublicKeysWithAddingRandomString(new String(firstResult, StandardCharsets.UTF_8), publicKeys);

        codedMessage = new String(secondResult, StandardCharsets.UTF_8);
        codedMessages.add(secondResult);
    }

    public void sendBulletinToVoter(Voter voter) {
        System.out.println(this.getName() + " sent message to voter - " + voter.getName());
        messages.add(message);
    }

    public void sendBulletinsToVoter(Voter voter) {
        System.out.println(this.getName() + " sent bulletins to voter - " + voter.getName());
    }

    public void signEds() {
        long eds[] = ElGamalEds.generateEds(this.bulletin.toPreCodedString());
        this.bulletin.setEds(eds);
        System.out.println("Bulletins were signed with eds - " + Arrays.toString(eds));
    }

    private void removeEdsAndDecrypt(List<Bulletin> bulletins) {
        for (Bulletin bulletin: bulletins) {
            bulletin.removeEds();
        }
    }

    private void deleteRandomStringFromMessage(String message) {
        System.out.println("Deleting random message!");
        for (String random : randomAddedStrings) {
            randomAddedStrings.remove(random);
        }
    }

    public void shuffleBulletins() {
        System.out.println("Shuffled bulletins!");
         Collections.shuffle(bulletins);
    }

    public List<Bulletin> decryptWithPrivateKey() {
        for (int i = 0; i < codedMessages.size(); i++) {

            String decrypted = MyRsaCipher.decrypt(codedMessages.get(i), keyPair.getPrivate());
            randomAddedStrings.remove(0);
            String removed = decrypted.substring(0, decrypted.length() - 5);
            byte[] encrypted = MyRsaCipher.encrypt(removed, getPublicKey());
            codedMessages.remove(i);
            codedMessages.add(i, encrypted);

            System.out.println("Decrypted " + decrypted + ". Removed random");
        }

        return bulletins;
    }

    public void decryptAndDeleteEds() {
        byte[] decrypted = null;
        switch (name) {
            case "A":
                decrypted = codedBytesA;
                break;
            case "B":
                decrypted = codedBytesB;
                break;
            case "C":
                decrypted = codedBytesC;
                break;
            case "D":
                decrypted = codedBytesD;
                break;
        }
        System.out.println("Decrypted message - " + MyRsaCipher.decrypt(decrypted, keyPair.getPrivate()) + ". Eds removed!");
    }

    public Bulletin checkAndDeleteEds(Bulletin bulletin) {
        bulletin.removeEds();
        return bulletins.get(0);
    }

    private byte[] processCodingWithPublicKeysWithNoAddingRandomString(String stringToCode, List<PublicKey> publicKeys) {
        PublicKey aKey = publicKeys.get(0);
        PublicKey bKey = publicKeys.get(1);
        PublicKey cKey = publicKeys.get(2);
        PublicKey dKey = publicKeys.get(3);

        byte[] resultAfterD = processCodingWithPublicKey(stringToCode, dKey);
        codedBytesD = resultAfterD;
        System.out.println(Arrays.toString(resultAfterD));
        byte[] resultAfterC = processCodingWithPublicKey(new String(resultAfterD, StandardCharsets.UTF_8), cKey);
        codedBytesC = resultAfterC;
        byte[] resultAfterB = processCodingWithPublicKey(new String(resultAfterC, StandardCharsets.UTF_8), bKey);
        codedBytesB = resultAfterB;
        byte[] resultAfterA = processCodingWithPublicKey(new String(resultAfterB, StandardCharsets.UTF_8), aKey);
        codedBytesA = resultAfterA;

        return resultAfterA;
    }

    private byte[] processCodingWithPublicKeysWithAddingRandomString(String string, List<PublicKey> publicKeys) {
        PublicKey aKey = publicKeys.get(0);
        PublicKey bKey = publicKeys.get(1);
        PublicKey cKey = publicKeys.get(2);
        PublicKey dKey = publicKeys.get(3);

        byte[] resultAfterD = processCodingWithPublicKeyAndAddingRandomStr(string, dKey);
        byte[] resultAfterC = processCodingWithPublicKeyAndAddingRandomStr(new String(resultAfterD, StandardCharsets.UTF_8), cKey);
        byte[] resultAfterB = processCodingWithPublicKeyAndAddingRandomStr(new String(resultAfterC, StandardCharsets.UTF_8), bKey);
        byte[] resultAfterA = processCodingWithPublicKeyAndAddingRandomStr(new String(resultAfterB, StandardCharsets.UTF_8), aKey);

        return resultAfterA;
    }

    private byte[] processCodingWithPublicKeyAndAddingRandomStr(String string, PublicKey publicKey) {
        String randomString = getRandomString();
        byte[] randomStringBytes = randomString.getBytes(StandardCharsets.UTF_8);
        randomAddedStrings.add(randomString);
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        byte[] bytesToCode = new byte[string.length() + randomString.length()];

        System.arraycopy(bytes, 0, bytesToCode, 0, bytes.length);

        for (int i = bytes.length, j = 0; i < bytes.length + randomString.length(); i++, j++) {
            bytesToCode[i] = randomStringBytes[j];
        }

        return processCodingWithPublicKey(new String(bytesToCode, StandardCharsets.UTF_8), publicKey);
    }

    private byte[] processCodingWithPublicKey(String string, PublicKey publicKey) {
        return codeString(string, publicKey);
    }

    private String getBulletinStrWithAddedRandomStr() {
        return bulletin.toPreCodedString() + getRandomString();
    }

    private String getRandomString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < amountOfCharsInAddedMsg; i++) {
            stringBuilder.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return stringBuilder.toString();
    }

    private byte[] codeString(String string, PublicKey publicKey) {
       return MyRsaCipher.encrypt(string, publicKey);
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }
}
