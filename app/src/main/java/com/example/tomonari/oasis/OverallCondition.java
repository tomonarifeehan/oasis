package com.example.tomonari.oasis;
import java.io.Serializable;

public enum OverallCondition implements Serializable {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String overallCondition;

    OverallCondition(String overallCondition) {

        this.overallCondition = overallCondition;
    }

    public String getOverallCondition() {

        return overallCondition;
    }
}
