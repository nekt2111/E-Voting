package lab5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
public class SavedMessage {

    private Integer voterId;
    private byte[] codedBulletin;
    private Integer electionCommissionId;

}
