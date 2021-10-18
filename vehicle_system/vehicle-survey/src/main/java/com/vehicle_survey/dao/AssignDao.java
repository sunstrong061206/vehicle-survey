package com.vehicle_survey.dao;


import com.vehicle_survey.entity.AssignLog;


import java.sql.*;
import java.util.ArrayList;

public class AssignDao {

    public static int add(AssignLog assignLog){
        String sql = "insert into test_assignlog (assignLicense,assignTime,assignPoint,assignMsg,surveyNo,manageNo,manageTime,vetNo) values (?,?,?,?,?,?,?,?)";
        Object[] params ={
                assignLog.getAssignLicense(),
                new Timestamp(assignLog.getAssignTime()),
                assignLog.getAssignPoint(),
                assignLog.getAssignMsg(),
                assignLog.getSurveyNo(),
                assignLog.getManageNo(),
                new Timestamp(assignLog.getManageTime()),
                assignLog.getVetNo()
        };
        return BaseDao.executeIUD(sql,params);

    }

    public static int revoke(int assignId,long time){
        ArrayList<AssignLog> list = AssignDao.getDetail(assignId);
        if(list.get(0).getEffectStatus() == 0) {
            String sql = "update test_assignlog set effectStatus = -1,revokeTime = ? where assignId = ?";
            Object[] params = {
                    new Timestamp(time),
                    assignId
            };
            return BaseDao.executeIUD(sql, params);
        }else return -1;
    }

    public  static int vet(AssignLog assignLog){
        String sql = "update test_assignlog set vetNo = ?,vetTime = ?,result = ?,resultMsg = ?,effectStatus = 1 where assignId = ?";
        Object[] params = {
                assignLog.getVetNo(),
                new Timestamp(assignLog.getVetTime()),
                assignLog.getResult(),
                assignLog.getResultMsg(),
                assignLog.getAssignId()
        };
        return BaseDao.executeIUD(sql,params);
    }

