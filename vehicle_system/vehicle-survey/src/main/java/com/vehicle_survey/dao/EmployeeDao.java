package com.vehicle_survey.dao;


import com.vehicle_survey.entity.Employee;
import com.vehicle_survey.utils.TokenUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDao {
    /**
     * 信息认证，暂不考虑人脸识别
     * 1表示认证成功，-1表示查无此人，-2表示已经认证，0表示异常
     */
    public static int verify(Employee employee) {
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        //String sql = "select * from test_employee where no = ? && name = ? && position = ? && dept = ?";
        String sql = "select * from test_employee where no = ? && name = ? ";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getNo());
            ps.setString(2, employee.getName());
//            ps.setString(3, employee.getPos());
//            ps.setString(4, employee.getDept());
            rs = ps.executeQuery();
            if (rs.next()) {
                String wechat = rs.getString("wechat");
                if (wechat == null) {
                    Object[] params = {employee.getWechat(), employee.getNo()};
                    count = BaseDao.executeIUD("update test_employee set wechat = ? where no = ?", params);
                } else count = -2;
            } else count = -1;
            if (count > 0) {
                Object[] params = {
                        employee.getPwd(),
                        employee.getNo()
                };
                BaseDao.executeIUD("update test_employee set password = ? where no = ?", params);
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return 0;
    }

    /*
    public static HashMap login(String no, String pwd) {
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        HashMap<String, Object> map = new HashMap();

        try {
            String token;
            String sql = "select * from test_employee where no = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);

            rs = ps.executeQuery();
            String name = null, phone = null, dept = null, position = null, bossNo = null;
            if (rs.next()) {
                name = rs.getString("name");
                phone = rs.getString("phone");
                dept = rs.getString("dept");
                position = rs.getString("position");
                bossNo = rs.getString("bossNo");
                String password = rs.getString("password");
                if (password == null) token = "NoPwd";
                else if (pwd.equals(password)) token = TokenUtils.createToken(no);
                else token = "fail";
            } else token = "NoBody";
            map.put("token", token);
            map.put("employee", new Employee(no, name, phone, position, dept, bossNo));
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
     */


    public static HashMap login(String no, String pwd) {
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        HashMap<String, Object> map = new HashMap();

        try {
            String sql = "select * from test_employee where no = ? && password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            ps.setString(2, pwd);

            rs = ps.executeQuery();
            String msg;
            String name = null, phone = null, dept = null, position = null, bossNo = null;
            if (rs.next()) {
                name = rs.getString("name");
                phone = rs.getString("phone");
                dept = rs.getString("dept");
                position = rs.getString("position");
                bossNo = rs.getString("bossNo");
                msg = "登录成功";
            } else msg = "工号或密码错误";
            map.put("msg", msg);
            map.put("employee", new Employee(no, name, phone, position, dept, bossNo));
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int updatePwd(String no, String oldPwd, String newPwd) {
        String msg = (String) login(no, oldPwd).get("msg");
        String sql;
        if ("工号或密码错误".equals(msg)) return -1;
        else if ("登录成功".equals(msg)) {
            sql = "update test_employee set password = ? where no = ?";
            Object[] params = {
                    newPwd,
                    no
            };
            return BaseDao.executeIUD(sql, params);
        }
        return 0;
    }

    public static ArrayList<Employee> getPersonInfo(ResultSet rs) {
        ArrayList<Employee> list = new ArrayList<>();
        try {
            while (rs.next()) {
                String no = rs.getString("no");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String phone = rs.getString("phone");
                String pos = rs.getString("position");
                String dept = rs.getString("dept");

                Employee emp = new Employee(no, name, phone, pos, dept, age);
                list.add(emp);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 查询某员工所有信息
    public static ArrayList<Employee> getEmployee_no(String no) {
        ArrayList list = new ArrayList();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from test_employee where no = ? && status = 1";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            rs = ps.executeQuery();
            list = getPersonInfo(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    public static ArrayList<Employee> getEmployee_position(String pos) {
        ArrayList list;
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from test_employee where position = ? && status = 1";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pos);
            rs = ps.executeQuery();
            list = getPersonInfo(rs);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }

    //   public static ArrayList<Employee> getBoss(String no) {
    public static Employee getBoss(String no) {
        ArrayList<Employee> list = new ArrayList();
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from test_employee where no = (select bossNo from test_employee where no = ?)";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, no);
            rs = ps.executeQuery();
            list = getPersonInfo(rs);
            //return list;
            if (list.size() > 0) return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return null;
    }


}
