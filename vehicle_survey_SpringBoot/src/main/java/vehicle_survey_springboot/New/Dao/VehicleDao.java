package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.PickVehicle;
import vehicle_survey_springboot.New.entity.Vehicle;

import java.sql.*;
import java.util.ArrayList;

public class VehicleDao {

    public static int add(Vehicle vehicle) {
        String sql = "insert into test_vehicle (license,status,begin,type,peopleNum,emission,manageNo,img,price,lastProtectTime) values (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                vehicle.getLicense(),
                vehicle.getStatus(),
                vehicle.getBegin(),
                vehicle.getType(),
                vehicle.getPeopleNum(),
                vehicle.getEmission(),
                vehicle.getManageNo(),
                vehicle.getImgPath(),
                vehicle.getPrice(),
                vehicle.getLastProtectTime()
        };
        return BaseDao.executeIUD(sql, params);
    }

    public static ArrayList<Vehicle> getAllList(ResultSet rs) {
        ArrayList<Vehicle> list = new ArrayList<>();
        try {
            while (rs.next()) {
                String license = rs.getString("license");
                int status = rs.getInt("status");
                String manageName = rs.getString("manageName");
                int peopleNum = rs.getInt("peopleNum");
                String emission = rs.getString("emission");
                Timestamp lastProtectTime = rs.getTimestamp("lastProtectTime");

                list.add(new Vehicle(license, status, peopleNum, emission, manageName, lastProtectTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<Vehicle> getDetatil(String license){
        ArrayList<Vehicle> list = new ArrayList<>();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Timestamp begin = null;
        Timestamp end = null;
        Timestamp lastProtectTime = null;

        try {
            String sql = "select veh.*,empManage.name manageName from test_vehicle veh left join test_employee empManage on empManage.no = veh.manageNo where veh.license = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, license);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object obj = null;
                Integer status = rs.getInt("status");
                Long begin1 = rs.getTimestamp("begin").getTime();
                Long end1 = null;
                if ((obj = rs.getTimestamp("end")) != null) {
                    end1 = ((Timestamp) obj).getTime();
                    end = new Timestamp(end1);
                }
                Integer type = rs.getInt("type");
                Integer peopleNum = rs.getInt("peopleNum");
                String emission = rs.getString("emission");
                String manageNo = rs.getString("manageNo");
                String manageName = rs.getString("manageName");
                String imgTitle = rs.getString("img");
                String folderPath = PickVehicle.getFolderPath();
                Double price = rs.getDouble("price");
                Long lastProtectTime1 = null;
                if ((obj = rs.getTimestamp("lastProtectTime")) != null) {
                    lastProtectTime1 = ((Timestamp) obj).getTime();
                    lastProtectTime = new Timestamp(lastProtectTime1);
                }
                String endMsg = rs.getString("endMsg");

                list.add(new Vehicle(license, status, begin, end, type, peopleNum, emission, manageNo, manageName, folderPath+imgTitle, price, lastProtectTime, endMsg));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return list;
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
            if (rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return false;
    }

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

    public static int update_status(String license,int status){
        String sql = "update test_vehicle set status = ? where license = ?";
        Object[] params = {
                status,
                license
        };
        return BaseDao.executeIUD(sql, params);
    }


    public static ArrayList<Vehicle> getAll_license(String no,String pos,String license){
        ArrayList<Vehicle> list = new ArrayList<>();

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            if("manage".equals(pos)){
                String sql = "select veh.*,emp.name as manageName from test_vehicle veh  left join test_employee emp on emp.no = veh.manageNo where license regexp ? && manageNo = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,license);
                ps.setString(2,no);
                rs = ps.executeQuery();
                list = getAllList(rs);
            }else{
                String sql = "select veh.*,emp.name as manageName from test_vehicle veh  left join test_employee emp on emp.no = veh.manageNo where license regexp ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,license);
                rs = ps.executeQuery();
                list = getAllList(rs);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return  list;
    }
}
