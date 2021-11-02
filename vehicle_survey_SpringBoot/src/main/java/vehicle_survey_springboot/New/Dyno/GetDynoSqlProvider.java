package vehicle_survey_springboot.New.Dyno;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import vehicle_survey_springboot.New.entity.RepairConcise;

import java.sql.Timestamp;
import java.util.ArrayList;

public class GetDynoSqlProvider {

    public String getAllList_lendResult(@Param("no") String no, @Param("result") Integer result) {
        return new SQL() {
            {
                SELECT("*");
                FROM("test_lendlog");
                WHERE("manageNo = #{no} || vetNo = #{result}");
                AND();
                if (result == 1) {
                    WHERE("vetLendResult = 1");
                } else {
                    WHERE("vetLendResult = 0");
                }
            }
        }.toString();
    }

    public String getAllList_isVet(@Param("no") String no, @Param("isVet") Boolean isVet) {
        return new SQL() {
            {
                SELECT("*");
                FROM("test_lendlog");
                WHERE("vetNo = #{no}");
                AND();
                WHERE("vetLendResult is null");
                if (isVet == false) {
                    AND();
                    WHERE("revokeTime is null");
                } else {
                    WHERE("vetLendResult is not null");
                }
            }
        }.toString();
    }

    public String getAllList_returnResult(@Param("no") String no, @Param("result") Integer result) {
        return new SQL() {
            {
                SELECT("*");
                FROM("test_lendlog");
                WHERE("manageNo = #{no} || vetNo = #{no}");
                AND();
                if (result == 1) {
                    WHERE("vetReturnResult = 1");
                } else {
                    WHERE("vetReturnResult = 0");
                }
            }
        }.toString();
    }

    public String getAll_allVehicle(@Param("no") String no, @Param("pos") String pos) {
        return new SQL() {
            {
                SELECT("veh.*,emp.name as manageName");
                FROM("test_vehicle veh");
                LEFT_OUTER_JOIN("test_employee emp on emp.no = veh.manageNo");
                if ("vets".equals(pos)) {
                    WHERE("veh.end is null");
                } else if ("manage".equals(pos)) {
                    WHERE("veh.end is null and veh.manageNo = #{no}");
                }
            }
        }.toString();
    }

    public String getAll_status(@Param("no") String no, @Param("pos") String pos, @Param("status") int status) {
        return new SQL() {
            {
                SELECT("veh.*,emp.name as manageName");
                FROM("test_vehicle veh");
                LEFT_OUTER_JOIN("test_employee emp on emp.no = veh.manageNo");
                if ("vet".equals(pos)) {
                    WHERE("veh.end is null and veh.status = #{status}");
                } else if ("manage".equals(pos)) {
                    WHERE("veh.end is null && veh.status = #{status} && veh.manageNo = #{no}");
                }
            }
        }.toString();
    }

    public String getAll_peopleNum(@Param("no") String no, @Param("pos") String pos, @Param("min") int min, @Param("max") int max) {
        return new SQL() {
            {
                //select veh.*,emp.name as manageName from test_vehicle veh
                SELECT("veh.*,emp.name as manageName");
                FROM("test_vehicle veh");
                //left join test_employee emp on emp.no = veh.manageNo where veh.end is null && veh.peopleNum > ? && veh.peopleNum < ?
                LEFT_OUTER_JOIN("test_employee emp on emp.no = veh.manageNo");
                if ("vet".equals(pos)) {
                    WHERE("veh.end is null && veh.peopleNum > #{min} && veh.peopleNum < #{max}");
                } else if ("manage".equals(pos)) {
                    WHERE("veh.end is null && veh.peopleNum > #{min} && veh.peopleNum < #{max} && veh.manageNo = #{no}");
                }
            }
        }.toString();
    }

