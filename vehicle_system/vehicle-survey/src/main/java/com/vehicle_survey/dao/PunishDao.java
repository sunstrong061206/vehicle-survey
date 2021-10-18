package com.vehicle_survey.dao;

import com.vehicle_survey.entity.PunishLog;

import java.sql.*;
import java.util.ArrayList;

public class PunishDao {
    public  static ArrayList<PunishLog> getAllList(ResultSet rs){
        ArrayList<PunishLog> list = new ArrayList<>();
        try{
            while(rs.next()){
                Object obj = null;
                int punishId = rs.getInt("punishId");
                String accidentMsg = rs.getString("accidentMsg");
                String accidentPoint = rs.getString("accidentPoint");
                Long accidentTime = null;
                if((obj = rs.getTimestamp("accidentTime"))!=null) accidentTime =((Timestamp)obj).getTime();
                String punishMsg = rs.getString("punishMsg");
                Double punishAmount = rs.getDouble("punishAmount");
                Integer payStatus = rs.getInt("payStatus");

                PunishLog punishLog = new PunishLog(punishId,accidentMsg,accidentTime,accidentPoint,punishMsg,punishAmount,payStatus);
                list.add(punishLog);
            }
            return list;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<PunishLog> getAll_all(String no){
        ArrayList<PunishLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet  rs = null;

        try{
            String sql = "select * from test_punishlog where surveyNo  = ? order by punishId desc";
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
        return  list;
    }

    public static ArrayList<PunishLog> getAll_time(String no,long begin,long end){
        ArrayList<PunishLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet  rs = null;

        try{
            String sql = "select * from test_punishlog where surveyNo  = ? && accidentTime > ? && accidentTime < ? order by punishId desc";
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
        return  list;
    }

    public static ArrayList<PunishLog> getAll_license(String no,String license){
        ArrayList<PunishLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet  rs = null;

        try{
            String sql = "select * from test_punishlog where surveyNo  = ? && license regexp ? order by punishId desc";
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
        return  list;
    }


    public static ArrayList<PunishLog> getAll_payStatus(String no,int payStatus){
        ArrayList<PunishLog> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet  rs = null;

        try{
            String sql = "select * from test_punishlog where surveyNo  = ? && payStatus = ? order by punishId desc";
            ps = conn.prepareStatement(sql);
            ps.setString(1,no);
            ps.setInt(2,payStatus);
            rs = ps.executeQuery();
            list = getAllList(rs);
            return list;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return  list;
    }

    public static  ArrayList<PunishLog> getDetail(int id){
        ArrayList<PunishLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

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
                Long accidentTime = null;
                if((obj = rs.getTimestamp("accidentTime"))!=null) accidentTime = ((Timestamp)obj).getTime();
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
