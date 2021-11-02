package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.entity.PunishLog;

import java.sql.*;
import java.util.ArrayList;

public class PunishDao {
    public static ArrayList<PunishLog> getDetail(int id){
        ArrayList<PunishLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Timestamp accidentTime = null;

        try{
            String sql = "select pun.*,empSurvey.name as surveyName from test_punishlog pun left join test_employee empSurvey on empSurvey.no = pun.surveyNo where punishId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                Object obj = null;
                Integer punishId = rs.getInt("punishId");
                String surveyNo = rs.getString("surveyNo");
                String surveyName = rs.getString("surveyName");
                String accidentMsg= rs.getString("accidentMsg");
                Long accidentTime1 = null;
                if((obj = rs.getTimestamp("accidentTime"))!=null) {
                    accidentTime1 = ((Timestamp)obj).getTime();
                    accidentTime = new Timestamp(accidentTime1);
                }
                String accidentPoint = rs.getString("accidentPoint");
                String punishMsg = rs.getString("punishMsg");
                Double punishAmount = rs.getDouble("punishAmount");
                Integer payStatus = rs.getInt("payStatus");
                String license = rs.getString("license");

                list.add(new PunishLog(punishId,surveyNo,surveyName,accidentMsg,accidentTime,accidentPoint,punishMsg,punishAmount,payStatus,license));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            BaseDao.closeAll(conn,ps,rs);
        }
        return list;
    }
}
