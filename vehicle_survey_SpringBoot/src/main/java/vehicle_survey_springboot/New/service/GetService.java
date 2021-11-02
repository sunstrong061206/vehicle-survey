package vehicle_survey_springboot.New.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehicle_survey_springboot.New.entity.*;
import vehicle_survey_springboot.New.mapper.GetMapper;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class GetService {
    @Autowired
    GetMapper getMapper;

    public ArrayList<DriveLog> getAll_license(String no,String license){
        return getMapper.getAll_license(no,license);
    }

    public ArrayList<DriveLog> getAll_all(String no){
        return getMapper.getAll_all(no);
    }

    public ArrayList<LendLog> getAllList_all(String no){
        return getMapper.getAllList_all(no);
    }

    public ArrayList<LendLog> getAllList_lendTime(String no, Timestamp lendBegin, Timestamp lendEnd){
        return getMapper.getAllList_lendTime(no,lendBegin,lendEnd);
    }
    public ArrayList<LendLog> getAllList_returnTime(String no,Timestamp returnBegin, Timestamp returnEnd){
        return getMapper.getAllList_returnTime(no,returnBegin,returnEnd);
    }
    public ArrayList<LendLog> getAllList_license(String no, String license){
        return getMapper.getAllList_license(no,license);
    }
    public ArrayList<LendLog> getAllList_lendResult(String no, Integer result){
        return getMapper.getAllList_lendResult(no,result);
    }
    public ArrayList<LendLog> getAllList_isVet(String no, Boolean isVet){
        return getMapper.getAllList_isVet(no,isVet);
    }
    public ArrayList<LendLog> getAllList_name(String no, String name){
        return getMapper.getAllList_name(no,name);
    }
    public ArrayList<LendLog> getAllList_returnResult(String no, Integer result){
        return getMapper.getAllList_returnResult(no,result);
    }
    public ArrayList<Vehicle> getAll_allVehicle(String no, String pos){
        return getMapper.getAll_allVehicle(no,pos);
    }
    public ArrayList<Vehicle> getAll_name(String no,String manageName){
        return getMapper.getAll_name(no,manageName);
    }
    public ArrayList<Vehicle> getAll_status(String no,String pos,int status){
        return getMapper.getAll_status(no,pos,status);
    }
    public ArrayList<Vehicle> getAll_lastProtectTime(String no ,String pos,Timestamp min, Timestamp max){
        return getMapper.getAll_lastProtectTime(no,pos,min,max);
    }
    public ArrayList<Vehicle> getAll_peopleNum(String no,String pos,int min, int max){
        return getMapper.getAll_peopleNum(no,pos,min,max);
    }
    public ArrayList<ProtectLog> getAll_time(String no,Timestamp begin,Timestamp end){
        return getMapper.getAll_time(no,begin,end);
    }
    public ArrayList<ProtectLog> getAll_licenseProtectLog(String no,String license){
        return getMapper.getAll_licenseProtectLog(no,license);
    }
    public ArrayList<ProtectLog> getAll_result(String no,int result){
        return getMapper.getAll_result(no,result);
    }
    public ArrayList<ProtectLog> getAll_isVet(String no,Boolean isVet){
        return getMapper.getAll_isVet(no,isVet);
    }
    public ArrayList<ProtectLog> getAll_allProtectLog(String no){
        return getMapper.getAll_allProtectLog(no);
    }
    public ArrayList<Vehicle> getProtectVehicle(String manageNo, Timestamp currentTime){
        return getMapper.getProtectVehicle(manageNo,currentTime);
    }
    public  ArrayList<PunishLog> getAll_timePunishLog(String no,Timestamp begin,Timestamp end){
        return getMapper.getAll_timePunishLog(no,begin,end);
    }
    public ArrayList<PunishLog> getAll_licensePunishLog(String no,String license){
        return getMapper.getAll_licensePunishLog(no,license);
    }
    public ArrayList<PunishLog> getAll_payStatus(String no,int payStatus){
        return getMapper.getAll_payStatus(no,payStatus);
    }
    public ArrayList<PunishLog> getAll_allPunishLog(String no){
        return getMapper.getAll_allPunishLog(no);
    }
    public ArrayList<RepairConcise> getAll_allRepairConcise(String no, String pos){
        return getMapper.getAll_allRepairConcise(no,pos);
    }
    public ArrayList<RepairConcise> getAll_timeRepairConcise(String no, String pos, Timestamp begin, Timestamp end){
        return getMapper.getAll_timeRepairConcise(no,pos,begin,end);
    }
    public ArrayList<RepairConcise> getAll_licenseRepairConcise(String no, String pos, String license){
        return getMapper.getAll_licenseRepairConcise(no,pos,license);
    }
    public ArrayList<RepairConcise> getAll_resultRepairConcise(String no, String pos, int result){
        return getMapper.getAll_resultRepairConcise(no,pos,result);
    }
    public ArrayList<RepairConcise> getAll_isVetRepairConcise(String no,String pos,Boolean isVet){
        return getMapper.getAll_isVetRepairConcise(no,pos,isVet);
    }

    public ArrayList<AssignLog> getAll_timeAssignLog(String no, String pos, Timestamp begin, Timestamp end){
        return getMapper.getAll_timeAssignLog(no,pos,begin,end);
    }

    public ArrayList<AssignLog> getAll_licenseAssignLog(String no, String pos, String license){
        return getMapper.getAll_licenseAssignLog(no,pos,license);
    }

    public ArrayList<AssignLog> getAll_resultAssignLog(String no, String pos, int result){
        return getMapper.getAll_resultAssignLog(no,pos,result);
    }
    public ArrayList<AssignLog> getAll_isVetAssignLog(String no,Boolean isVet){
        return getMapper.getAll_isVetAssignLog(no,isVet);
    }

    public ArrayList<AssignLog> getAll_allAssignLog(String no, String pos){
        return getMapper.getAll_allAssignLog(no,pos);
    }

    public Employee getBoss(String no){
        return getMapper.getBoss(no);
    }

}
