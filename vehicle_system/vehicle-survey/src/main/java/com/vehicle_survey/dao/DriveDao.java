package com.vehicle_survey.dao;

import com.vehicle_survey.entity.DriveLog;
import com.vehicle_survey.entity.Place;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;


public class DriveDao {
    public static int begin(DriveLog driveLog) {
        String sql = "insert into test_drivelog (driveId,assignId,driveLicense,time,surveyNo,placeX,placeY) values (?,?,?,?,?,?,?)";
        Object[] params = {
                driveLog.getAssignId(),
                driveLog.getAssignId(),
                driveLog.getDriveLicense(),
                new Timestamp(driveLog.getTime()),
                driveLog.getSurveyNo(),
                driveLog.getPlace().getLatitude(),
                driveLog.getPlace().getLongitude(),
        };
        return BaseDao.executeIUD(sql, params);
    }


    public static ArrayList<DriveLog> getAllList(ResultSet rs) {
        ArrayList<DriveLog> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Object obj = null;
                int driveId = rs.getInt("driveId");
                String driveLicense = rs.getString("driveLicense");
                Long driveBegin = rs.getTimestamp("driveBegin").getTime();
                Long driveEnd = rs.getTimestamp("driveEnd").getTime();
                String surveyName  = rs.getString("surveyName");
                String point = rs.getString("surveyPoint");


                DriveLog driveLog = new DriveLog(driveId,driveLicense,driveBegin,driveEnd,surveyName,point);
                list.add(driveLog);
            }
            if (list.size() > 0) return list;
            else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<DriveLog> getAll_all(String no) {
        ArrayList<DriveLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select dri.*,max(time) as driveEnd,min(time) as driveBegin,ass.assignPoint as surveyPoint,emp.name as surveyName from test_drivelog dri left join test_assignlog ass on dri.assignId = ass.assignId left  join test_employee emp on emp.no = dri.surveyNo where dri.surveyNo = ? group by dri.driveId order by dri.driveId desc";
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
        return null;
    }

    /*
    public static ArrayList<DriveLog> getAll_time(String no, long begin, long end) {
        ArrayList<DriveLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select dri.*,ass.assignPoint from test_drivelog dri left join test_assignlog ass on dri.assignId = ass.assignId where dri.surveyNo = ? && (dri.driveEnd > ? && dri.driveBegin < ?) order by dri.driveId desc";
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
        return null;
    }
     */


    public static ArrayList<DriveLog> getAll_license(String no, String license) {
        ArrayList<DriveLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select dri.*,max(time) as driveEnd,min(time) as driveBegin,ass.assignPoint as surveyPoint,emp.name as surveyName from test_drivelog dri left join test_assignlog ass on dri.assignId = ass.assignId left  join test_employee emp on emp.no = dri.surveyNo where dri.surveyNo = ? && dri.driveLicense regexp ? group by dri.driveId order by dri.driveId desc";
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
        return null;
    }


    public static ArrayList<DriveLog> getDetail(int id) {
        ArrayList<DriveLog> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ArrayList<Place> route = new ArrayList<>();

            String sql = "select dri.*,ass.assignPoint as surveyPoint,empSurvey.name as surveyName from test_drivelog dri left join test_assignlog ass on dri.assignId = ass.assignId left join test_employee empSurvey on empSurvey.no = dri.surveyNo where dri.driveId = ? order by time desc";
            ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                BigDecimal placeX = rs.getBigDecimal("placeX");
                BigDecimal placeY = rs.getBigDecimal("placeY");
                route.add(new Place(placeX, placeY));
            }
            rs.first();
            Long driveBegin = rs.getTimestamp("time").getTime();
            rs.last();
            Long driveEnd = rs.getTimestamp("time").getTime();
            Integer driveId = rs.getInt("driveId");
            Integer assignId = rs.getInt("assignId");
            String driveLicense = rs.getString("driveLicense");
            String surveyNo = rs.getString("surveyNo");
            String surveyName = rs.getString("surveyName");
            String surveyPoint = rs.getString("surveyPoint");

            list.add(new DriveLog(driveId, assignId, driveLicense, driveBegin, driveEnd, surveyNo, surveyName, surveyPoint, route));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

}
