package lab6.model;

import lombok.ToString;

import java.util.Arrays;

public abstract class Key {
    public final byte[] value;

    public Key(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Arrays.toString(value);
    }
}

