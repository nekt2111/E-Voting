package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Key {
    private long mainPart;
    private long generalPart;
}
