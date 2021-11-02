package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.entity.Employee;

import java.sql.*;

public class InvestigationDao {

    public static int addInvestigation(Timestamp investTime,String license,String reason,String licenseImg,String svNo) {
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        //String sql = "select * from test_employee where no = ? && name = ? && position = ? && dept = ?";
        String sql = "insert into test_investigation (investTime,license,reason,licenseImg,svNo) values (?,?,?,?,?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, investTime);
            ps.setString(2,license);
            ps.setString(3,reason);
            ps.setString(4,licenseImg);
            ps.setString(5,svNo);
            count = ps.executeUpdate();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return 0;
    }
}
