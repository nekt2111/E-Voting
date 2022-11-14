package lab2.model;

import lombok.Data;

import java.util.List;

@Data
public class Bulletin {
    private Candidate candidate;

    private long[] eds;

    public Bulletin(Candidate candidate) {
        this.candidate = candidate;
    }

    public void setEds(long[] eds) {
        if (eds != null) {
            this.eds = eds;
        }
    }

    public String toPreCodedString() {
        return null;
    }
}