    public static ArrayList<AssignLog> getAllList(ResultSet rs){
        ArrayList<AssignLog> list = new ArrayList<>();
        try {
            while (rs.next()) {
                int assignId = rs.getInt("assignId");
                long assignTime = rs.getTimestamp("assignTime").getTime();
                String assignLicense = rs.getString("assignLicense");
                int result = rs.getInt("result");
                int effectStatus = rs.getInt("effectStatus");

                AssignLog assignLog = new AssignLog(assignId, assignLicense, assignTime, result, effectStatus);
                list.add(assignLog);
            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<AssignLog> getAll_all(String no, String pos) {
        ArrayList<AssignLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            // 勘查员只能看到审批通过的派车单
            if ("survey".equals(pos))
                sql = "select * from test_assignlog where surveyNo = ? && result = 1 order by assignId desc";
            else if ("manage".equals(pos)) sql = "select * from test_assignlog where manageNo = ? order by assignId desc ";
            else if ("vet".equals(pos)) sql = "select  * from test_assignlog where vetNo = ? order by assignId desc ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            rs = ps.executeQuery();

            list = getAllList(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    public static ArrayList<AssignLog> getAll_time(String no, String pos, long begin, long end) {
        ArrayList<AssignLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            // 勘查员按照出车时间筛选
            if ("survey".equals(pos))
                sql = "select * from test_assignlog where surveyNo = ? && result = 1 && assignTime > ? && assignTime < ? order by assignId desc";
            else if ("manage".equals(pos)) sql = "select * from test_assignlog where manageNo = ? && manageTime > ? && manageTime < ? order by assignId desc ";
            else if ("vet".equals(pos)) sql = "select  * from test_assignlog where vetNo = ? && vetTime > ? && vetTime < ? order by assignId desc ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            ps.setTimestamp(2,new Timestamp(begin));
            ps.setTimestamp(3,new Timestamp(end));
            rs = ps.executeQuery();

            list = getAllList(rs);
            return  list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    public static ArrayList<AssignLog> getAll_license(String no, String pos, String license) {
        ArrayList<AssignLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if ("survey".equals(pos))
                sql = "select * from test_assignlog where surveyNo = ? && result = 1 && assignLicense regexp ? order by assignId desc";
            else if ("manage".equals(pos)) sql = "select * from test_assignlog where manageNo = ? && assignLicense regexp ? order by assignId desc ";
            else if ("vet".equals(pos)) sql = "select  * from test_assignlog where vetNo = ? && assignLicense regexp ? order by assignId desc ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            ps.setString(2,license);
            rs = ps.executeQuery();

            list = getAllList(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    public static ArrayList<AssignLog> getAll_result(String no, String pos, int result) {
        ArrayList<AssignLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if(result == 1){
                if ("survey".equals(pos))
                    sql = "select * from test_assignlog where surveyNo = ? && result = 1  && effectStatus != -1 order by assignId desc";
                else if ("manage".equals(pos)) sql = "select * from test_assignlog where manageNo = ? && result = 1 && effectStatus != -1 order by assignId desc ";
                else if ("vet".equals(pos)) sql = "select  * from test_assignlog where vetNo = ? && result = 1 && effectStatus != -1 order by assignId desc ";

            }
            else {
                if ("survey".equals(pos))
                    sql = "select * from test_assignlog where surveyNo = ? && result = 1 && result = 0 && effectStatus != -1 order by assignId desc";
                else if ("manage".equals(pos)) sql = "select * from test_assignlog where manageNo = ? && result = 0 && effectStatus != -1 order by assignId desc ";
                else if ("vet".equals(pos)) sql = "select  * from test_assignlog where vetNo = ? && result = 0 && effectStatus != -1 order by assignId desc ";

            }
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            rs = ps.executeQuery();

            list = getAllList(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    public static ArrayList<AssignLog> getAll_isVet(String no,Boolean isVet){
        ArrayList<AssignLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if(isVet == false){
                sql = "select * from test_assignlog where vetNo = ? && result is null && revokeTime  is null";
            }else sql = "select * from test_assignlog where vetNo = ? && result is not null";

            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            rs = ps.executeQuery();

            list = getAllList(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    public static ArrayList<AssignLog> getDetail(int id){
        ArrayList<AssignLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select ass.*,empSurvey.name surveyName,empManage.name manageName,empVet.name vetName from test_assignlog ass left join test_employee empSurvey on empSurvey.no = ass.surveyNo left join test_employee empManage on empManage.no = ass.manageNo left join test_employee empVet on empVet.no = ass.vetNo where assignId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                Object obj;
                Integer assignId = rs.getInt("assignId");
                String assignLicense = rs.getString("assignLicense");
                Long assignTime = rs.getTimestamp("assignTime").getTime();
                String assignPoint = rs.getString("assignPoint");
                String assignMsg = rs.getString("assignMsg");
                String surveyNo = rs.getString("surveyNo");
                String manageNo = rs.getString("manageNo");
                Long manageTime = null;
                if((obj = rs.getTimestamp("manageTime"))!=null) manageTime = ((Timestamp)obj).getTime();
                String vetNo = rs.getString("vetNo");
                Long vetTime = null;
                if((obj=rs.getTimestamp("vetTime"))!=null) vetTime = ((Timestamp)obj).getTime();
                Integer result = rs.getInt("result");
                String resultMsg = rs.getString("resultMsg");
                Integer effectStatus = rs.getInt("effectStatus");
                String surveyName = rs.getString("surveyName");
                String manageName = rs.getString("manageName");
                String vetName= rs.getString("vetName");

                list.add(new AssignLog(assignId,assignLicense,assignTime,assignPoint,assignMsg,surveyNo,surveyName,manageNo,manageName,manageTime,vetNo,vetName,vetTime,result,resultMsg,effectStatus));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return  list;
    }
}
