package lab6.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Candidate {
    public String name;

    public String toString() {
        return name;
    }
}
