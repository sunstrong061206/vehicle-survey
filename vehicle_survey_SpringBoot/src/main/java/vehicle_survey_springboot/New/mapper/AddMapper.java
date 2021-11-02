package vehicle_survey_springboot.New.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import vehicle_survey_springboot.New.entity.AssignLog;

import java.sql.Timestamp;

@Mapper
public interface AddMapper {
    @Insert("insert into test_assignlog (assignLicense,assignTime,assignPoint,assignMsg,surveyNo,manageNo," +
            "manageTime,vetNo) values (#{assignLicense},#{assignTime},#{assignPoint},#{assignMsg},#{surveyNo}," +
            "#{manageNo},#{manageTime},#{vetNo})")
    public int add(@Param("assignLicense")String assignLicense, @Param("assignTime")Timestamp assignTime,
                   @Param("assignPoint")String assignPoint, @Param("assignMsg")String assignMsg, @Param("surveyNo")String surveyNo,
                   @Param("manageNo")String manageNo, @Param("manageTime")Timestamp manageTime,@Param("vetNo")String vetNo);
}
