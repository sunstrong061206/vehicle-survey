package vehicle_survey_springboot.New.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.Dao.RepairDao;
import vehicle_survey_springboot.New.entity.RepairLog;
import vehicle_survey_springboot.New.service.GetService;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class RepairConciseController {
    @Autowired
    GetService getService;

    @RequestMapping("/api/get/getAll/repair")
    public HashMap RepairConcise(@RequestParam("filter")String filter, @RequestParam("no")String no, @RequestParam("position")String position, Timestamp begin,Timestamp end,String license,Integer result,Boolean isVet){
        int code;
        String msg;
        Object data;
        ArrayList list = null;
        HashMap json = new HashMap();
        
        if ("all".equals(filter)) {
            list = getService.getAll_allRepairConcise(no, position);
        } else if ("time".equals(filter)) {
            list = getService.getAll_timeRepairConcise(no, position, begin, end);
        }
        else if ("license".equals(filter)) {
            list = getService.getAll_licenseRepairConcise(no, position, license);
        }
        else if ("result".equals(filter)) {
            list = getService.getAll_resultRepairConcise(no, position, result);
        }else if("isVet".equals(filter)){
            list = getService.getAll_isVetRepairConcise(no,position,isVet);
        }

        if(list != null){
            if(list.size()>0){
                code = 200;
                msg = "数据不为空，成功";
            }else{
                code = 200;
                msg = "数据为空";
            }
        }else {
            code = 999;
            msg ="网络异常，请稍后再试";
        }

        data = list;
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);

        return json;
    }

    @RequestMapping("/api/get/getDetail/repair")
    public HashMap RepairDetail(@RequestParam("repairId")Integer repairId){
        int code;
        String msg;
        Object data;
        ArrayList list = RepairDao.getDetail(repairId);
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

    @RequestMapping("/api/update/vet/repair")
    public HashMap RepairDetail(@RequestParam("repairId")Integer repairId,@RequestParam("position")String position,@RequestParam("no")String no,@RequestParam("time")Long time,@RequestParam("result")Integer result,@RequestParam("resultMsg")String resultMsg){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();

        int count = RepairDao.vet(new RepairLog(repairId,no,time,result,resultMsg),position);
        if(count >0){
            code = 200;
            msg  = "审批成功";
            map.put("repairId",repairId);
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

    @RequestMapping("/api/delete/revoke/repair")
    public HashMap RepairRevoke(@RequestParam("repairId")Integer repairId, @RequestParam("revokeTime")Timestamp revokeTime){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();

        int count = RepairDao.revoke(repairId,revokeTime);
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
}