    public String getAll_lastProtectTime(@Param("no") String no, @Param("pos") String pos, @Param("min") long min, @Param("max") long max) {
        return new SQL() {
            {
                //select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo
                SELECT("veh.*,emp.name as manageName");
                FROM("test_vehicle veh");
                LEFT_OUTER_JOIN("test_employee emp on emp.no = veh.manageNo");
                if ("vet".equals(pos)) {
                    WHERE("veh.end is null && veh.lastProtectTime > #{min} && veh.lastProtectTime < #{max}");
                } else if ("manage".equals(pos)) {
                    WHERE("veh.end is null && veh.lastProtectTime > #{min} && veh.lastProtectTime < #{max} && veh.manageNo = #{no}");
                }
            }
        }.toString();
    }

    public String getAll_result(@Param("no") String no, @Param("result") int result) {
        return new SQL() {
            {
                SELECT("*");
                FROM("test_protectlog");
                if (result == 1) {
                    WHERE("manageNo = #{no} && result = 1 && effectStatus != 1");
                } else {
                    WHERE("where manageNo = #{no} && result = 0 && effectStatus != 1");
                }
                ORDER_BY("protectId desc");
            }
        }.toString();
    }

    public String getAll_isVet(@Param("no") String no, @Param("isVet") Boolean isVet) {
        return new SQL() {
            {
                SELECT("*");
                FROM("test_protectlog");
                if (isVet == false) {
                    WHERE("vetNo = #{no} && result is null && revokeTime is null");
                } else {
                    WHERE("vetNo = #{no} && result is not null");
                }
            }
        }.toString();
    }

    public String getAll_allRepairConcise(@Param("no") String no, @Param("pos") String pos) {
        return new SQL() {
            {
                SELECT("rep.*,empSurvey.name as surveyName,empManage.name as manageName");
                FROM("test_repairlog rep");
                LEFT_OUTER_JOIN("test_employee empSurvey on rep.surveyNo = empSurvey.no");
                LEFT_OUTER_JOIN("test_employee empManage on rep.manageNo = empManage.no");
                if ("survey".equals(pos)) {
                    WHERE("rep.surveyNo = #{no}");
                } else if ("manage".equals(pos)) {
                    WHERE("rep.manageNo = #{no}");
                } else if ("vet".equals(pos)) {
                    WHERE("rep.vetNo = #{no}");
                }

            }
        }.toString();
    }

    public String getAll_timeRepairConcise(@Param("no") String no, @Param("pos") String pos, @Param("begin") Timestamp begin, @Param("end") Timestamp end) {
        return new SQL() {
            {
                SELECT("rep.*,empSurvey.name as surveyName,empManage.name as manageName");
                FROM("test_repairlog rep");
                LEFT_OUTER_JOIN("test_employee empSurvey on rep.surveyNo = empSurvey.no");
                LEFT_OUTER_JOIN("test_employee empManage on rep.manageNo = empManage.no");
                if ("drive".equals(pos)) {
                    WHERE("rep.surveyNo = #{no} && repairTime > #{begin} && repairTime < #{end}");
                } else if ("manage".equals(pos)) {
                    WHERE("rep.manageNo = #{no} && repairTime > #{begin} && repairTime < #{end}");
                } else if ("vet".equals(pos)) {
                    WHERE("rep.vetNo = #{no} && repairTime > #{begin} && repairTime < #{end}");
                }
            }
        }.toString();
    }

    public String getAll_licenseRepairConcise(@Param("no") String no, @Param("pos") String pos, @Param("license") String license) {
        return new SQL() {
            {
                SELECT("rep.*,empSurvey.name as surveyName,empManage.name as manageName");
                FROM("test_repairlog rep");
                LEFT_OUTER_JOIN("test_employee empSurvey on rep.surveyNo = empSurvey.no");
                LEFT_OUTER_JOIN("test_employee empManage on rep.manageNo = empManage.no");
                if ("drive".equals(pos)) {
                    WHERE("rep.surveyNo = #{no} && repairLicense regexp #{license}");
                } else if ("manage".equals(pos)) {
                    WHERE("rep.manageNo = #{no} && repairLicense regexp #{license}");
                } else if ("vet".equals(pos)) {
                    WHERE("rep.vetNo = #{no} && repairLicense regexp #{license}");
                }
            }
        }.toString();
    }

