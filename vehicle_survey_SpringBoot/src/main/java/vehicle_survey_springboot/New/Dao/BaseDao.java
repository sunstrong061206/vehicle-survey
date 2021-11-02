package vehicle_survey_springboot.New.Dao;


import java.math.BigDecimal;
import java.sql.*;
import java.util.BitSet;

public class BaseDao {
    static {
        // 加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vehicle_survey", "root", "ssz061206...");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    // 执行增删改语句，返回修改记录的数量,-10表示唯一约束异常
    // sql = insert into vehicle (license,status) values (?,?);
    public static int executeIUD(String sql, Object[] params) {
        int count = 0;

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof String) {
                    ps.setString(i + 1, (String) params[i]);
                } else if (params[i] instanceof Timestamp) {
                    ps.setTimestamp(i + 1, (Timestamp) params[i]);
                } else if (params[i] instanceof Integer) {
                    ps.setInt(i + 1, (int) params[i]);
                } else if (params[i] instanceof Double) {
                    ps.setDouble(i + 1, (double) params[i]);
                } else if (params[i] instanceof BigDecimal) {
                    ps.setBigDecimal(i + 1, (BigDecimal) params[i]);
                }
            }
            count = ps.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) { // 唯一约束的相关异常
            e.printStackTrace();
            return -10;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, null);
        }
        return count;
    }

    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != ps) {
                ps.close();
            }
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static int[] count(String no,String position) {

        int[] arr = new int[2];

        Connection conn = BaseDao.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            //未审批
            if("vet".equals(position)){
                String sql = "select count(*) as count from test_assignlog where vetNo = ? && vetTime is null && revokeTime is null ";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[0] += rs.getInt("count");

                sql = "select count(*) as count from test_lendlog where vetNo = ? && vetLendTime is null && revokeTime is null";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[0] += rs.getInt("count");

                sql = "select count(*) as count from test_lendlog where vetNo = ? && vetReturnTime is null && manageReturnTime is not null";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[0] += rs.getInt("count");

                sql = "select count(*) as count from test_protectlog where vetNo = ? && vetTime is null && revokeTime is null";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[0] += rs.getInt("count");

                sql = "select count(*) as count from test_repairlog where vetNo = ? && vetTime is null && revokeTime is null && manageTime is not null && manageResult != -1";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[0] += rs.getInt("count");



                // 已审批
                sql = "select count(*) as count from test_assignlog where vetNo = ? && vetTime is not null && revokeTime is null ";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[1] += rs.getInt("count");

                sql = "select count(*) as count from test_lendlog where vetNo = ? && vetLendTime is not null && revokeTime is null";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[1] += rs.getInt("count");

                sql = "select count(*) as count from test_lendlog where vetNo = ? && vetReturnTime is not null";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[1] += rs.getInt("count");

                sql = "select count(*) as count from test_protectlog where vetNo = ? && vetTime is not null && revokeTime is null";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[1] += rs.getInt("count");

                sql = "select count(*) as count from test_repairlog where vetNo = ? && vetTime is not null && revokeTime is null && manageTime is not null && manageResult != -1";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[1] += rs.getInt("count");
            }else{
                String sql = "select count(*) as count from test_repairlog where manageNo = ? && manageTime is null && revokeTime is null ";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[0] += rs.getInt("count");

                sql = "select count(*) as count from test_repairlog where manageNo = ? && manageTime is not null && revokeTime is null ";
                ps = conn.prepareStatement(sql);
                ps.setString(1,no);
                rs = ps.executeQuery();
                rs.next();
                arr[1] += rs.getInt("count");

            }

        } catch (SQLException e) {
            arr = null;
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, rs);
        }
        return arr;


    }


}


