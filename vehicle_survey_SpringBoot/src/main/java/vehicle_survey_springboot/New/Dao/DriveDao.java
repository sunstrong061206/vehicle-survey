package vehicle_survey_springboot.New.Dao;

import vehicle_survey_springboot.New.entity.DriveLog;
import vehicle_survey_springboot.New.entity.Place;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class DriveDao {

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
            Long driveBegin1 = rs.getTimestamp("time").getTime();
            Timestamp driveBegin = new Timestamp(driveBegin1);
            rs.last();
            Long driveEnd1 = rs.getTimestamp("time").getTime();
            Timestamp driveEnd = new Timestamp(driveBegin1);
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

    public static int begin(DriveLog driveLog) {
        String sql = "insert into test_drivelog (driveId,assignId,driveLicense,time,surveyNo,placeX,placeY) values (?,?,?,?,?,?,?)";
        Object[] params = {
                driveLog.getAssignId(),
                driveLog.getAssignId(),
                driveLog.getDriveLicense(),
                driveLog.getTime(),
                driveLog.getSurveyNo(),
                driveLog.getPlace().getLatitude(),
                driveLog.getPlace().getLongitude(),
        };
        return BaseDao.executeIUD(sql, params);
    }

}
