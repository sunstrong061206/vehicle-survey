package com.vehicle_survey.entity;

public class PunishLog {
    private Integer punishId;
    private String surveyNo;
    private String surveyName;
    private String accidentMsg;
    private Long accidentTime;
    private String accidentPoint;
    private String punishMsg;
    private Double punishAmount;
    private Integer payStatus;
    private  String license;

    public PunishLog(Integer punishId, String surveyNo, String surveyName, String accidentMsg, Long accidentTime, String accidentPoint, String punishMsg, Double punishAmount, Integer payStatus, String license) {
        this.punishId = punishId;
        this.surveyNo = surveyNo;
        this.surveyName = surveyName;
        this.accidentMsg = accidentMsg;
        this.accidentTime = accidentTime;
        this.accidentPoint = accidentPoint;
        this.punishMsg = punishMsg;
        this.punishAmount = punishAmount;
        this.payStatus = payStatus;
        this.license = license;
    }

    public PunishLog(Integer punishId, String accidentMsg, Long accidentTime, String accidentPoint, String punishMsg, Double punishAmount, Integer payStatus) {
        this.punishId = punishId;
        this.accidentMsg = accidentMsg;
        this.accidentTime = accidentTime;
        this.accidentPoint = accidentPoint;
        this.punishMsg = punishMsg;
        this.punishAmount = punishAmount;
        this.payStatus = payStatus;
    }

    public Integer getPunishId() {
        return punishId;
    }

    public void setPunishId(Integer punishId) {
        this.punishId = punishId;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getAccidentMsg() {
        return accidentMsg;
    }

    public void setAccidentMsg(String accidentMsg) {
        this.accidentMsg = accidentMsg;
    }

    public Long getAccidentTime() {
        return accidentTime;
    }

    public void setAccidentTime(Long accidentTime) {
        this.accidentTime = accidentTime;
    }

    public String getAccidentPoint() {
        return accidentPoint;
    }

    public void setAccidentPoint(String accidentPoint) {
        this.accidentPoint = accidentPoint;
    }

    public String getPunishMsg() {
        return punishMsg;
    }

    public void setPunishMsg(String punishMsg) {
        this.punishMsg = punishMsg;
    }

    public Double getPunishAmount() {
        return punishAmount;
    }

    public void setPunishAmount(Double punishAmount) {
        this.punishAmount = punishAmount;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }
}
