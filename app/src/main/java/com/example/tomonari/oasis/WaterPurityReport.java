package com.example.tomonari.oasis;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WaterPurityReport implements Serializable {
    public static final List<OverallCondition> legalOverallConditions = Arrays.asList(OverallCondition.values());

    private int reportNumber;
    private Date date;
    private String location;
    private OverallCondition overallCondition;
    private String submittedBy;
    private int virusPPM;
    private int contaminantPPM;
    private String uid;

    /**
     * Empty constructor for Firebase
     *
     */
    public WaterPurityReport() {
    }

    /**
     * Normal constructor for a WaterPurityReport
     *
     * @param reportNumber
     * @param date
     * @param location
     * @param overallCondition
     * @param submittedBy
     * @param virusPPM
     * @param contaminantPPM
     * @param uid
     */
    public WaterPurityReport(int reportNumber, Date date, String location,
                             OverallCondition overallCondition, String submittedBy,
                             int virusPPM, int contaminantPPM, String uid) {
        this.reportNumber = reportNumber;
        this.date = date;
        this.location = location;
        this.overallCondition = overallCondition;
        this.submittedBy = submittedBy;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        this.uid = uid;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OverallCondition getOverallCondition() {
        return overallCondition;
    }

    public void setOverallCondition(OverallCondition overallCondition) {
        this.overallCondition = overallCondition;
    }

    public int getVirusPPM() { return virusPPM; }

    public void setVirusPPM(int virusPPM) { this.virusPPM = virusPPM; }

    public int getContaminantPPM() { return contaminantPPM; }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return this.uid;
    }

    public void setContaminantPPM(int contaminantPPM) { this.contaminantPPM = contaminantPPM; }

    @Override
    public String toString() {
        return "WaterSourceReport{" +
                "reportNumber=" + reportNumber +
                ", date=" + date +
                ", location=" + location +
                ", waterCondition=" + overallCondition +
                ", virusPPM=" + virusPPM +
                ", contaminantPPM=" + contaminantPPM +
                ", submittedBy='" + submittedBy +
                ", uid='" + uid + '\'' +
                '}';
    }
}
