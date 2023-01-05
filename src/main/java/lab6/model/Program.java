package lab6.model;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class Program {
    public LoginInfo loginInfo;
    public Token token;

    public void vote(Candidate candidate) {
        String candidateAsString = candidate.toString();

        byte[] candidateBytes = candidateAsString.getBytes(StandardCharsets.UTF_8);

        byte[] encryptedCandidateBytes = Encryptor.apply(candidateBytes, token.publicKey);

        System.out.println("Encrypted candidate - " + Arrays.toString(encryptedCandidateBytes));

        ElectionCommission.receive(new Bulletin(encryptedCandidateBytes, token.electorId));
    }
}
