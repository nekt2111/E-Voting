package lab1.model;

import lombok.Data;
import message.coder.GammingMessageCoder;
import message.coder.MessageCoder;

import java.util.List;
import java.util.Map;

@Data
public class Elections {

    private List<Candidate> candidates;
    private List<Voter> voters;

    private List<Bulletin> bulletins;

    private Map<Integer, Integer> voterPublicKeyMap;

    private static int MIN_AMOUNT_OF_CANDIDATES = 2;
    private static int MIN_AMOUNT_OF_VOTERS = 2;

    private static MessageCoder messageCoder = new GammingMessageCoder();

    public Elections(List<Candidate> candidates,
                     List<Voter> voters) {

        if (isCandidatesListValid(candidates) && isVotersListValid(voters)) {
            this.candidates = candidates;
            this.voters = voters;
        } else {
            throw new IllegalArgumentException("Candidates/voters lists is not valid");
        }
    }

    public void receiveCodedBulletinFromVoter(String codedBulletin, Integer voterId) {
        String str = messageCoder.decode(codedBulletin, voterPublicKeyMap.get(voterId));
        if (str.contains(String.valueOf(voterPublicKeyMap.get(voterId)))) {
            String[] strs = str.split(",");
            Bulletin bulletin = new Bulletin(Integer.parseInt(strs[0]), candidates);
            bulletin.setSelectedCandidate(candidates.stream().filter(candidate -> candidate.getName().equals(strs[1])).findFirst().get());
            bulletins.add(bulletin);
        }
    }

    private static boolean isCandidatesListValid(List<Candidate> candidates) {
        return candidates != null && candidates.size() >= MIN_AMOUNT_OF_CANDIDATES;
    }

    private static boolean isVotersListValid(List<Voter> voters) {
        return voters != null && voters.size() >= MIN_AMOUNT_OF_VOTERS;
    }
}
