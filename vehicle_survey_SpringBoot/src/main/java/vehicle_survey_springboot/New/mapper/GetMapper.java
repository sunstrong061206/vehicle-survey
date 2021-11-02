package vehicle_survey_springboot.New.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider;
import vehicle_survey_springboot.New.entity.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface GetMapper {
    @Select("select dri.*,max(time) as driveEnd,min(time) as driveBegin,ass.assignPoint as surveyPoint,emp.name as surveyName from test_drivelog dri left join test_assignlog ass on dri.assignId = ass.assignId left  join test_employee emp on emp.no = dri.surveyNo where dri.surveyNo = #{no} && dri.driveLicense regexp #{license} group by dri.driveId order by dri.driveId desc")
    public ArrayList<DriveLog> getAll_license(@Param("no") String no, @Param("license") String license);

    @Select("select dri.*,max(time) as driveEnd,min(time) as driveBegin,ass.assignPoint as surveyPoint,emp.name as surveyName from test_drivelog dri left join test_assignlog ass on dri.assignId = ass.assignId left  join test_employee emp on emp.no = dri.surveyNo where dri.surveyNo = #{no} group by dri.driveId order by dri.driveId desc")
    public ArrayList<DriveLog> getAll_all(@Param("no") String no);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class ,method = "getAll_allVehicle")
    public ArrayList<Vehicle> getAll_allVehicle(String no, String pos);

    @Select("select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && emp.name regexp #{manageName}")
    public ArrayList<Vehicle> getAll_name(String no,@Param("manageName") String manageName);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_status")
    public ArrayList<Vehicle> getAll_status(String no,String pos,int status);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_peopleNum")
    public ArrayList<Vehicle> getAll_peopleNum(String no,String pos,int min, int max);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_lastProtectTime")
    public ArrayList<Vehicle> getAll_lastProtectTime(String no , String pos, Timestamp min, Timestamp max);

    @Select("select * from test_vehicle where manageNo = #{manageNo} && status!= -1 && (unix_timestamp(lastProtectTime+1000 * 60 * 60 * 24 * 30 * 6))<#{currentTime} order by lastProtectTime")
    public ArrayList<Vehicle> getProtectVehicle(@Param("manageNo") String manageNo, @Param("currentTime") Timestamp currentTime);

    @Select("select * from test_protectlog where manageNo = #{no} && protectTime > #{begin} && protectTime < #{end} order by protectId desc")
    public ArrayList<ProtectLog> getAll_time(@Param("no") String no, @Param("begin") Timestamp begin, @Param("end") Timestamp end);

    @Select("select * from test_protectlog where manageNo = #{no} && protectLicense regexp #{license} order by protectId desc")
    public ArrayList<ProtectLog> getAll_licenseProtectLog(@Param("no") String no,@Param("license") String license);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_result")
    public ArrayList<ProtectLog> getAll_result(String no,int result);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_isVet")
    public ArrayList<ProtectLog> getAll_isVet(String no,Boolean isVet);

    @Select("select * from test_protectlog where manageNo = #{no} order by protectId desc")
    public ArrayList<ProtectLog> getAll_allProtectLog(@Param("no") String no);

    @Select("select * from test_lendlog where ( manageNo = #{no} || vetNo = #{no})")
    public ArrayList<LendLog> getAllList_all(@Param("no") String no);

    @Select("select * from test_lendlog where ( manageNo = #{no} || vetNo = #{no}) && lendTime > #{lendBegin} && lendTime < #{lendEnd}")
    public ArrayList<LendLog> getAllList_lendTime(@Param("no") String no, @Param("lendBegin") Timestamp lendBegin, @Param("lendEnd") Timestamp lendEnd);

    @Select("select * from test_lendlog where (manageNo = #{no} || vetNo = #{no}) && returnTime > #{returnBegin} && returnTime < #{returnEnd}")
    public ArrayList<LendLog> getAllList_returnTime(@Param("no")String no, @Param("returnBegin")Timestamp returnBegin, @Param("returnEnd") Timestamp returnEnd);

    @Select("select * from test_lendlog where ( manageNo = #{no} || vetNo = #{no}) && lendLicense regexp #{license}")
    public ArrayList<LendLog> getAllList_license(@Param("no") String no, @Param("license") String license);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class , method = "getAllList_lendResult")
    public ArrayList<LendLog> getAllList_lendResult(String no, Integer result);

    @SelectProvider(type =vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class , method = "getAllList_isVet")
    public ArrayList<LendLog> getAllList_isVet(String no, Boolean isVet);

    @Select("select * from test_lendlog where ( manageNo = #{no} || vetNo = #{no}) && lendName regexp #{name}")
    public ArrayList<LendLog> getAllList_name(@Param("no") String no, @Param("name") String name);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class ,method = "getAllList_returnResult")
    public ArrayList<LendLog> getAllList_returnResult(String no, Integer result);

    @Select("select * from test_punishlog where surveyNo  = #{no} && accidentTime > #{begin} && accidentTime < #{end} order by punishId desc")
    public ArrayList<PunishLog> getAll_timePunishLog(@Param("no") String no, @Param("begin") Timestamp begin, @Param("end") Timestamp end);

    @Select("select * from test_punishlog where surveyNo  = #{no} && license regexp #{license} order by punishId desc")
    public ArrayList<PunishLog> getAll_licensePunishLog(@Param("no") String no,@Param("license") String license);

    @Select("select * from test_punishlog where surveyNo  = #{no} && payStatus = #{payStatus} order by punishId desc")
    public ArrayList<PunishLog> getAll_payStatus(@Param("no") String no,@Param("payStatus") int payStatus);

    @Select("select * from test_punishlog where surveyNo  = #{no} order by punishId desc")
    public ArrayList<PunishLog> getAll_allPunishLog(@Param("no") String no);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class ,method = "getAll_allRepairConcise")
    public ArrayList<RepairConcise> getAll_allRepairConcise(String no, String pos);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_timeRepairConcise")
    public ArrayList<RepairConcise> getAll_timeRepairConcise(String no, String pos, Timestamp begin, Timestamp end);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_licenseRepairConcise")
    public ArrayList<RepairConcise> getAll_licenseRepairConcise(String no, String pos, String license);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_resultRepairConcise")
    public ArrayList<RepairConcise> getAll_resultRepairConcise(String no, String pos, int result);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_isVetRepairConcise")
    public ArrayList<RepairConcise> getAll_isVetRepairConcise(String no,String pos,Boolean isVet);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_timeAssignLog")
    public ArrayList<AssignLog> getAll_timeAssignLog(String no, String pos, Timestamp begin, Timestamp end);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_licenseAssignLog")
    public ArrayList<AssignLog> getAll_licenseAssignLog(String no, String pos, String license);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_resultAssignLog")
    public ArrayList<AssignLog> getAll_resultAssignLog(String no, String pos, int result);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_isVetAssignLog")
    public ArrayList<AssignLog> getAll_isVetAssignLog(String no,Boolean isVet);

    @SelectProvider(type = vehicle_survey_springboot.New.Dyno.GetDynoSqlProvider.class,method = "getAll_allAssignLog")
    public ArrayList<AssignLog> getAll_allAssignLog(String no, String pos);

    @Select("select * from test_employee where no = (select bossNo from test_employee where no = #{no})")
    public Employee getBoss(@Param("no") String no);
}

















