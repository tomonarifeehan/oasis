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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
