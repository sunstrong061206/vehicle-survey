package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.entity.ProtectLog;

import java.sql.*;
import java.util.ArrayList;

public class ProtectDao {

    public  static ArrayList<ProtectLog> getDetail(int id){
        ArrayList<ProtectLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs= null;
        Timestamp vetTime = null;
        Timestamp protectTime = null;


        try{
            String sql = "select pro.*,empManage.name as manageName,empVet.name as vetName from test_protectlog pro left join test_employee empManage on empManage.no = pro.manageNo left join test_employee empVet on empVet.no = pro.vetNo where pro.protectId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                Object obj;
                Integer protectId= rs.getInt("protectId");
                String protectLicense = rs.getString("protectLicense");
                Long protectTime1 = null;
                if((obj = rs.getTimestamp("protectTime"))!=null) {
                    protectTime1 = ((Timestamp)obj).getTime();
                    protectTime = new Timestamp(protectTime1);
                }
                String protectMsg  = rs.getString("protectMsg");
                String manageNo = rs.getString("manageNo");
                String manageName = rs.getString("manageName");
                Long manageTime1 = rs.getTimestamp("manageTime").getTime();
                Timestamp manageTime  = new Timestamp(manageTime1);
                String vetNo = rs.getString("vetNo");
                String vetName = rs.getString("vetName");
                Long vetTime1 = null;
                if((obj = rs.getTimestamp("vetTime"))!=null) {
                    vetTime1 = ((Timestamp)obj).getTime();
                    vetTime = new Timestamp(vetTime1);
                }
                Integer result = rs.getInt("result");
                String resultMsg = rs.getString("resultMsg");
                Integer effectStatus = rs.getInt("effectStatus");

                list.add(new ProtectLog(protectId,protectLicense,protectTime,protectMsg,manageNo,manageName,manageTime,vetNo,vetName,vetTime,result,resultMsg,effectStatus));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeAll(conn,ps,rs);
        }
        return list;
    }
    public static int revoke(int protectId,Timestamp time){
        ArrayList<ProtectLog> list = ProtectDao.getDetail(protectId);
        if (list.get(0).getEffectStatus() == 0) {
            String sql  ="update test_protectlog set effectStatus = -1,revokeTime = ? where protectId = ?";
            Object[] params = {
                    time,
                    protectId
            };
            return BaseDao.executeIUD(sql,params);
        }else {
            return -1;
        }
    }

    public static int add(ProtectLog protectLog){
        String sql = "insert into test_protectlog (protectLicense,protectTime,protectMsg,manageNo,manageTime,vetNo) values (?,?,?,?,?,?)";
        Object[] params = {
                protectLog.getProtectLicense(),
                protectLog.getProtectTime(),
                protectLog.getProtectMsg(),
                protectLog.getManageNo(),
                protectLog.getManageTime(),
                protectLog.getVetNo()
        };
        return  BaseDao.executeIUD(sql,params);
    }
}

