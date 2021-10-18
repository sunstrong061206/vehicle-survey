package com.vehicle_survey.dao;

import com.vehicle_survey.entity.LendLog;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LendDao {
    public static int addLend(LendLog lendLog){
        String sql=  "insert into test_lendlog (lendLicense,lendName,lendPhone,lendDrivecardImg,lendTime,lendDays,lendMsg,manageNo,manageLendTime,vetNo) values (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                lendLog.getLendLicense(),
                lendLog.getLendName(),
                lendLog.getLendPhone(),
                String.join(";",lendLog.getLendDrivecardImg()),
                new Timestamp(lendLog.getLendTime()),
                lendLog.getLendDays(),
                lendLog.getLendMsg(),
                lendLog.getManageNo(),
                new Timestamp(lendLog.getManageLendTime()),
                lendLog.getVetNo()
        };
        return BaseDao.executeIUD(sql,params);
    }

    public static int addReturn(LendLog lendLog){
        String sql = "update test_lendlog set returnTime = ?,returnImg = ?,manageReturnTime = ? where lendId = ?";
        Object[] params = {
                new Timestamp(lendLog.getReturnTime()),
                String.join(";",lendLog.getReturnImg()),
                new Timestamp(lendLog.getManageReturnTime()),
                lendLog.getLendId()
        };
        return  BaseDao.executeIUD(sql,params);
    }

    public static  int revoke(int lendId,long time){
        ArrayList<LendLog> list = LendDao.getDetail(lendId);
        if(list.get(0).getEffectStatus() == 0){
            String sql = "update test_lendlog set effectStatus = -1,revokeTime = ? where lendId = ?";
            Object[] params = {
                    new Timestamp(time),
                    lendId
            };
            return BaseDao.executeIUD(sql,params);
        }else return -1;
    }
    public static  int vetLend(LendLog lendLog){
        String sql ="update test_lendlog set vetNo = ?,vetLendTime = ?,vetLendResult = ?,vetLendResultMsg = ?,effectStatus = 1 where lendId = ?";
        Object[] params = {
                // 暂时存为 审批员
                lendLog.getVetNo(),
                new Timestamp(lendLog.getVetLendTime()),
                lendLog.getVetLendResult(),
                lendLog.getVetLendResultMsg(),
                lendLog.getLendId()
        };
        return BaseDao.executeIUD(sql,params);
    }

    public static  int vetReturn(LendLog lendLog){
        String sql ="update test_lendlog set vetNo = ?,vetReturnTime = ?,vetReturnResult = ?,vetReturnResultMsg = ?,effectStatus = 1 where lendId = ?";
        Object[] params = {
                // 暂时存为 审批员
                lendLog.getVetNo(),
                new Timestamp(lendLog.getVetLendTime()),
                lendLog.getVetLendResult(),
                lendLog.getVetLendResultMsg(),
                lendLog.getLendId()
        };
        return BaseDao.executeIUD(sql,params);
    }

    public static ArrayList<LendLog> getAllList(ResultSet rs) {
        ArrayList<LendLog> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Object obj;
                Integer lendId = rs.getInt("lendId");
                String lendLicense = rs.getString("lendLicense");
                String lendName = rs.getString("lendName");
                Long lendTime = null;
                if((obj = rs.getTimestamp("lendTime"))!=null) lendTime = ((Timestamp)obj).getTime();
                Integer lendDays = rs.getInt("lendDays");
                Long returnTime = null;
                if((obj = rs.getTimestamp("returnTime"))!=null) returnTime = ((Timestamp)obj).getTime();
                Integer vetLendResult = rs.getInt("vetLendResult");
                Integer vetReturnResult = rs.getInt("vetReturnResult");

                Integer effectStatus = rs.getInt("effectStatus");
                list.add(new LendLog(lendId, lendLicense, lendName, lendTime, lendDays, vetLendResult, returnTime, vetReturnResult, effectStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<LendLog> getAllList_all(String no) {
        ArrayList<LendLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from test_lendlog where ( manageNo = ? || vetNo = ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, no);
            rs = ps.executeQuery();
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<LendLog> getAllList_lendTime(String no, Long lendBegin, Long lendEnd) {
        ArrayList<LendLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from test_lendlog where ( manageNo = ? || vetNo = ?) && lendTime > ? && lendTime < ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, no);
            ps.setTimestamp(3, new Timestamp(lendBegin));
            ps.setTimestamp(4, new Timestamp(lendEnd));
            rs = ps.executeQuery();
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<LendLog> getAllList_returnTime(String no, Long returnBegin, Long returnEnd) {
        ArrayList<LendLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from test_lendlog where (manageNo = ? || vetNo = ?) && returnTime > ? && returnTime < ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, no);
            ps.setTimestamp(3, new Timestamp(returnBegin));
            ps.setTimestamp(4, new Timestamp(returnEnd));
            rs = ps.executeQuery();
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }


    public static ArrayList<LendLog> getAllList_name(String no, String name) {
        ArrayList<LendLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from test_lendlog where ( manageNo = ? || vetNo = ?) && lendName = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, no);
            ps.setString(3, name);
            rs = ps.executeQuery();
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<LendLog> getAllList_license(String no, String license) {
        ArrayList<LendLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from test_lendlog where ( manageNo = ? || vetNo = ?) && lendLicense regexp ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, no);
            ps.setString(3, license);
            rs = ps.executeQuery();
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }


    public static ArrayList<LendLog> getAllList_lendResult(String no, Integer result) {
        ArrayList<LendLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String sql = "";
            if (result == 1)
                sql = "select * from test_lendlog where (manageNo = ? || vetNo = ?) && vetLendResult = 1";
            else
                sql = "select * from test_lendlong where ( manageNo = ? || vetNo = ?) && vetLendResult = 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, no);
            rs = ps.executeQuery();
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }


    public static ArrayList<LendLog> getAllList_returnResult(String no, Integer result) {
        ArrayList<LendLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            String sql = "";
            if (result == 1)
                sql = "select * from test_lendlog where (manageNo = ? || vetNo = ?) && vetReturnResult = 1";
            else if (result == 0)
                sql = "select * from test_lendlong where (manageNo = ? || vetNo = ?) && vetReturnResult = 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, no);
            rs = ps.executeQuery();
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<LendLog> getAllList_isVet(String no,Boolean isVet){
        ArrayList<LendLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "";
            if(isVet == false) sql = "select * from test_lendlog where vetNo = ? && vetLendResult is null && revokeTime is null";
            else sql = "select * from test_lendlog where vetNo = ? && vetLendResult is not null";
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            rs = ps.executeQuery();
            return getAllList(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    public static ArrayList<LendLog> getDetail(int id) {
        ArrayList<LendLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

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
                Long lendTime = null;
                if((obj = rs.getTimestamp("lendTime"))!=null) lendTime = ((Timestamp)obj).getTime();
                Integer lendDays = rs.getInt("lendDays");
                String lendMsg = rs.getString("lendMsg");
                String manageNo = rs.getString("manageNo");
                Long manageLendTime = null;
                if((obj = rs.getTimestamp("manageLendTime"))!=null) manageLendTime = ((Timestamp)obj).getTime();
                String vetNo = rs.getString("vetNo");
                Long vetLendTime = null;
                if((obj = rs.getTimestamp("vetLendTime"))!=null) vetLendTime = ((Timestamp)obj).getTime();
                Integer vetLendResult = rs.getInt("vetLendResult");
                String vetLendResultMsg = rs.getString("vetLendResultMsg");
                Long returnTime = null;
                if((obj = rs.getTimestamp("returnTime"))!=null) returnTime = ((Timestamp)obj).getTime();
                ArrayList<String> returnImg = new ArrayList<>(Arrays.asList(rs.getString("returnImg").split(";")));
                Long manageReturnTime = null;
                if((obj = rs.getTimestamp("manageReturnTime"))!=null) manageReturnTime = ((Timestamp)obj).getTime();
                Long vetReturnTime = null;
                if((obj =  rs.getTimestamp("vetReturnTime"))!=null) vetReturnTime = ((Timestamp)obj).getTime();
                Integer vetReturnResult = rs.getInt("vetReturnResult");
                String vetReturnResultMsg = rs.getString("vetReturnResultMsg");
                Integer effectStatus = rs.getInt("effectStatus");
                String manageName = rs.getString("manageName");
                String vetName = rs.getString("vetName");

                list.add(new LendLog(lendId,lendLicense,lendName,lendPhone,lendDrivecardImg,lendTime,lendDays,lendMsg,manageNo,manageName,manageLendTime,vetNo,vetName,vetLendTime,vetLendResult,vetLendResultMsg,returnTime,returnImg,manageReturnTime,vetReturnTime,vetReturnResult,vetReturnResultMsg,effectStatus));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            BaseDao.closeAll(conn,ps,rs);
        }
        return list;
    }
}
