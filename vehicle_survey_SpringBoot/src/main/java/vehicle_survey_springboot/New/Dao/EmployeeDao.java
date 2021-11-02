package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDao {
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
                } else {
                    count = -2;
                }
            } else {
                count = -1;
            }
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


    public int update_status(String no ,int status){
        String sql  ="update test_employee set status = ? where no = ?";
        Object[] params = {
                status,
                no
        };
        return BaseDao.executeIUD(sql,params);
    }

}
