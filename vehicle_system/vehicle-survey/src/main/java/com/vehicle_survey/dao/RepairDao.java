package com.vehicle_survey.dao;

import com.vehicle_survey.controller.get.getAll.Repair;
import com.vehicle_survey.entity.RepairConcise;
import com.vehicle_survey.entity.RepairLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RepairDao {
    public static int add(RepairLog repairLog, String position) {
        String sql = "";
        if ("survey".equals(position))
            sql = "insert into test_repairlog (repairLicense,repairTime,repairImg,repairMsg,surveyNo,surveyTime,manageNo) values (?,?,?,?,?,?,?)";
        else if ("manage".equals(position))
            sql = "insert into  test_repairlog (repairLicense,repairTime,repairImg,repairMsg,manageNo,manageTime,vetNo) values (?,?,?,?,?,?,?)";
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

    public static int vet(RepairLog repairLog, String position) {
        String sql = "";
        if ("manage".equals(position))
            sql = "update test_repairlog set manageNo = ?,manageTime = ?,manageResult = ?,manageResultMsg = ? where repairId = ?";
        else if ("vet".equals(position))
            sql = "update test_repairlog set vetNo = ?,vetTime = ?,vetResult = ?,vetResultMsg = ?,effectStatus = 1 where repairId = ?";
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

    public static int revoke(int repairId, long time) {
        ArrayList<RepairLog> list = RepairDao.getDetail(repairId);
        if (list.get(0).getEffectStatus() == 0) {
            String sql = "update test_repairlog set effectStatus = -1,revokeTime = ? where repairId = ?";
            Object[] params = {
                    new Timestamp(time),
                    repairId
            };
            return BaseDao.executeIUD(sql, params);
        } else return -1;
    }

    public static ArrayList<RepairConcise> getAllList(ResultSet rs) {
        ArrayList<RepairConcise> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Object obj;
                int repairId = rs.getInt("repairId");
                String repairLicense = rs.getString("repairLicense");
                Long repairTime = null;
                if ((obj = rs.getTimestamp("repairTime")) != null) repairTime = ((Timestamp) obj).getTime();
                String repairMsg = rs.getString("repairMsg");
                // 讨论 申报人信息
                String name;
                String surveyName = rs.getString("surveyName");
                if (surveyName != null) name = surveyName;
                else name = rs.getString("manageName");
                Integer result = null;
                Integer manageResult = rs.getInt("manageResult");
                Integer vetResult = rs.getInt("vetResult");
                if (manageResult == 1 && vetResult == 1) result = 1;

                Integer effectStatus = rs.getInt("effectStatus");

                RepairConcise repairConcise = new RepairConcise(repairId, repairLicense, repairTime, repairMsg, name, result, effectStatus);
                list.add(repairConcise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<RepairConcise> getAll_all(String no, String pos) {
        ArrayList<RepairConcise> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if ("survey".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.surveyNo = ?";
            else if ("manage".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ?";
            else if ("vet".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            rs = ps.executeQuery();
            list = getAllList(rs);
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<RepairConcise> getAll_time(String no, String pos, long begin, long end) {
        ArrayList<RepairConcise> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if ("drive".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.surveyNo = ? && repairTime > ? && repairTime < ?";
            else if ("manage".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? && repairTime > ? && repairTime < ?";
            else if ("vet".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? && repairTime > ? && repairTime < ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setTimestamp(2, new Timestamp(begin));
            ps.setTimestamp(3, new Timestamp(end));
            rs = ps.executeQuery();
            list = getAllList(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<RepairConcise> getAll_license(String no, String pos, String license) {
        ArrayList<RepairConcise> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if ("drive".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.surveyNo = ? && repairLicense regexp ?";
            else if ("manage".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? && repairLicense regexp ?";
            else if ("vet".equals(pos))
                sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? && repairLicense regexp ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, license);
            rs = ps.executeQuery();
            list = getAllList(rs);
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }


    // 通过的维修记录  指的是审批通过+生效
    public static ArrayList<RepairConcise> getAll_result(String no, String pos, int result) {
        ArrayList<RepairConcise> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if ("drive".equals(pos)) {
                if (result == 1)
                    sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.surveyNo = ? &&  rep.vetResult = 1 && rep.effectStatus != -1";
                else
                    sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.surveyNo = ? &&  rep.vetResult = 0 && rep.effectStatus != -1";
            } else if ("manage".equals(pos))
                if (result == 1)
                    sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? &&  rep.vetResult = 1 && rep.effectStatus != -1";
                else
                    sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? &&  rep.vetResult = 0 && rep.effectStatus != -1";

            else if ("vet".equals(pos))
                if (result == 1)
                    sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? &&  rep.vetResult = 1 && rep.effectStatus != -1";
                else
                    sql = "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? &&  rep.vetResult = 0 && rep.effectStatus != -1";

            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            rs = ps.executeQuery();
            list = getAllList(rs);
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static  ArrayList<RepairConcise> getAll_isVet(String no,String pos,Boolean isVet){
        ArrayList<RepairConcise> list =new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql  ="";
            if("manage".equals(pos)){
                if(isVet == false) sql= "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? && rep.manageResult is null && rep.surveyNo is not null && rep.revokeTime is null";
                else sql= "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.manageNo = ? && rep.manageResult is not null && rep.surveyNo is not null ";
            }else {
                if(isVet == false) sql= "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? && rep.vetResult is null  && rep.revokeTime is null";
                else sql= "select rep.*,empSurvey.name as surveyName,empManage.name as manageName from test_repairlog rep left join test_employee empSurvey on rep.surveyNo = empSurvey.no left join test_employee empManage on rep.manageNo = empManage.no where rep.vetNo = ? && rep.vetResult is not null";
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            rs = ps.executeQuery();
            list = getAllList(rs);
            return list;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

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
                if ((obj = rs.getTimestamp("repairTime")) != null) repairTime = ((Timestamp) obj).getTime();
                ArrayList<String> repairImg = new ArrayList<String>(Arrays.asList(rs.getString("repairImg").split(";")));
                String repairMsg = rs.getString("repairMsg");
                String surveyNo = rs.getString("surveyNo");
                String surveyName = rs.getString("surveyName");
                Long surveyTime = null;
                if ((obj = rs.getTimestamp("surveyTime")) != null) surveyTime = ((Timestamp) obj).getTime();
                String manageNo = rs.getString("manageNo");
                String manageName = rs.getString("manageName");
                Long manageTime = null;
                if ((obj = rs.getTimestamp("manageTime")) != null) manageTime = ((Timestamp) obj).getTime();
                Integer manageResult = rs.getInt("manageResult");
                String manageResultMsg = rs.getString("manageResultMsg");
                String vetNo = rs.getString("vetNo");
                String vetName = rs.getString("vetName");
                Long vetTime = null;
                if ((obj = rs.getTimestamp("vetTime")) != null) vetTime = ((Timestamp) obj).getTime();
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

}
