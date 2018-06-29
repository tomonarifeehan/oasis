package com.example.tomonari.oasis;

public class Count {
    private int source_count;
    private int purity_count;
    private String uid;

    public Count() {

    }

    public Count(int source_count, int purity_count, String uid) {
        this.source_count = source_count;
        this.purity_count = purity_count;
        this.uid = uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
