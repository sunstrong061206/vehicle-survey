package vehicle_survey_springboot.New.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.Dao.PunishDao;
import vehicle_survey_springboot.New.service.GetService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@ResponseBody
public class PunishController {
    @Autowired
    GetService getService;

    @RequestMapping("/api/get/getAll/punish")
    public HashMap Punish(@RequestParam("no")String no, @RequestParam("filter")String filter, Timestamp begin,Timestamp end,String license,Integer payStatus){
        int code;
        String msg;
        Object data;
        ArrayList list = null;
        HashMap json = new HashMap();
        if ("all".equals(filter)) {
            list = getService.getAll_allPunishLog(no);
        } else if ("time".equals(filter)){
            list = getService.getAll_timePunishLog(no, begin, end);
        }
        else if ("license".equals(filter)) {
            list = getService.getAll_licensePunishLog(no, license);
        }
        else if("payStatus".equals(filter)){
            list = getService.getAll_payStatus(no,payStatus);
        }
        if (list != null) {
            if(list.size()>0){
                code = 200;
                msg = "数据不为空，成功";
            }else {
                code = 200;
                msg = "数据为空";
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

    @RequestMapping("/api/get/getDetail/punish")
    public HashMap PunishDetail(@RequestParam("punishId")Integer punishId){
        int code;
        String msg;
        Object data;
        ArrayList list = PunishDao.getDetail(punishId);
        HashMap json = new HashMap();

        if (list != null) {
            if (list.size() > 0) {
                code = 200;
                msg = "数据不为空，成功";
            } else {
                code = 200;
                msg = "数据为空";
            }
        } else {
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
