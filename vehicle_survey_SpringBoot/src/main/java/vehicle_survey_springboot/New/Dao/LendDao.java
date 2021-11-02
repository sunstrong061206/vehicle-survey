package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.entity.LendLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LendDao {
    public static ArrayList<LendLog> getDetail(int id) {
        ArrayList<LendLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Timestamp vetLendTime = null;
        Timestamp lendTime = null;
        Timestamp manageLendTime = null;
        Timestamp returnTime = null;
        Timestamp manageReturnTime = null;
        Timestamp vetReturnTime = null;

        try {
            String sql = "select len.*,empManage.name manageName,empVet.name vetName from test_lendlog len  left join test_employee empManage on len.manageNo = empManage.no left join test_employee empVet on len.vetNo = empVet.no where lendId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object obj;
                Integer lendId = rs.getInt("lendId");
                String lendLicense = rs.getString("lendLicense");
                String lendName = rs.getString("lendName");
                String lendPhone = rs.getString("lendPhone");
                ArrayList<String> lendDrivecardImg = new ArrayList<>(Arrays.asList(rs.getString("lendDrivecardImg").split(";")));
                Long lendTime1 = null;
                if ((obj = rs.getTimestamp("lendTime")) != null) {
                    lendTime1 = ((Timestamp) obj).getTime();
                    lendTime = new Timestamp(lendTime1);
                }

                Integer lendDays = rs.getInt("lendDays");
                String lendMsg = rs.getString("lendMsg");
                String manageNo = rs.getString("manageNo");
                Long manageLendTime1 = null;
                if ((obj = rs.getTimestamp("manageLendTime")) != null) {
                    manageLendTime1 = ((Timestamp) obj).getTime();
                    manageLendTime = new Timestamp(manageLendTime1);
                }

                String vetNo = rs.getString("vetNo");
                Long vetLendTime1 = null;
                if ((obj = rs.getTimestamp("vetLendTime")) != null) {
                    vetLendTime1 = ((Timestamp) obj).getTime();
                    vetLendTime = new Timestamp(vetLendTime1);
                }
                Integer vetLendResult = rs.getInt("vetLendResult");
                String vetLendResultMsg = rs.getString("vetLendResultMsg");
                Long returnTime1 = null;
                if ((obj = rs.getTimestamp("returnTime")) != null) {
                    returnTime1 = ((Timestamp) obj).getTime();
                    returnTime = new Timestamp(returnTime1);
                }
                ArrayList<String> returnImg = null;
                String images = rs.getString("returnImg");
                if(images!=null) returnImg = new ArrayList<>(Arrays.asList(images.split(";")));
                Long manageReturnTime1 = null;
                if ((obj = rs.getTimestamp("manageReturnTime")) != null) {
                    manageReturnTime1 = ((Timestamp) obj).getTime();
                    manageReturnTime = new Timestamp(manageLendTime1);
                }
                Long vetReturnTime1 = null;
                if ((obj = rs.getTimestamp("vetReturnTime")) != null) {
                    vetReturnTime1 = ((Timestamp) obj).getTime();
                    vetReturnTime = new Timestamp(vetLendTime1);
                }
                Integer vetReturnResult = rs.getInt("vetReturnResult");
                String vetReturnResultMsg = rs.getString("vetReturnResultMsg");
                Integer effectStatus = rs.getInt("effectStatus");
                String manageName = rs.getString("manageName");
                String vetName = rs.getString("vetName");

                list.add(new LendLog(lendId, lendLicense, lendName, lendPhone, lendDrivecardImg, lendTime, lendDays, lendMsg, manageNo, manageName, manageLendTime, vetNo, vetName, vetLendTime, vetLendResult, vetLendResultMsg, returnTime, returnImg, manageReturnTime, vetReturnTime, vetReturnResult, vetReturnResultMsg, effectStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }
    public static int vetLend(LendLog lendLog) {
        String sql = "update test_lendlog set vetNo = ?,vetLendTime = ?,vetLendResult = ?,vetLendResultMsg = ?,effectStatus = 1 where lendId = ?";
        Object[] params = {
                // 暂时存为 审批员
                lendLog.getVetNo(),
                lendLog.getVetLendTime(),
                lendLog.getVetLendResult(),
                lendLog.getVetLendResultMsg(),
                lendLog.getLendId()
        };
        return BaseDao.executeIUD(sql, params);
    }

    public static int vetReturn(LendLog lendLog) {
        String sql = "update test_lendlog set vetNo = ?,vetReturnTime = ?,vetReturnResult = ?,vetReturnResultMsg = ?,effectStatus = 1 where lendId = ?";
        Object[] params = {
                // 暂时存为 审批员
                lendLog.getVetNo(),
                lendLog.getVetLendTime(),
                lendLog.getVetLendResult(),
                lendLog.getVetLendResultMsg(),
                lendLog.getLendId()
        };
        return BaseDao.executeIUD(sql, params);
    }

    public static int revoke(int lendId, Timestamp time) {
        ArrayList<LendLog> list = LendDao.getDetail(lendId);
        if (list.get(0).getEffectStatus() == 0) {
            String sql = "update test_lendlog set effectStatus = -1,revokeTime = ? where lendId = ?";
            Object[] params = {
                    time,
                    lendId
            };
            return BaseDao.executeIUD(sql, params);
        } else {
            return -1;
        }
    }

    public static int addLend(LendLog lendLog) {
        String sql = "insert into test_lendlog (lendLicense,lendName,lendPhone,lendDrivecardImg,lendTime,lendDays,lendMsg,manageNo,manageLendTime,vetNo) values (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                lendLog.getLendLicense(),
                lendLog.getLendName(),
                lendLog.getLendPhone(),
                String.join(";", lendLog.getLendDrivecardImg()),
                lendLog.getLendTime(),
                lendLog.getLendDays(),
                lendLog.getLendMsg(),
                lendLog.getManageNo(),
                lendLog.getManageLendTime(),
                lendLog.getVetNo()
        };
        return BaseDao.executeIUD(sql, params);
    }

    public static int addReturn(LendLog lendLog) {
        String sql = "update test_lendlog set returnTime = ?,returnImg = ?,manageReturnTime = ? where lendId = ?";
        Object[] params = {
                lendLog.getReturnTime(),
                String.join(";", lendLog.getReturnImg()),
                lendLog.getManageReturnTime(),
                lendLog.getLendId()
        };
        return BaseDao.executeIUD(sql, params);
    }
}
