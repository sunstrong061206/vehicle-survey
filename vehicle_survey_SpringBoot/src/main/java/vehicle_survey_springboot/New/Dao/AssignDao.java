package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.entity.AssignLog;

import java.sql.*;
import java.util.ArrayList;

public class AssignDao {
    public static ArrayList<AssignLog> getDetail(int id){
        ArrayList<AssignLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Timestamp manageTime = null;
        Timestamp vetTime = null;

        try{
            String sql = "select ass.*,empSurvey.name surveyName,empManage.name manageName,empVet.name vetName from test_assignlog ass left join test_employee empSurvey on empSurvey.no = ass.surveyNo left join test_employee empManage on empManage.no = ass.manageNo left join test_employee empVet on empVet.no = ass.vetNo where assignId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                Object obj;
                Integer assignId = rs.getInt("assignId");
                String assignLicense = rs.getString("assignLicense");
                Long assignTime1 = rs.getTimestamp("assignTime").getTime();
                Timestamp assignTime = new Timestamp(assignTime1);
                String assignPoint = rs.getString("assignPoint");
                String assignMsg = rs.getString("assignMsg");
                String surveyNo = rs.getString("surveyNo");
                String manageNo = rs.getString("manageNo");
                Long manageTime1 = null;
                if((obj = rs.getTimestamp("manageTime"))!=null) {
                    manageTime1 = ((Timestamp)obj).getTime();
                    manageTime = new Timestamp(manageTime1);
                }
                String vetNo = rs.getString("vetNo");
                Long vetTime1 = null;
                if((obj=rs.getTimestamp("vetTime"))!=null) {
                    vetTime1 = ((Timestamp)obj).getTime();
                    vetTime = new Timestamp(vetTime1);
                }
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

    public  static int vet(AssignLog assignLog){
        String sql = "update test_assignlog set vetNo = ?,vetTime = ?,result = ?,resultMsg = ?,effectStatus = 1 where assignId = ?";
        Object[] params = {
                assignLog.getVetNo(),
                assignLog.getVetTime(),
                assignLog.getResult(),
                assignLog.getResultMsg(),
                assignLog.getAssignId()
        };
        return BaseDao.executeIUD(sql,params);
    }

    public static int revoke(int assignId,Timestamp time){
        ArrayList<AssignLog> list = AssignDao.getDetail(assignId);
        if(list.get(0).getEffectStatus() == 0) {
            String sql = "update test_assignlog set effectStatus = -1,revokeTime = ? where assignId = ?";
            Object[] params = {
                    time,
                    assignId
            };
            return BaseDao.executeIUD(sql, params);
        }else {
            return -1;
        }
    }


}
