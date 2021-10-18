package com.vehicle_survey.dao;


import com.vehicle_survey.controller.add.PickVehicle;
import com.vehicle_survey.entity.Vehicle;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class VehicleDao {

    public static ArrayList<Vehicle> getAllList(ResultSet rs) {
        ArrayList<Vehicle> list = new ArrayList<>();
        try {
            while (rs.next()) {
                String license = rs.getString("license");
                int status = rs.getInt("status");
                String manageName = rs.getString("manageName");
                int peopleNum = rs.getInt("peopleNum");
                String emission = rs.getString("emission");
                Long lastProtectTime = rs.getTimestamp("lastProtectTime").getTime();

                list.add(new Vehicle(license, status, peopleNum, emission, manageName, lastProtectTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int add(Vehicle vehicle) {
        String sql = "insert into test_vehicle (license,status,begin,type,peopleNum,emission,manageNo,img,price,lastProtectTime) values (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                vehicle.getLicense(),
                vehicle.getStatus(),
                new Timestamp(vehicle.getBegin()),
                vehicle.getType(),
                vehicle.getPeopleNum(),
                vehicle.getEmission(),
                vehicle.getManageNo(),
                vehicle.getImgPath(),
                vehicle.getPrice(),
                new Timestamp(vehicle.getLastProtectTime())
        };
        return BaseDao.executeIUD(sql, params);
    }

    // 弃用车辆，-1表示此车不存在，
    public static int delete(String license, String endMsg, String manageNo) {
        // 如果不存在此车，无法删除
        String sql = "update test_vehicle set end = ?,status = -1,endMsg = ? where license = ?";
        Object[] params = {
                new Timestamp(System.currentTimeMillis()),
                endMsg,
                license
        };
        return BaseDao.executeIUD(sql, params);
    }

    // 更换车辆照片
    public static int updateImg(String license, String imgPath) {
        String sql = "update test_vehicle set img = ? where license = ?";
        if (isExist(license)) {
            Object[] params = {
                    imgPath,
                    license
            };
            return BaseDao.executeIUD(sql, params);
        }
        // 错误返回-1
        return -1;
    }

    public static boolean isExist(String license) {
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // 车辆现役才算存在
        String sql = "select * from test_vehicle where license = ? && status != -1";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, license);
            rs = ps.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return false;
    }


    public static ArrayList<Vehicle> getProtectVehicle(String manageNo, long currentTime) {
        final long INTERVAL = 1000 * 60 * 60 * 24 * 30 * 6; // 设置保养时间间隔为6个月
        ArrayList<Vehicle> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 该管理员，未废弃，需保养
        String sql = "select * from test_vehicle where manageNo = ? && status!= -1 && (unix_timestamp(lastProtectTime)+?)<? order by lastProtectTime";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, manageNo);
            ps.setLong(2, INTERVAL);
            ps.setLong(3, currentTime);
            rs = ps.executeQuery();
            while (rs.next()) {
                String license = rs.getString("license");
                long lastProtectTime = rs.getTimestamp("lastProtectTime").getTime();
                Vehicle vehicle = new Vehicle(license, lastProtectTime);
                list.add(vehicle);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<Vehicle> getAll_all(String no,String pos) {
        ArrayList<Vehicle> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql ="";
            if("vet".equals(pos)) {
                sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
            }
            else if("manage".equals(pos)) {
                sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && veh.manageNo = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
            }
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<Vehicle> getAll_name(String no,String name) {
        ArrayList<Vehicle> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && emp.name = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<Vehicle> getAll_status(String no,String pos,int status) {
        ArrayList<Vehicle> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if("vet".equals(pos)){
                sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && veh.status = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, status);
                rs = ps.executeQuery();
            }else if("manage".equals(pos)){
                sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && veh.status = ? && veh.manageNo = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, status);
                ps.setString(2,no);
                rs = ps.executeQuery();
            }
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<Vehicle> getAll_peopleNum(String no,String pos,int min, int max) {
        ArrayList<Vehicle> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if("vet".equals(pos)){
                sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && veh.peopleNum > ? && veh.peopleNum < ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, min);
                ps.setInt(2, max);
                rs = ps.executeQuery();
            }else if("manage".equals(pos)){
                sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && veh.peopleNum > ? && veh.peopleNum < ? && veh.manageNo = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, min);
                ps.setInt(2, max);
                ps.setString(3,no);
                rs = ps.executeQuery();
            }
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<Vehicle> getAll_lastProtectTime(String no ,String pos,long min, long max) {
        ArrayList<Vehicle> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "";
            if("vet".equals(pos)){
                sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && veh.lastProtectTime > ? && veh.lastProtectTime < ?";
                ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, new Timestamp(min));
                ps.setTimestamp(2, new Timestamp(max));
                rs = ps.executeQuery();
            }else if("manage".equals(pos)){
                sql = "select veh.*,emp.name as manageName from test_vehicle veh left join test_employee emp on emp.no = veh.manageNo where veh.end is null && veh.lastProtectTime > ? && veh.lastProtectTime < ? && veh.manageNo = ?";
                ps = conn.prepareStatement(sql);
                ps.setTimestamp(1, new Timestamp(min));
                ps.setTimestamp(2, new Timestamp(max));
                ps.setString(3,no);
                rs = ps.executeQuery();
            }
            list = getAllList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static ArrayList<Vehicle> getDetatil(String license) {
        ArrayList<Vehicle> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select veh.*,empManage.name manageName from test_vehicle veh left join test_employee empManage on empManage.no = veh.manageNo where veh.license = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, license);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object obj = null;
                Integer status = rs.getInt("status");
                Long begin = rs.getTimestamp("begin").getTime();
                Long end = null;
                if ((obj = rs.getTimestamp("end")) != null) end = ((Timestamp) obj).getTime();
                Integer type = rs.getInt("type");
                Integer peopleNum = rs.getInt("peopleNum");
                String emission = rs.getString("emission");
                String manageNo = rs.getString("manageNo");
                String manageName = rs.getString("manageName");
                String imgTitle = rs.getString("img");
                String folderPath = PickVehicle.getFolderPath();
                Double price = rs.getDouble("price");
                Long lastProtectTime = null;
                if ((obj = rs.getTimestamp("lastProtectTime")) != null) lastProtectTime = ((Timestamp) obj).getTime();
                String endMsg = rs.getString("endMsg");

                list.add(new Vehicle(license, status, begin, end, type, peopleNum, emission, manageNo, manageName, folderPath+imgTitle, price, lastProtectTime, endMsg));
            }
        } catch (SQLException  e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
    }

    public static String getManage(String license){
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String sql = "select * from test_vehicle where license = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,license);
            rs = ps.executeQuery();
            while(rs.next()){
                return rs.getString("manageNo");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            BaseDao.closeAll(conn,ps,rs);
        }
        return null;
    }

}
