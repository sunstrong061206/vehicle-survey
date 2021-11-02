package vehicle_survey_springboot.New.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.Dao.AssignDao;
import vehicle_survey_springboot.New.entity.AssignLog;
import vehicle_survey_springboot.New.service.GetService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class AssginController {
    @Autowired
    GetService getService;

    @RequestMapping("/api/get/getAll/assign")
    public HashMap AssignLog(@RequestParam("filter")String filter, @RequestParam("no")String no, @RequestParam("position")String pos, Timestamp begin,Timestamp end,String license,Integer result,Boolean isVet){
        int code;
        String msg;
        Object data;
        ArrayList list = null;
        HashMap json = new HashMap();
        
        if("all".equals(filter)) {
            list = getService.getAll_allAssignLog(no,pos);
        } else if("time".equals(filter)) {
            list = getService.getAll_timeAssignLog(no,pos,begin,end);
        }
        else if("license".equals(filter)) {
            list = getService.getAll_licenseAssignLog(no,pos,license);
        }
        else if("result".equals(filter)) {
            list = getService.getAll_resultAssignLog(no,pos,result);
        }else if("isVet".equals(filter)){
            list  = getService.getAll_isVetAssignLog(no,isVet);
        }

        if(list != null){
            if(list.size()>0){
                code = 200;
                msg = "数据不为空，成功";
            }else{
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

    @RequestMapping("/api/get/getDetail/assign")
    public HashMap AssignDetail(@RequestParam("assignId")Integer assignId){
        int code;
        String msg;
        Object data;
        ArrayList list = AssignDao.getDetail(assignId);
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

    @RequestMapping("/api/update/vet/assign")
    public HashMap AssignVet(@RequestParam("vetNo")String vetNo,@RequestParam("vetTime")Timestamp vetTime,@RequestParam("result")Integer result,@RequestParam("resultMsg")String resultMsg,@RequestParam("assignId")Integer assignId){
        HashMap json = new HashMap();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        int count  = AssignDao.vet(new AssignLog(assignId,vetNo,vetTime,result,resultMsg));
        if(count >0){
            code = 200;
            msg  = "审批成功";
            map.put("assignId",assignId);
        }else{
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        data = map;
        json.put("code",code);
        json.put("msg",msg);
        json.put("data",data);

        return json;
    }

    @RequestMapping("/api/delete/revoke/assign")
    public HashMap AssignRevoke(@RequestParam("assignId")Integer assignId,@RequestParam("revokeTime")Timestamp revokeTime){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();
        int count = AssignDao.revoke(assignId, revokeTime);
        if(count > 0){
            code = 200;
            msg ="撤销成功";
        }else if(count == -1){
            code = 333;
            msg = "通过审批后，无法撤销";
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
}
