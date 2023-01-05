package lab6.model;

import lombok.Data;

@Data
public final class ElectionInfo {
    public final Candidate candidate;
    public final int count;

    public ElectionInfo(Candidate candidate, int count) {
        this.candidate = candidate;
        this.count = count;
    }
}
