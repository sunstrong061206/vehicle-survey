package vehicle_survey_springboot.New.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.service.AddService;

import java.sql.Timestamp;
import java.util.HashMap;

@Controller
@ResponseBody
public class AddController {
    @Autowired
    AddService addService;

    @RequestMapping("/api/add/report/assign")
    public HashMap add(@RequestParam("assignLicense")String assignLicense, @RequestParam("assignTime")Long assignTime1,
                       @RequestParam("assignPoint")String assignPoint, @RequestParam("assignMsg")String assignMsg, @RequestParam("surveyNo")String surveyNo,
                       @RequestParam("manageNo")String manageNo, @RequestParam("manageTime") Long manageTime1, @RequestParam("vetNo")String vetNo){

        Timestamp assignTime = new Timestamp(assignTime1);
        Timestamp manageTime = new Timestamp(manageTime1);

        int count = addService.add(assignLicense,assignTime,assignPoint,assignMsg,surveyNo,manageNo,manageTime,vetNo);
        int code;
        String msg;
        Object data;
        HashMap json = new HashMap();
        HashMap map = new HashMap();
        data = map;
        if(count > 0){
            code = 200;
            msg = "派车申报成功";
        }else {
            // 异常导致失败
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);

        return json;

    }

}
