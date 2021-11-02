package vehicle_survey_springboot.New.Dao;

import java.sql.*;

public class FaceDao {
//    public static int insertImage(String svId,String image){
//        Connection conn = BaseDao.getConn();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        int count = 0;
//
//        //String sql = "select * from test_employee where no = ? && name = ? && position = ? && dept = ?";
//        String sql = "insert into test_face (svId,image) values (?,?)";
//
//        try {
//            ps = conn.prepareStatement(sql);
//
//            ps.setString(1,svId);
//            ps.setString(2,image);
//
//            count = ps.executeUpdate();
//            return count;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeAll(conn, ps, rs);
//        }
//        return 0;
//    }

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

