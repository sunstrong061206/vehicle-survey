package com.vehicle_survey.entity;

public class RepairConcise {
    private Integer  repairId;
    private String repairLicense;
    private Long repairTime;
    private String repairMsg;
    private String name;
    private Integer result;
    private Integer effectStatus;

    public RepairConcise(Integer repairId, String repairLicense, Long repairTime, String repairMsg, String name, Integer result, Integer effectStatus) {
        this.repairId = repairId;
        this.repairLicense = repairLicense;
        this.repairTime = repairTime;
        this.repairMsg = repairMsg;
        this.name = name;
        this.result = result;
        this.effectStatus = effectStatus;
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

    public String getRepairMsg() {
        return repairMsg;
    }

    public void setRepairMsg(String repairMsg) {
        this.repairMsg = repairMsg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getEffectStatus() {
        return effectStatus;
    }

    public void setEffectStatus(Integer effectStatus) {
        this.effectStatus = effectStatus;
    }
}
