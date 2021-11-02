package vehicle_survey_springboot.New.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.Dao.VehicleDao;
import vehicle_survey_springboot.New.service.GetService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class VehicleInfoController {
    @Autowired
    GetService getService;
    
    @RequestMapping("/api/get/getAll/vehicleInfo")
    public HashMap VehicleInfo(@RequestParam("filter")String filter, @RequestParam("position")String position, @RequestParam("no")String no, String manageName, Integer status, Integer peopleNumMin, Integer peopleNumMax, Timestamp lastProtectTimeBegin,Timestamp lastProtectTimeEnd,String license){
        int code;
        String msg;
        Object data;
        ArrayList list = null;
        HashMap json = new HashMap();

        if ("all".equals(filter)) {
            list = getService.getAll_allVehicle(no, position);
        } else if ("manageName".equals(filter)) {
            if ("vet".equals(position)) {
                list = getService.getAll_name(no, manageName);
            }
        } else if ("status".equals(filter)) {
            list = getService.getAll_status(no, position, status);
        } else if ("peopleNum".equals(filter)) {
            list = getService.getAll_peopleNum(no, position, peopleNumMin, peopleNumMax);
        } else if ("lastProtectTime".equals(filter)) {
            list = getService.getAll_lastProtectTime(no, position, lastProtectTimeBegin, lastProtectTimeEnd);
        }else if("license".equals(filter)) list = VehicleDao.getAll_license(no,position,license);

        if ("manageName".equals(filter) && ("manage".equals(position))) {
            code = 303;
            msg = "管理员只能查看自己管理的车辆";
        } else if (list.size() > 0) {
            code = 200;
            msg = "数据不为空，成功";
        } else if (list.size() == 0) {
            code = 200;
            msg = "数据为空";
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

    @RequestMapping("/api/get/getDetail/vehicleInfo")
    public HashMap VehicleDetail(@RequestParam("license")String license){
        HashMap json = new HashMap();
        int code;
        String msg;
        Object data;
        ArrayList list = VehicleDao.getDetatil(license);


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

    @RequestMapping("/api/delete/banVehicle")
    public HashMap VehicleDelete(@RequestParam("license")String license,@RequestParam("endMsg")String endMsg,@RequestParam("manageNo")String manageNo){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();
        int count = VehicleDao.delete(license, endMsg,manageNo);

        if (count > 0) {
            // 车辆删除成功
            code = 200;
            msg = "车辆删除成功";
        }
        // 车辆不存在，无法删除
        else if(count == -1) {
            code = 303;
            msg = "车辆不存在，无法删除";
        }
        else {
            // 车辆删除失败，网络异常
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
