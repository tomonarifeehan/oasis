package com.example.tomonari.oasis;
import java.io.Serializable;

public enum WaterCondition implements Serializable {
    WASTE("Waste"),
    TREATABLECLEAR("Treatable-Clear"),
    TREATABLEMUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private final String waterCondition;

    WaterCondition(String waterCondition) {
        this.waterCondition = waterCondition;
    }

    public String getWaterCondition() {
        return waterCondition;
    }
}