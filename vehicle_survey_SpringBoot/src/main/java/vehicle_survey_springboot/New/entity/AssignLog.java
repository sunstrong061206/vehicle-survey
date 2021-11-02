package vehicle_survey_springboot.New.entity;


import java.sql.Timestamp;

public class AssignLog {
    private Integer assignId;
    private String assignLicense;
    private Timestamp assignTime;
    private String assignPoint;
    private String assignMsg;
    private String surveyNo;
    private String surveyName;
    private String manageNo;
    private String manageName;
    private Timestamp manageTime;
    private String vetNo;
    private String vetName;
    private Timestamp vetTime;
    private Integer result;
    private String resultMsg;
    private Integer effectStatus;





    public AssignLog(int assignId, String assignLicense, Timestamp assignTime, int result, int effectStatus) {
        this.assignId = assignId;
        this.assignLicense = assignLicense;
        this.assignTime = assignTime;
        this.result = result;
        this.effectStatus = effectStatus;
    }

    public AssignLog(Integer assignId, String vetNo, Timestamp vetTime, Integer result, String resultMsg) {
        this.assignId = assignId;
        this.vetNo = vetNo;
        this.vetTime = vetTime;
        this.result = result;
        this.resultMsg = resultMsg;
    }

    public AssignLog(String assignLicense, Timestamp assignTime, String assignPoint, String assignMsg, String surveyNo, String manageNo, Timestamp manageTime, String vetNo) {
        this.assignLicense = assignLicense;
        this.assignTime = assignTime;
        this.assignPoint = assignPoint;
        this.assignMsg = assignMsg;
        this.surveyNo = surveyNo;
        this.manageNo = manageNo;
        this.manageTime = manageTime;
        this.vetNo = vetNo;
    }

    public AssignLog(Integer assignId, String assignLicense, Timestamp assignTime, String assignPoint, String assignMsg, String surveyNo, String surveyName, String manageNo, String manageName, Timestamp manageTime, String vetNo, String vetName, Timestamp vetTime, Integer result, String resultMsg, Integer effectStatus) {
        this.assignId = assignId;
        this.assignLicense = assignLicense;
        this.assignTime = assignTime;
        this.assignPoint = assignPoint;
        this.assignMsg = assignMsg;
        this.surveyNo = surveyNo;
        this.surveyName = surveyName;
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

    public AssignLog(){

    }
    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
    }

    public String getAssignLicense() {
        return assignLicense;
    }

    public void setAssignLicense(String assignLicense) {
        this.assignLicense = assignLicense;
    }

    public Timestamp getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Timestamp assignTime) {
        this.assignTime = assignTime;
    }

    public String getAssignPoint() {
        return assignPoint;
    }

    public void setAssignPoint(String assignPoint) {
        this.assignPoint = assignPoint;
    }

    public String getAssignMsg() {
        return assignMsg;
    }

    public void setAssignMsg(String assignMsg) {
        this.assignMsg = assignMsg;
    }

    public String getSurveyNo() {
        return surveyNo;
    }

    public void setSurveyNo(String surveyNo) {
        this.surveyNo = surveyNo;
    }

    public String getManageNo() {
        return manageNo;
    }

    public void setManageNo(String manageNo) {
        this.manageNo = manageNo;
    }

    public Timestamp getManageTime() {
        return manageTime;
    }

    public void setManageTime(Timestamp manageTime) {
        this.manageTime = manageTime;
    }

    public String getVetNo() {
        return vetNo;
    }

    public void setVetNo(String vetNo) {
        this.vetNo = vetNo;
    }

    public Timestamp getVetTime() {
        return vetTime;
    }

    public void setVetTime(Timestamp vetTime) {
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
}
