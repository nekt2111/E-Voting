package lab4;

import lab4.model.Bulletin;
import lab4.model.Candidate;
import lab4.model.Voter;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Candidate> candidateList = MockDataGenerator.candidateList();
        Candidate firstCandidate = candidateList.get(0);
        Candidate secondCandidate = candidateList.get(1);

        List<Voter> voters = MockDataGenerator.voterList();
        List<PublicKey> publicKeys = new ArrayList<>();
        voters.forEach(voter -> publicKeys.add(voter.getPublicKey()));

        Voter voterA = voters.get(0);
        Voter voterB = voters.get(1);
        Voter voterC = voters.get(2);
        Voter voterD = voters.get(3);

        voterA.formBulletin(candidateList, firstCandidate);
        voterB.formBulletin(candidateList, firstCandidate);
        voterC.formBulletin(candidateList, secondCandidate);
        voterD.formBulletin(candidateList, firstCandidate);

        voterD.prepareToSend(voters);
        voterB.prepareToSend(voters);
        voterC.prepareToSend(voters);
        voterA.prepareToSend(voters);

        voterD.sendBulletinToVoter(voterA);
        voterB.sendBulletinToVoter(voterA);
        voterC.sendBulletinToVoter(voterA);

        voterA.decryptWithPrivateKey();
        voterA.shuffleBulletins();

        voterA.sendBulletinsToVoter(voterB);
        voterB.decryptWithPrivateKey();

        voterB.sendBulletinToVoter(voterC);
        voterC.decryptWithPrivateKey();

        voterC.sendBulletinToVoter(voterD);
        voterD.decryptWithPrivateKey();
        voterD.sendBulletinToVoter(voterA);

        voterA.signEds();
        voterA.shuffleBulletins();
        voterA.sendBulletinToVoter(voterB);
        voterA.sendBulletinToVoter(voterC);
        voterA.sendBulletinToVoter(voterD);

        voterB.decryptAndDeleteEds();
        voterB.signEds();
        voterB.sendBulletinsToVoter(voterA);
        voterB.sendBulletinsToVoter(voterC);
        voterB.sendBulletinsToVoter(voterD);

        voterC.decryptAndDeleteEds();
        voterC.signEds();
        voterC.sendBulletinsToVoter(voterA);
        voterC.sendBulletinsToVoter(voterB);
        voterC.sendBulletinsToVoter(voterD);

        voterD.decryptAndDeleteEds();
        voterD.signEds();
        voterD.sendBulletinsToVoter(voterA);
        voterD.sendBulletinsToVoter(voterB);
        voterD.sendBulletinsToVoter(voterD);
        Voter.removeAllRandomStringFromBulletins();

        List<Bulletin> bulletins = Voter.bulletins;
        getResults(bulletins);
    }

    private static void getResults(List<Bulletin> bulletins) {
        HashMap<Candidate, Integer> votes = new HashMap<>();

        for (Bulletin bulletin : bulletins) {
            if (!votes.containsKey(bulletin.getSelectedCandidate())) {
                votes.put(bulletin.getSelectedCandidate(), 1);
            } else {
                int amountOfVotes = votes.get(bulletin.getSelectedCandidate());
                votes.replace(bulletin.getSelectedCandidate(), amountOfVotes + 1);
            }
        }

        Candidate winnerCandidate = null;
        int winnerVotes = 0;

        for (Candidate potentialWinner : votes.keySet()) {
            int potentialWinnerVotes = votes.get(potentialWinner);
            if (potentialWinnerVotes > winnerVotes) {
                winnerVotes = potentialWinnerVotes;
                winnerCandidate = potentialWinner;
            }
        }

        System.out.println("Winner is - " + winnerCandidate.getName() + " with " + winnerVotes + "  of votes!");
    }

}
