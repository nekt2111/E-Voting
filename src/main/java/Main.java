import eds.ElectronicDigitalSignature;
import eds.ElectronicDigitalSignatureRsa;
import message.coder.GammingMessageCoder;
import message.coder.MessageCoder;
import util.RandomPrimaryNumberGenerator;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', ' '};

        MessageCoder messageCoder = new GammingMessageCoder();
        String codedMessage = messageCoder.encode("hello my name is nikita", 23942874);
        System.out.println(codedMessage);
        String decodedMessage = messageCoder.decode(codedMessage, 23942874);
        System.out.println(decodedMessage);

        ElectronicDigitalSignature eds = new ElectronicDigitalSignatureRsa();
        long[] edsArray = eds.generateEds();

        System.out.println(Arrays.toString(edsArray));
        System.out.println(eds.checkEds(edsArray, eds.getOpenKey()));

    }
}
