package vehicle_survey_springboot.New.Dao;

import java.sql.*;

public class FaceDao {

    public static String GetPath(String no) throws SQLException {
        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String image = null;

        String sql = "select facedata from test_employee where no = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, no);
        rs = ps.executeQuery();
        while (rs.next()){
            image = rs.getString("facedata");
        }
        return image;
    }
}

