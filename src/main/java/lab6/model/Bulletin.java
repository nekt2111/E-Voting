package lab6.model;

public final class Bulletin {
    public final byte[] encryptedCandidateBytes;
    public final int electorId;

    public Bulletin(byte[] encryptedCandidateBytes, int electorId) {
        this.encryptedCandidateBytes = encryptedCandidateBytes;
        this.electorId = electorId;
    }
}
