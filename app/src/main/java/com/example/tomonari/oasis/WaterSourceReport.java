package com.example.tomonari.oasis;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WaterSourceReport implements Serializable {
    public static final List<WaterType> legalTypes = Arrays.asList(WaterType.values());
    public static final List<WaterCondition> legalConditions = Arrays.asList(WaterCondition.values());

    private int reportNumber;
    private Date date;
    private String location;
    private WaterType waterType;
    private WaterCondition waterCondition;
    private String submittedBy;
    private String uid;

    /**
     * Empty constructor for Firebase
     *
     */
    public WaterSourceReport() {

    }

    /**
     * Constructor for a WaterSourceReport
     *
     * @param reportNumber report number
     * @param date date
     * @param location location
     * @param waterType water type
     * @param waterCondition water condition
     * @param submittedBy submitted by
     */
    public WaterSourceReport(int reportNumber, Date date, String location,
                             WaterType waterType, WaterCondition waterCondition,
                             String submittedBy, String uid) {
        this.reportNumber = reportNumber;
        this.date = date;
        this.location = location;
        this.waterType = waterType;
        this.waterCondition = waterCondition;
        this.submittedBy = submittedBy;
        this.uid = uid;
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

    public WaterType getWaterType() {
        return waterType;
    }

    public void setWaterType(WaterType waterType) {
        this.waterType = waterType;
    }

    public WaterCondition getWaterCondition() {
        return waterCondition;
    }

    public void setWaterCondition(WaterCondition waterCondition) {
        this.waterCondition = waterCondition;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "WaterSourceReport{" +
                "reportNumber=" + reportNumber +
                ", date=" + date +
                ", location=" + location +
                ", waterType=" + waterType +
                ", waterCondition=" + waterCondition +
                ", submittedBy='" + submittedBy + '\'' +
                '}';
    }
}
