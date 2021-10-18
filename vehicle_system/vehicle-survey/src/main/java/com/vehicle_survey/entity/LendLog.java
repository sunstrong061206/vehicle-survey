package com.vehicle_survey.entity;

import java.util.ArrayList;

public class LendLog {
    private Integer lendId;
    private String lendLicense;
    private String lendName;
    private String lendPhone;
    private ArrayList<String> lendDrivecardImg;
    private Long lendTime;
    private Integer lendDays;
    private String lendMsg;
    private String manageNo;
    private String manageName;
    private Long manageLendTime;
    private String vetNo;
    private String vetName;
    private Long vetLendTime;
    private Integer vetLendResult;
    private String vetLendResultMsg;
    private Long returnTime;
    private ArrayList<String> returnImg;
    private Long manageReturnTime;
    private Long vetReturnTime;
    private Integer vetReturnResult;
    private String vetReturnResultMsg;
    private Integer effectStatus;

    public LendLog(Integer lendId,Long  returnTime, ArrayList<String> returnImg, Long manageReturnTime) {
        this.lendId = lendId;
        this.returnTime = returnTime;
        this.returnImg = returnImg;
        this.manageReturnTime = manageReturnTime;
    }

    public LendLog(String lendLicense, String lendName, String lendPhone, ArrayList<String> lendDrivecardImg, Long lendTime, Integer lendDays, String lendMsg, String manageNo, Long manageLendTime, String vetNo) {
        this.lendLicense = lendLicense;
        this.lendName = lendName;
        this.lendPhone = lendPhone;
        this.lendDrivecardImg = lendDrivecardImg;
        this.lendTime = lendTime;
        this.lendDays = lendDays;
        this.lendMsg = lendMsg;
        this.manageNo = manageNo;
        this.manageLendTime = manageLendTime;
        this.vetNo = vetNo;
    }

    public LendLog(Integer lendId, String lendLicense, String lendName, String lendPhone, ArrayList<String> lendDrivecardImg, Long lendTime, Integer lendDays, String lendMsg, String manageNo, String manageName, Long manageLendTime, String vetNo, String vetName, Long vetLendTime, Integer vetLendResult, String vetLendResultMsg, Long returnTime, ArrayList<String> returnImg, Long manageReturnTime, Long vetReturnTime, Integer vetReturnResult, String vetReturnResultMsg, Integer effectStatus) {
        this.lendId = lendId;
        this.lendLicense = lendLicense;
        this.lendName = lendName;
        this.lendPhone = lendPhone;
        this.lendDrivecardImg = lendDrivecardImg;
        this.lendTime = lendTime;
        this.lendDays = lendDays;
        this.lendMsg = lendMsg;
        this.manageNo = manageNo;
        this.manageName = manageName;
        this.manageLendTime = manageLendTime;
        this.vetNo = vetNo;
        this.vetName = vetName;
        this.vetLendTime = vetLendTime;
        this.vetLendResult = vetLendResult;
        this.vetLendResultMsg = vetLendResultMsg;
        this.returnTime = returnTime;
        this.returnImg = returnImg;
        this.manageReturnTime = manageReturnTime;
        this.vetReturnTime = vetReturnTime;
        this.vetReturnResult = vetReturnResult;
        this.vetReturnResultMsg = vetReturnResultMsg;
        this.effectStatus = effectStatus;
    }

    public LendLog(Integer lendId, String vetNo, Long vetLendTime, Integer vetLendResult, String vetLendResultMsg) {
        this.lendId = lendId;
        this.vetNo = vetNo;
        this.vetLendTime = vetLendTime;
        this.vetLendResult = vetLendResult;
        this.vetLendResultMsg = vetLendResultMsg;
    }


