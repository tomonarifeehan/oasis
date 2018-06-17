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

    public int getSource_count() {
        return source_count;
    }

    public void setSource_count(int source_count) {
        this.source_count = source_count;
    }

    public int getPurity_count() {
        return purity_count;
    }

    public void setPurity_count(int purity_count) {
        this.purity_count = purity_count;
    }
}
