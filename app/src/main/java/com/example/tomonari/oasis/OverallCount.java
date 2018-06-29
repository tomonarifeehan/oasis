package com.example.tomonari.oasis;

public class OverallCount {
    private int source_count;
    private int purity_count;

    public OverallCount() {

    }

    public OverallCount(int source_count, int purity_count) {
        this.source_count = source_count;
        this.purity_count = purity_count;
    }

    public int getSourceCount() {
        return source_count;
    }

    public void setSourceCount(int source_count) {
        this.source_count = source_count;
    }

    public int getPurityCount() {
        return purity_count;
    }

    public void setPurityCount(int purity_count) {
        this.purity_count = purity_count;
    }
}
