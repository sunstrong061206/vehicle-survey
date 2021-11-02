package vehicle_survey_springboot.New.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.Dao.ProtectDao;
import vehicle_survey_springboot.New.entity.ProtectLog;
import vehicle_survey_springboot.New.service.GetService;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class ProtectController {
    @Autowired
    GetService getService;

    @RequestMapping("/api/get/getAll/protect")
    public HashMap Protect(@RequestParam("filter") String filter, @RequestParam("no") String no, Timestamp begin, Timestamp end, String license, Integer result, Boolean isVet) {
        HashMap json = new HashMap();
        int code;
        String msg;
        Object data;
        ArrayList list = null;
        if ("all".equals(filter)) {
            list = getService.getAll_allProtectLog(no);
        } else if ("time".equals(filter)) {
            list = getService.getAll_time(no, begin, end);
        } else if ("license".equals(filter)) {
            list = getService.getAll_licenseProtectLog(no, license);
        } else if ("result".equals(filter)) {
            list = getService.getAll_result(no, result);
        } else if ("isVet".equals(filter)) {
            list = getService.getAll_isVet(no, isVet);
        }
        if(list != null){
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

    @RequestMapping("/api/get/getDetail/protect")
    public HashMap ProtectGetDetail(@RequestParam("protectId")Integer protectId){
        int code;
        String msg;
        Object data;
        ArrayList list = ProtectDao.getDetail(protectId);
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

    @RequestMapping("/api/delete/revoke/protect")
    public HashMap ProtectRevoke(@RequestParam("protectId")Integer protectId,@RequestParam("revokeTime")Timestamp revokeTime){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();

        int count = ProtectDao.revoke(protectId,revokeTime);
        if(count > 0){
            code = 200;
            msg ="撤销成功";
        }else if(count == -1){
            code = 333;
            msg = "当前状态无法撤销";
        }
        else{
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        data = map;
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);

        return json;
    }

    @RequestMapping("/api/add/report/protect")
    public HashMap ProtectAdd(@RequestParam("protectLicense")String protectLicense, @RequestParam("protectMsg")String protectMsg, @RequestParam("protectTime")Long protectTime1,@RequestParam("manageNo")String manageNo,@RequestParam("manageTime")Long manageTime1,@RequestParam("vetNo")String vetNo){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();
        Timestamp protectTime = new Timestamp(protectTime1);
        Timestamp manageTime = new Timestamp(manageTime1);

        int count = ProtectDao.add(new ProtectLog(protectLicense,protectTime,protectMsg,manageNo,manageTime,vetNo));
        if(count > 0){
            code = 200;
            msg = "保养申报成功";
        }else {
            // 异常导致失败
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        data = map;
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);

        return json;
    }
}