    public LendLog(Integer lendId,String lendLicense, String lendName, Long lendTime, Integer lendDays,  Integer vetLendResult, Long returnTime, Integer vetReturnResult, Integer effectStatus) {
        this.lendId = lendId;
        this.lendLicense = lendLicense;
        this.lendName = lendName;
        this.lendTime = lendTime;
        this.lendDays = lendDays;
        this.vetLendResult = vetLendResult;
        this.returnTime = returnTime;

        this.vetReturnResult = vetReturnResult;
        this.effectStatus = effectStatus;
    }

    public Integer getLendId() {
        return lendId;
    }

    public void setLendId(Integer lendId) {
        this.lendId = lendId;
    }

    public String getLendLicense() {
        return lendLicense;
    }

    public void setLendLicense(String lendLicense) {
        this.lendLicense = lendLicense;
    }

    public String getLendName() {
        return lendName;
    }

    public void setLendName(String lendName) {
        this.lendName = lendName;
    }

    public String getLendPhone() {
        return lendPhone;
    }

    public void setLendPhone(String lendPhone) {
        this.lendPhone = lendPhone;
    }

    public ArrayList<String> getLendDrivecardImg() {
        return lendDrivecardImg;
    }

    public void setLendDrivecardImg(ArrayList<String> lendDrivecardImg) {
        this.lendDrivecardImg = lendDrivecardImg;
    }



    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public Long getLendTime() {
        return lendTime;
    }

    public void setLendTime(Long lendTime) {
        this.lendTime = lendTime;
    }

    public Integer getLendDays() {
        return lendDays;
    }

    public void setLendDays(Integer lendDays) {
        this.lendDays = lendDays;
    }

    public String getLendMsg() {
        return lendMsg;
    }

    public void setLendMsg(String lendMsg) {
        this.lendMsg = lendMsg;
    }



    public String getVetNo() {
        return vetNo;
    }

    public void setVetNo(String vetNo) {
        this.vetNo = vetNo;
    }

    public Long getVetLendTime() {
        return vetLendTime;
    }

    public void setVetLendTime(Long vetLendTime) {
        this.vetLendTime = vetLendTime;
    }

    public Integer getVetLendResult() {
        return vetLendResult;
    }

    public void setVetLendResult(Integer vetLendResult) {
        this.vetLendResult = vetLendResult;
    }

    public String getVetLendResultMsg() {
        return vetLendResultMsg;
    }

    public void setVetLendResultMsg(String vetLendResultMsg) {
        this.vetLendResultMsg = vetLendResultMsg;
    }

    public Long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Long returnTime) {
        this.returnTime = returnTime;
    }

    public ArrayList<String> getReturnImg() {
        return returnImg;
    }

    public void setReturnImg(ArrayList<String> returnImg) {
        this.returnImg = returnImg;
    }

    public String getManageNo() {
        return manageNo;
    }

    public void setManageNo(String manageNo) {
        this.manageNo = manageNo;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public Long getManageLendTime() {
        return manageLendTime;
    }

    public void setManageLendTime(Long manageLendTime) {
        this.manageLendTime = manageLendTime;
    }

    public Long getManageReturnTime() {
        return manageReturnTime;
    }

    public void setManageReturnTime(Long manageReturnTime) {
        this.manageReturnTime = manageReturnTime;
    }

    public Long getVetReturnTime() {
        return vetReturnTime;
    }

    public void setVetReturnTime(Long vetReturnTime) {
        this.vetReturnTime = vetReturnTime;
    }

    public Integer getVetReturnResult() {
        return vetReturnResult;
    }

    public void setVetReturnResult(Integer vetReturnResult) {
        this.vetReturnResult = vetReturnResult;
    }

    public String getVetReturnResultMsg() {
        return vetReturnResultMsg;
    }

    public void setVetReturnResultMsg(String vetReturnResultMsg) {
        this.vetReturnResultMsg = vetReturnResultMsg;
    }

    public Integer getEffectStatus() {
        return effectStatus;
    }

    public void setEffectStatus(Integer effectStatus) {
        this.effectStatus = effectStatus;
    }
}
