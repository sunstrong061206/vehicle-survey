package com.vehicle_survey.dao;

import com.vehicle_survey.entity.ProtectLog;

import java.sql.*;
import java.util.ArrayList;

public class ProtectDao {
    public static int add(ProtectLog protectLog){
        String sql = "insert into test_protectlog (protectLicense,protectTime,protectMsg,manageNo,manageTime,vetNo) values (?,?,?,?,?,?)";
        Object[] params = {
                protectLog.getProtectLicense(),
                new Timestamp( protectLog.getProtectTime()),
                protectLog.getProtectMsg(),
                protectLog.getManageNo(),
                new Timestamp(protectLog.getManageTime()),
                protectLog.getVetNo()
        };
        return  BaseDao.executeIUD(sql,params);
    }

    public static int vet(ProtectLog protectLog){
        String sql  ="update test_protectlog set vetNo = ?,vetTime = ?,result = ?,resultMsg = ?,effectStatus = 1 where protectId = ?";
        Object[] params = {
                protectLog.getVetNo(),
                new Timestamp(protectLog.getVetTime()),
                protectLog.getResult(),
                protectLog.getResultMsg(),
                protectLog.getProtectId()
        };
        return BaseDao.executeIUD(sql, params);
    }

    public static int revoke(int protectId,long time){
        ArrayList<ProtectLog> list = ProtectDao.getDetail(protectId);
        if (list.get(0).getEffectStatus() == 0) {
            String sql  ="update test_protectlog set effectStatus = -1,revokeTime = ? where protectId = ?";
            Object[] params = {
                    new Timestamp(time),
                    protectId
            };
            return BaseDao.executeIUD(sql,params);
        }else return -1;
    }
    public static ArrayList<ProtectLog> getAllList(ResultSet rs){
        ArrayList<ProtectLog> list = new ArrayList<>();
        try{
            while(rs.next()){
                Object obj;
                Integer protectId = rs.getInt("protectId");
                String protectLicense = rs.getString("protectLicense");
                Long protectTime = null;
                if((obj=rs.getTimestamp("protectTime"))!=null) protectTime = ((Timestamp)obj).getTime();
                String protectMsg = rs.getString("protectMsg");
                list.add(new ProtectLog(protectId,protectLicense,protectTime,protectMsg));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<ProtectLog> getAll_all(String no){
        ArrayList<ProtectLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select * from test_protectlog where manageNo = ? order by protectId desc";
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
        return list;
    }

    public static ArrayList<ProtectLog> getAll_time(String no,long begin,long end){
        ArrayList<ProtectLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select * from test_protectlog where manageNo = ? && protectTime > ? && protectTime < ? order by protectId desc";
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            ps.setTimestamp(2,new Timestamp(begin));
            ps.setTimestamp(3,new Timestamp(end));
            rs = ps.executeQuery();
            list = getAllList(rs);
            return list;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<ProtectLog> getAll_license(String no,String license){
        ArrayList<ProtectLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select * from test_protectlog where manageNo = ? && protectLicense regexp ? order by protectId desc";
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            ps.setString(2,license);
            rs = ps.executeQuery();
            list = getAllList(rs);
            return list;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<ProtectLog> getAll_result(String no,int result){
        ArrayList<ProtectLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql;
            if(result == 1){ // 通过
                sql = "select * from test_protectlog where manageNo = ? && result = 1 && effectStatus != 1 order by protectId desc";
            }else{ // 未通过
                sql = "select * from test_protectlog where manageNo = ? && result = 0 && effectStatus != 1 order by protectId desc";
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
        return list;
    }

    public static  ArrayList<ProtectLog> getAll_isVet(String no,Boolean isVet){
        ArrayList<ProtectLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs= null;

        try{
            String sql ="";
            if(isVet = false) sql  ="select * from test_protectlog where vetNo = ? && result is null && revokeTime is null";
            else sql = "select * from test_protectlog where vetNo = ? && result is not null";
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            rs = ps.executeQuery();
            return  getAllList(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    public  static  ArrayList<ProtectLog> getDetail(int id){
        ArrayList<ProtectLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs= null;

        try{
            String sql = "select pro.*,empManage.name as manageName,empVet.name as vetName from test_protectlog pro left join test_employee empManage on empManage.no = pro.manageNo left join test_employee empVet on empVet.no = pro.vetNo where pro.protectId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                Object obj;
                Integer protectId= rs.getInt("protectId");
                String protectLicense = rs.getString("protectLicense");
                Long protectTime = null;
                if((obj = rs.getTimestamp("protectTime"))!=null) protectTime = ((Timestamp)obj).getTime();
                String protectMsg  = rs.getString("protectMsg");
                String manageNo = rs.getString("manageNo");
                String manageName = rs.getString("manageName");
                Long manageTime = rs.getTimestamp("manageTime").getTime();
                String vetNo = rs.getString("vetNo");
                String vetName = rs.getString("vetName");
                Long vetTime = null;
                if((obj = rs.getTimestamp("vetTime"))!=null) vetTime = ((Timestamp)obj).getTime();
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
}
