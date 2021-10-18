package com.vehicle_survey.entity;

public class ProtectLog {
    private Integer protectId;
    private String protectLicense;
    private Long protectTime;
    private String protectMsg;
    private String manageNo;
    private String manageName;
    private Long manageTime;
    private String vetNo;
    private String vetName;
    private Long vetTime;
    private Integer result;
    private String resultMsg;
    private Integer effectStatus;



    public ProtectLog(String protectLicense, Long protectTime, String protectMsg, String manageNo, Long manageTime, String vetNo) {
        this.protectLicense = protectLicense;
        this.protectTime = protectTime;
        this.protectMsg = protectMsg;
        this.manageNo = manageNo;
        this.manageTime = manageTime;
        this.vetNo = vetNo;
    }

    public ProtectLog(Integer protectId, String protectLicense, Long protectTime, String protectMsg, String manageNo, String manageName, Long manageTime, String vetNo, String vetName, Long vetTime, Integer result, String resultMsg, Integer effectStatus) {
        this.protectId = protectId;
        this.protectLicense = protectLicense;
        this.protectTime = protectTime;
        this.protectMsg = protectMsg;
        this.manageNo = manageNo;
        this.manageName = manageName;
        this.manageTime = manageTime;
        this.vetNo = vetNo;
        this.vetName = vetName;
        this.vetTime = vetTime;
        this.result = result;
        this.resultMsg = resultMsg;
        this.effectStatus = effectStatus;
    }

    public ProtectLog(Integer protectId, String vetNo, Long vetTime, Integer result, String resultMsg) {
        this.protectId = protectId;
        this.vetNo = vetNo;
        this.vetTime = vetTime;
        this.result = result;
        this.resultMsg = resultMsg;
    }

    public ProtectLog(Integer protectId, String protectLicense, Long protectTime, String protectMsg) {
        this.protectId = protectId;
        this.protectLicense = protectLicense;
        this.protectTime = protectTime;
        this.protectMsg = protectMsg;
    }

    public Integer getProtectId() {
        return protectId;
    }

    public void setProtectId(Integer protectId) {
        this.protectId = protectId;
    }

    public String getProtectLicense() {
        return protectLicense;
    }

    public void setProtectLicense(String protectLicense) {
        this.protectLicense = protectLicense;
    }

    public Long getProtectTime() {
        return protectTime;
    }

    public void setProtectTime(Long protectTime) {
        this.protectTime = protectTime;
    }

    public String getProtectMsg() {
        return protectMsg;
    }

    public void setProtectMsg(String protectMsg) {
        this.protectMsg = protectMsg;
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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Integer getEffectStatus() {
        return effectStatus;
    }

    public void setEffectStatus(Integer effectStatus) {
        this.effectStatus = effectStatus;
    }
}
