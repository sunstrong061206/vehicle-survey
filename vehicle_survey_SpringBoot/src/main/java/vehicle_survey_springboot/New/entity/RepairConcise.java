package vehicle_survey_springboot.New.entity;

import java.sql.Timestamp;

public class RepairConcise {
    private Integer  repairId;
    private String repairLicense;
    private Timestamp repairTime;
    private String repairMsg;
    private String name;
    private Integer result;
    private Integer effectStatus;

    public RepairConcise(){

    }

    public RepairConcise(Integer repairId, String repairLicense, Timestamp repairTime, String repairMsg, String name, Integer result, Integer effectStatus) {
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

    public Timestamp getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(Timestamp repairTime) {
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
