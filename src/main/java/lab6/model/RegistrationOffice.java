package lab6.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RegistrationOffice {
    private static Token[] tokens;
    private static ArrayList<LoginInfo> loginInfos = new ArrayList<>();
    private static int tokensGiven;

    public static void receive(Voter[] voters) {
        int votersCount = voters.length;

        System.out.println("Received voters - " + Arrays.toString(voters));

        int[] votersIds = new int[votersCount];

        for (int i = 0; i < votersCount; i++) {
            votersIds[i] = voters[i].hashCode();
        }

        System.out.println("Generated votersId - " + Arrays.toString(votersIds));

        ElectionCommission.receive(votersIds);
    }

    public static void receive(Token[] tokens) {
        RegistrationOffice.tokens = tokens;
        System.out.println("Received tokens!");
    }

    public static void registrate(Voter voter) {
        Random random = new Random();

        String login = ((Integer) random.nextInt()).toString();
        String password = ((Integer) random.nextInt()).toString();

        var loginInfo = new LoginInfo(login, password);
        System.out.println("Generated information to log in " + loginInfo);
        loginInfos.add(loginInfo);
        voter.receive(loginInfo);

        voter.receive(tokens[tokensGiven]);
        tokensGiven++;
    }
}
