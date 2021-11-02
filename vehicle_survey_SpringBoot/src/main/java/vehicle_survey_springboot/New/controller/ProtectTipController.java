package vehicle_survey_springboot.New.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.service.GetService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@ResponseBody
public class ProtectTipController {
    @Autowired
    GetService getService;

    @RequestMapping("/api/get/getProtectTip")
    public HashMap ProtectTip(@RequestParam("no")String no){
        long time = System.currentTimeMillis();
        Timestamp realtime = new Timestamp(time);

        HashMap json = new HashMap();
        int code;
        String msg;
        Object data;
        ArrayList list = null;

        list = getService.getProtectVehicle(no,realtime);
        if(list!=null){
            if(list.size()>0){
                code = 200;
                msg = "数据不为空，返回成功";
            }else{
                code = 200;
                msg="数据为空";
            }
        }else{
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        data = list;
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);

        return json;
    }
}
