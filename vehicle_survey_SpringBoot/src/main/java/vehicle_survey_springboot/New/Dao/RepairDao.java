package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.entity.RepairLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RepairDao {
    public static ArrayList<RepairLog> getDetail(int id) {
        ArrayList<RepairLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName,empVet.name as vetName from test_repairlog rep left join test_employee empSurvey on empSurvey.no = rep.surveyNo left join test_employee empManage on empManage.no = rep.manageNo left join test_employee empVet on empVet.no = rep.vetNo where repairId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object obj;
                Integer repairId = rs.getInt("repairId");
                String repairLicense = rs.getString("repairLicense");
                Long repairTime = null;
                if ((obj = rs.getTimestamp("repairTime")) != null) {
                    repairTime = ((Timestamp) obj).getTime();
                }
                ArrayList<String> repairImg = new ArrayList<String>(Arrays.asList(rs.getString("repairImg").split(";")));
                String repairMsg = rs.getString("repairMsg");
                String surveyNo = rs.getString("surveyNo");
                String surveyName = rs.getString("surveyName");
                Long surveyTime = null;
                if ((obj = rs.getTimestamp("surveyTime")) != null) {
                    surveyTime = ((Timestamp) obj).getTime();
                }
                String manageNo = rs.getString("manageNo");
                String manageName = rs.getString("manageName");
                Long manageTime = null;
                if ((obj = rs.getTimestamp("manageTime")) != null) {
                    manageTime = ((Timestamp) obj).getTime();
                }
                Integer manageResult = rs.getInt("manageResult");
                String manageResultMsg = rs.getString("manageResultMsg");
                String vetNo = rs.getString("vetNo");
                String vetName = rs.getString("vetName");
                Long vetTime = null;
                if ((obj = rs.getTimestamp("vetTime")) != null) {
                    vetTime = ((Timestamp) obj).getTime();
                }
                Integer vetResult = rs.getInt("vetResult");
                String vetResultMsg = rs.getString("vetResultMsg");
                Integer effectStatus = rs.getInt("effectStatus");

                list.add(new RepairLog(repairId, repairLicense, repairTime, repairImg, repairMsg, surveyNo, surveyName, surveyTime, manageNo, manageName, manageTime, manageResult, manageResultMsg, vetNo, vetName, vetTime, vetResult, vetResultMsg, effectStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static int vet(RepairLog repairLog, String position) {
        String sql = "";
        if ("manage".equals(position)) {
            sql = "update test_repairlog set manageNo = ?,manageTime = ?,manageResult = ?,manageResultMsg = ? where repairId = ?";
        } else if ("vet".equals(position)) {
            sql = "update test_repairlog set vetNo = ?,vetTime = ?,vetResult = ?,vetResultMsg = ?,effectStatus = 1 where repairId = ?";
        }
        Object[] params = {
                // 暂时存为 管理员
                repairLog.getManageNo(),
                new Timestamp(repairLog.getManageTime()),
                repairLog.getManageResult(),
                repairLog.getManageResultMsg(),
                repairLog.getRepairId()
        };
        return BaseDao.executeIUD(sql, params);
    }

    public static int revoke(int repairId, Timestamp time) {
        ArrayList<RepairLog> list = RepairDao.getDetail(repairId);
        if (list.get(0).getEffectStatus() == 0) {
            String sql = "update test_repairlog set effectStatus = -1,revokeTime = ? where repairId = ?";
            Object[] params = {
                    time,
                    repairId
            };
            return BaseDao.executeIUD(sql, params);
        } else {
            return -1;
        }
    }

    public static int add(RepairLog repairLog, String position) {
        String sql = "";
        if ("survey".equals(position)) {
            sql = "insert into test_repairlog (repairLicense,repairTime,repairImg,repairMsg,surveyNo,surveyTime,manageNo) values (?,?,?,?,?,?,?)";
        } else if ("manage".equals(position)) {
            sql = "insert into  test_repairlog (repairLicense,repairTime,repairImg,repairMsg,manageNo,manageTime,vetNo) values (?,?,?,?,?,?,?)";
        }
        Object[] params = {
                repairLog.getRepairLicense(),
                new Timestamp(repairLog.getRepairTime()),
                String.join(";", repairLog.getRepairImg()),
                repairLog.getRepairMsg(),
                repairLog.getSurveyNo(),
                new Timestamp(repairLog.getSurveyTime()),
                repairLog.getVetNo()
        };
        return BaseDao.executeIUD(sql, params);
    }

}
