package vehicle_survey_springboot.New.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.entity.Employee;
import vehicle_survey_springboot.New.service.GetService;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class BossController {
    @Autowired
    GetService getService;

    @RequestMapping("/api/get/boss")
    public HashMap getBoss(@RequestParam("no")String no){
        int code;
        String msg;
        Object data;
        Map map = new HashMap();
        HashMap json = new HashMap();

        Employee boss =  getService.getBoss(no);
        if(boss!=null){
            code = 200;
            msg = "数据不为空";
            map.put("Boss",boss);
        }
        else{
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        data = map;
        json.put("code",code);
        json.put("msg",msg);
        json.put("data",data);
        return json;
    }
}
