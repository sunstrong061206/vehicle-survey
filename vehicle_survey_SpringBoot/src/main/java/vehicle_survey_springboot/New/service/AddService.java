package vehicle_survey_springboot.New.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vehicle_survey_springboot.New.entity.AssignLog;
import vehicle_survey_springboot.New.mapper.AddMapper;

import java.sql.Timestamp;

@Service
public class AddService {
    @Autowired
    AddMapper addMapper;

    public int add(String assignLicense, Timestamp assignTime,
                   String assignPoint,String assignMsg, String surveyNo,
                   String manageNo,Timestamp manageTime,String vetNo){
        return addMapper.add(assignLicense,assignTime,assignPoint,assignMsg,surveyNo,manageNo,manageTime,vetNo);
    }

    //(@Param("assignLicense")String assignLicense, @Param("assignTime")Timestamp assignTime,
    //                   @Param("assignPoint")String assignPoint, @Param("assignMsg")String assignMsg, @Param("surveyNo")String survey,
    //                   @Param("manageNo")String manageNo, @Param("manageTime")Timestamp manageTime,@Param("vetNo")String vetNo);

}