    public String getAll_resultRepairConcise(@Param("no") String no, @Param("pos") String pos, @Param("result") int result) {
        return new SQL() {
            {
                //select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join
                // test_employee empSurvey on rep.surveyNo = empSurvey.no
                SELECT("rep.*,empSurvey.name as surveyName,empManage.name as manageName");
                FROM("test_repairlog rep");
                LEFT_OUTER_JOIN("test_employee empSurvey on rep.surveyNo = empSurvey.no");
                //left join test_employee empManage on rep.manageNo = empManage.no
                LEFT_OUTER_JOIN("test_employee empManage on rep.manageNo = empManage.no");
                if ("drive".equals(pos)) {
                    if (result == 1) {
                        WHERE("rep.surveyNo = #{no} &&  rep.vetResult = 1 && rep.effectStatus != -1");
                    } else {
                        WHERE("rep.surveyNo = #{no} &&  rep.vetResult = 0 && rep.effectStatus != -1");
                    }
                } else if ("manage".equals(pos)) {
                    if (result == 1) {
                        WHERE("rep.manageNo = #{no} &&  rep.vetResult = 1 && rep.effectStatus != -1");
                        //sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? &&  rep.vetResult = 1 && rep.effectStatus != -1";
                    } else {
                        WHERE("rep.manageNo = #{no} &&  rep.vetResult = 0 && rep.effectStatus != -1");
                        //sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? &&  rep.vetResult = 0 && rep.effectStatus != -1";
                    }
                } else if ("vet".equals(pos)) {
                    if (result == 1) {
                        WHERE("rep.vetNo = #{no} &&  rep.vetResult = 1 && rep.effectStatus != -1");
                        //sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? &&  rep.vetResult = 1 && rep.effectStatus != -1";
                    } else {
                        WHERE("rep.vetNo = #{no} &&  rep.vetResult = 0 && rep.effectStatus != -1");
                        //sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? &&  rep.vetResult = 0 && rep.effectStatus != -1";
                    }
                }
            }
        }.toString();
    }

    public String getAll_isVetRepairConcise(@Param("no") String no, @Param("pos") String pos, Boolean isVet) {
        return new SQL() {
            {
                SELECT("rep.*,empSurvey.name as surveyName,empManage.name as manageName");
                FROM("test_repairlog rep");
                LEFT_OUTER_JOIN(" test_employee empSurvey on rep.surveyNo = empSurvey.no");
                LEFT_OUTER_JOIN("test_employee empManage on rep.manageNo = empManage.no");
                if ("manage".equals(pos)) {
                    if (isVet == false) {
                        WHERE("rep.manageNo = #{no} && rep.manageResult is null && rep.surveyNo is not null && rep.revokeTime is null");
                        //sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? && rep.manageResult is null && rep.surveyNo is not null && rep.revokeTime is null";
                    } else {
                        WHERE("rep.manageNo = #{no} && rep.manageResult is not null && rep.surveyNo is not null");
                        //sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? && rep.manageResult is not null && rep.surveyNo is not null ";
                    }
                } else {
                    if (isVet == false) {
                        WHERE("rep.vetNo = #{no} && rep.vetResult is null  && rep.revokeTime is null");
                        //sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? && rep.vetResult is null  && rep.revokeTime is null";
                    } else {
                        WHERE("rep.vetNo = #{no} && rep.vetResult is not null");
                        //sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? && rep.vetResult is not null";
                    }
                }
            }
        }.toString();
    }

    public String getAll_timeAssignLog(@Param("no") String no, @Param("pos") String pos, @Param("begin") Timestamp begin, @Param("end") Timestamp end) {
        return new SQL() {
            {
                SELECT("*");
                FROM("test_assignlog");
                if ("survey".equals(pos)) {
                    WHERE("surveyNo = #{no} && result = 1 && assignTime > #{begin} && assignTime < #{end}");
                } else if ("manage".equals(pos)) {
                    WHERE("manageNo = #{no} && manageTime > #{begin} && manageTime < #{end}");
                } else if ("vet".equals(pos)) {
                    WHERE("vetNo = #{no} && vetTime > #{begin} && vetTime < #{end}");
                }
                ORDER_BY("assignId desc");
            }
        }.toString();
    }

