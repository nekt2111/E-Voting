package lab6.model;

import lab6.src.Main;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ElectionCommission {
    private static HashMap<Integer, PrivateKey> electorsKeys;

    private static ArrayList<Bulletin> collectedBulletins = new ArrayList<>();

    public static void receive(int[] electorIds) {
        KeyPair keyPair = KeyGenerator.Generate();

        electorsKeys = new HashMap<>();

        for (int electorId : electorIds) {
            electorsKeys.put(electorId, keyPair.privateKey);
        }

        System.out.println("Generated keys - " + keyPair);

        var tokens = new Token[electorIds.length];

        for (int i = 0; i < electorIds.length; i++) {
            tokens[i] = new Token(electorIds[i], keyPair.publicKey);
        }

        System.out.println("Generated tokens - " + Arrays.toString(tokens));

        RegistrationOffice.receive(tokens);
    }

    public static void receive(Bulletin bulletin) {
        collectedBulletins.add(bulletin);
        System.out.println("Received bulletin from voter!");
    }

    public static void publicateResults() {
        System.out.println("Starting to get results!");
        HashMap<Candidate, Integer> votes = new HashMap<>();

        for (Bulletin collectedBulletin : collectedBulletins) {
            PrivateKey privateKey = electorsKeys.get(collectedBulletin.electorId);

            byte[] candidateBytes = Encryptor.apply(collectedBulletin.encryptedCandidateBytes, privateKey);
            String candidateStr = new String(candidateBytes, StandardCharsets.UTF_8);
            System.out.println("Decrypted candidate - " + candidateStr);

            Candidate candidate = Arrays.stream(Main.candidates)
                    .filter(c -> Objects.equals(c.getName(), candidateStr))
                    .findFirst()
                    .orElse(null);

            if (votes.containsKey(candidate)) {
                votes.put(candidate, votes.get(candidate) + 1);
            } else {
                votes.put(candidate, 1);
            }
        }

        for (Candidate candidate : Main.candidates) {
            if (!votes.containsKey(candidate)) {
                votes.put(candidate, 0);
            }
        }

        ArrayList<ElectionInfo> electionInfos = new ArrayList<>();

        for (Candidate candidate : votes.keySet()) {
            electionInfos.add(new ElectionInfo(candidate, votes.get(candidate)));
        }

        System.out.println("Results:");

        electionInfos.sort((a, b) -> ((Integer)b.count).compareTo(a.count));
        System.out.println(electionInfos);

        System.out.println("Winner is " + electionInfos.get(0).getCandidate() + " with " + electionInfos.get(0).getCount() + " votes!");
    }
}
