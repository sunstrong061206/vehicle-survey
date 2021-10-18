package com.vehicle_survey.entity;

import java.util.ArrayList;

public class RepairLog {
    private Integer repairId;
    private String repairLicense;
    private Long repairTime;
    private ArrayList<String> repairImg;
    private String repairMsg;
    private String surveyNo;
    private String surveyName;
    private Long surveyTime;
    private String manageNo;
    private String manageName;
    private Long manageTime;
    private Integer manageResult;
    private String manageResultMsg;
    private String vetNo;
    private String vetName;
    private Long vetTime;
    private Integer vetResult;
    private String vetResultMsg;
    private Integer effectStatus;


    public RepairLog(String repairLicense, Long repairTime, ArrayList<String> repairImg, String repairMsg, String surveyNo, Long surveyTime, String vetNo) {
        this.repairLicense = repairLicense;
        this.repairTime = repairTime;
        this.repairImg = repairImg;
        this.repairMsg = repairMsg;
        this.surveyNo = surveyNo;
        this.surveyTime = surveyTime;
        this.vetNo = vetNo;
    }

    public RepairLog(Integer repairId, String repairLicense, Long repairTime, ArrayList<String> repairImg, String repairMsg, String surveyNo, String surveyName, Long surveyTime, String manageNo, String manageName, Long manageTime, Integer manageResult, String manageResultMsg, String vetNo, String vetName, Long vetTime, Integer vetResult, String vetResultMsg, Integer effectStatus) {
        this.repairId = repairId;
        this.repairLicense = repairLicense;
        this.repairTime = repairTime;
        this.repairImg = repairImg;
        this.repairMsg = repairMsg;
        this.surveyNo = surveyNo;
        this.surveyName = surveyName;
        this.surveyTime = surveyTime;
        this.manageNo = manageNo;
        this.manageName = manageName;
        this.manageTime = manageTime;
        this.manageResult = manageResult;
        this.manageResultMsg = manageResultMsg;
        this.vetNo = vetNo;
        this.vetName = vetName;
        this.vetTime = vetTime;
        this.vetResult = vetResult;
        this.vetResultMsg = vetResultMsg;
        this.effectStatus = effectStatus;
    }

    public RepairLog(Integer repairId, String manageNo, Long manageTime, Integer manageResult, String manageResultMsg) {
        this.repairId = repairId;
        this.manageNo = manageNo;
        this.manageTime = manageTime;
        this.manageResult = manageResult;
        this.manageResultMsg = manageResultMsg;
    }

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public String getRepairLicense() {
        return repairLicense;
    }

    public void setRepairLicense(String repairLicense) {
        this.repairLicense = repairLicense;
    }

    public Long getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Long repairTime) {
        this.repairTime = repairTime;
    }

    public ArrayList<String> getRepairImg() {
        return repairImg;
    }

    public void setRepairImg(ArrayList<String> repairImg) {
        this.repairImg = repairImg;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getRepairMsg() {
        return repairMsg;
    }

    public void setRepairMsg(String repairMsg) {
        this.repairMsg = repairMsg;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public Long getSurveyTime() {
        return surveyTime;
    }

    public void setSurveyTime(Long surveyTime) {
        this.surveyTime = surveyTime;
    }

    public String getManageNo() {
        return manageNo;
    }

    public void setManageNo(String manageNo) {
        this.manageNo = manageNo;
    }

    public Long getManageTime() {
        return manageTime;
    }

    public void setManageTime(Long manageTime) {
        this.manageTime = manageTime;
    }

    public Integer getManageResult() {
        return manageResult;
    }

    public void setManageResult(Integer manageResult) {
        this.manageResult = manageResult;
    }

    public String getManageResultMsg() {
        return manageResultMsg;
    }

    public void setManageResultMsg(String manageResultMsg) {
        this.manageResultMsg = manageResultMsg;
    }

    public String getVetNo() {
        return vetNo;
    }

    public void setVetNo(String vetNo) {
        this.vetNo = vetNo;
    }

    public Long getVetTime() {
        return vetTime;
    }

    public void setVetTime(Long vetTime) {
        this.vetTime = vetTime;
    }

    public Integer getVetResult() {
        return vetResult;
    }

    public void setVetResult(Integer vetResult) {
        this.vetResult = vetResult;
    }

    public String getVetResultMsg() {
        return vetResultMsg;
    }

    public void setVetResultMsg(String vetResultMsg) {
        this.vetResultMsg = vetResultMsg;
    }

    public Integer getEffectStatus() {
        return effectStatus;
    }

    public void setEffectStatus(Integer effectStatus) {
        this.effectStatus = effectStatus;
    }
}