    public String getAll_licenseAssignLog(@Param("no") String no, @Param("pos") String pos, @Param("license") String license) {
        return new SQL() {
            {
                SELECT("*");
                FROM("test_assignlog");
                if ("survey".equals(pos)) {
                    WHERE("manageNo = #{no} && assignLicense regexp #{license} order by assignId desc");
                } else if ("manage".equals(pos)) {
                    WHERE("manageNo = #{no} && assignLicense regexp #{license} order by assignId desc");

                } else if ("vet".equals(pos)) {
                    WHERE("vetNo = #{no} && assignLicense regexp #{license} order by assignId desc");

                }
            }
        }.toString();
    }

    public String getAll_resultAssignLog(@Param("no") String no, @Param("pos") String pos, @Param("result") int result) {
        return new SQL() {
            {
                SELECT("*");
                FROM("test_assignlog");
                if (result == 1) {
                    if ("survey".equals(pos)) {
                        WHERE("surveyNo = #{no} && result = 1  && effectStatus != -1");
                        //sql = "select * from test_assignlog where surveyNo = ? && result = 1  && effectStatus != -1 order by assignId desc";
                    } else if ("manage".equals(pos)) {
                        WHERE("manageNo = #{no} && result = 1 && effectStatus != -1");
                        //sql = "select * from test_assignlog where manageNo = ? && result = 1 && effectStatus != -1 order by assignId desc ";
                    } else if ("vet".equals(pos)) {
                        WHERE("vetNo = #{no} && result = 1 && effectStatus != -1");
                        //sql = "select  * from test_assignlog where vetNo = ? && result = 1 && effectStatus != -1 order by assignId desc ";
                    }
                } else {
                    if ("survey".equals(pos)) {
                        WHERE(" surveyNo = #{no} && result = 1 && result = 0 && effectStatus != -1");
                        //sql = "select * from test_assignlog where surveyNo = ? && result = 1 && result = 0 && effectStatus != -1 order by assignId desc";
                    } else if ("manage".equals(pos)) {
                        WHERE("manageNo = #{no} && result = 0 && effectStatus != -1");
                       // sql = "select * from test_assignlog where manageNo = ? && result = 0 && effectStatus != -1 order by assignId desc ";
                    } else if ("vet".equals(pos)) {
                        WHERE("vetNo = #{no} && result = 0 && effectStatus != -1");
                        //sql = "select  * from test_assignlog where vetNo = ? && result = 0 && effectStatus != -1 order by assignId desc ";
                    }
                }
            }
        }.toString();
    }

    public String getAll_isVetAssignLog(@Param("no") String no,@Param("isVet") Boolean isVet){
        return new SQL(){
            {
                SELECT("*");
                FROM("test_assignlog");
                if(isVet == false){
                    WHERE("vetNo = #{no} && result is null && revokeTime  is null");
                    //sql = "select * from test_assignlog where vetNo = ? && result is null && revokeTime  is null";
                }else {
                    WHERE("vetNo = #{no} && result is not null");
                    //sql = "select * from test_assignlog where vetNo = ? && result is not null";
                }
            }
        }.toString();
    }

    public String getAll_allAssignLog(@Param("no") String no, @Param("pos") String pos){
        return new SQL(){
            {
                SELECT("*");
                FROM("test_assignlog");
                if ("survey".equals(pos)) {
                    WHERE("surveyNo = #{no} && result = 1");
                    //sql = "select * from test_assignlog where surveyNo = ? && result = 1 order by assignId desc";
                } else if ("manage".equals(pos)) {
                    WHERE("manageNo = #{no}");
                    //sql = "select * from test_assignlog where manageNo = ? order by assignId desc ";
                } else if ("vet".equals(pos)) {
                    WHERE("vetNo = #{no}");
                    //sql = "select  * from test_assignlog where vetNo = ? order by assignId desc ";
                }
            }
        }.toString();
    }
}