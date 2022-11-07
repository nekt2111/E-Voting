import messageCoders.GammingMessageCoder;
import messageCoders.MessageCoder;
import util.RandomPrimaryNumberGenerator;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', ' '};

        MessageCoder messageCoder = new GammingMessageCoder(alphabet, 23942874);
        String codedMessage = messageCoder.encode("hello my name is nikita");
        System.out.println(codedMessage);
        String decodedMessage = messageCoder.decode(codedMessage);
        System.out.println(decodedMessage);

        ElectronicDigitalSignature eds = new ElectronicDigitalSignatureRsa(new RandomPrimaryNumberGenerator(10_000), alphabet);
        long[] edsArray = eds.generateEds();

        System.out.println(Arrays.toString(edsArray));
        System.out.println(eds.checkEds(edsArray, eds.getOpenKey()));

    }



}
