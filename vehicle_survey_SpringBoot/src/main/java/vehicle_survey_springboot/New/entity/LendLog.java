package vehicle_survey_springboot.New.entity;

import java.sql.Timestamp;
import java.util.ArrayList;

public class LendLog {
    private Integer lendId;
    private String lendLicense;
    private String lendName;
    private String lendPhone;
    private ArrayList<String> lendDrivecardImg;
    private Timestamp lendTime;
    private Integer lendDays;
    private String lendMsg;
    private String manageNo;
    private String manageName;
    private Timestamp manageLendTime;
    private String vetNo;
    private String vetName;
    private Timestamp vetLendTime;
    private Integer vetLendResult;
    private String vetLendResultMsg;
    private Timestamp returnTime;
    private ArrayList<String> returnImg;
    private Timestamp manageReturnTime;
    private Timestamp vetReturnTime;
    private Integer vetReturnResult;
    private String vetReturnResultMsg;
    private Integer effectStatus;

    public LendLog(){

    }
    public LendLog(Integer lendId, Timestamp returnTime, ArrayList<String> returnImg, Timestamp manageReturnTime) {
        this.lendId = lendId;
        this.returnTime = returnTime;
        this.returnImg = returnImg;
        this.manageReturnTime = manageReturnTime;
    }

    public LendLog(String lendLicense, String lendName, String lendPhone, ArrayList<String> lendDrivecardImg, Timestamp lendTime, Integer lendDays, String lendMsg, String manageNo, Timestamp manageLendTime, String vetNo) {
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

    public LendLog(Integer lendId, String lendLicense, String lendName, String lendPhone, ArrayList<String> lendDrivecardImg, Timestamp lendTime, Integer lendDays, String lendMsg, String manageNo, String manageName, Timestamp manageLendTime, String vetNo, String vetName, Timestamp vetLendTime, Integer vetLendResult, String vetLendResultMsg, Timestamp returnTime, ArrayList<String> returnImg, Timestamp manageReturnTime, Timestamp vetReturnTime, Integer vetReturnResult, String vetReturnResultMsg, Integer effectStatus) {
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

    public LendLog(Integer lendId, String vetNo, Timestamp vetLendTime, Integer vetLendResult, String vetLendResultMsg) {
        this.lendId = lendId;
        this.vetNo = vetNo;
        this.vetLendTime = vetLendTime;
        this.vetLendResult = vetLendResult;
        this.vetLendResultMsg = vetLendResultMsg;
    }


    public LendLog(Integer lendId, String lendLicense, String lendName, Timestamp lendTime, Integer lendDays, Integer vetLendResult, Timestamp returnTime, Integer vetReturnResult, Integer effectStatus) {
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

    public Timestamp getLendTime() {
        return lendTime;
    }

    public void setLendTime(Timestamp lendTime) {
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

    public Timestamp getVetLendTime() {
        return vetLendTime;
    }

    public void setVetLendTime(Timestamp vetLendTime) {
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

    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
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

    public Timestamp getManageLendTime() {
        return manageLendTime;
    }

    public void setManageLendTime(Timestamp manageLendTime) {
        this.manageLendTime = manageLendTime;
    }

    public Timestamp getManageReturnTime() {
        return manageReturnTime;
    }

    public void setManageReturnTime(Timestamp manageReturnTime) {
        this.manageReturnTime = manageReturnTime;
    }

    public Timestamp getVetReturnTime() {
        return vetReturnTime;
    }

    public void setVetReturnTime(Timestamp vetReturnTime) {
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
