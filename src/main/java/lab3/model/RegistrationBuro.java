package lab3.model;

import lombok.Data;

import java.util.*;

@Data
public class RegistrationBuro {

    private final Map<Integer, Integer> voterIdRegistrationNumberMap;

    private static final Random random = new Random();
    private static final int MAX_REGISTRATION_NUMBER = 1_000_000;

    public RegistrationBuro() {
        voterIdRegistrationNumberMap = new HashMap<>();
    }

    public int generateRegistrationNumberForVoter(Integer voterId) {
        if (!voterIdRegistrationNumberMap.containsKey(voterId)) {
            int registrationNumber = random.nextInt(MAX_REGISTRATION_NUMBER);
            voterIdRegistrationNumberMap.put(voterId, registrationNumber);
            return registrationNumber;
        } else {
            return voterIdRegistrationNumberMap.get(voterId);
        }
    }

    public boolean verifyRegistrationNumberForVoter(Integer registrationNumber, Integer voterId) {
        int realRegNumber = voterIdRegistrationNumberMap.get(voterId);
        return realRegNumber == registrationNumber;
    }

    public void sendRegistrationNumberToCec(CentralElectionCommission centralElectionCommission) {
        List<Integer> registrationNumbers = new ArrayList<Integer>(voterIdRegistrationNumberMap.values());
        centralElectionCommission.receiveRegistrationNumbersList(registrationNumbers);
    }
}
