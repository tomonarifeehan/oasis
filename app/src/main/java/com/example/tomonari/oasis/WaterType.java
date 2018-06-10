package com.example.tomonari.oasis;
import java.io.Serializable;

public enum WaterType implements Serializable {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private final String waterType;

    WaterType(String accountType) {
        this.waterType = accountType;
    }

    public String getWaterType() {
        return waterType;
    }
}
