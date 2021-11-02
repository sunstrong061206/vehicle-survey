package vehicle_survey_springboot.New.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.Dao.LendDao;
import vehicle_survey_springboot.New.entity.LendLog;
import vehicle_survey_springboot.New.service.GetService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class LendController{
    @Autowired
    GetService getService;

    @RequestMapping("/api/get/getAll/lend")
    public HashMap Lend(@RequestParam("no")String no, @RequestParam("filter")String filter, String name, String license, Timestamp lendBegin, Timestamp lendEnd, Integer lendResult, Integer returnResult, Boolean isVet, Timestamp returnBegin,Timestamp returnEnd){
        int code;
        String msg;
        Object data;
        ArrayList list = null;
        HashMap json = new HashMap();
        if("all".equals(filter)) {
            list = getService.getAllList_all(no);
        } else if("name".equals(filter)) {
            list = getService.getAllList_name(no,name);
        }
        else if("license".equals(filter)) {
            list = getService.getAllList_license(no,license);
        }
        else if("lendTime".equals(filter)) {
            list = getService.getAllList_lendTime(no,lendBegin,lendEnd);
        }
        else if("returnTime".equals(filter)) {
            list = getService.getAllList_returnTime(no,returnBegin,returnEnd);
        }
        else if("lendResult".equals(filter)){
            list = getService.getAllList_lendResult(no,lendResult);
        }
        else if("returnResult".equals(filter)) {
            list = getService.getAllList_returnResult(no,returnResult);
        }else if("isVet".equals(filter)){
            list = getService.getAllList_isVet(no,isVet);
        }

        if(list.size()>0){
            code = 200;
            msg = "数据不为空，成功";
        }else{
            code = 200;
            msg = "数据为空";
        }
        data = list;
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);
        return json;
    }

    @RequestMapping("/api/get/getDetail/lend")
    public HashMap LendDetail(@RequestParam("lendId")int lendId){
        int code;
        String msg;
        Object data;
        ArrayList list = LendDao.getDetail(lendId);

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

    @RequestMapping("/api/update/vet/lend")
    public HashMap LendUpdate(@RequestParam("lendId")Integer lendId,@RequestParam("no")String no,@RequestParam("time")Timestamp time,@RequestParam("result")Integer result,@RequestParam("resultMsg")String resultMsg){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();
        int count = LendDao.vetLend(new LendLog(lendId,no,time,result,resultMsg));
        if(count >0){
            code = 200;
            msg  = "审批成功";
            map.put("lendId",lendId);
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

    @RequestMapping("/api/update/vet/return")
    public HashMap LendReturn(@RequestParam("lendId")Integer lendId,@RequestParam("no")String no,@RequestParam("time")Timestamp time,@RequestParam("result")Integer result,@RequestParam("resultMsg")String resultMsg){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();
        int count = LendDao.vetReturn(new LendLog(lendId,no,time,result,resultMsg));
        if(count >0){
            code = 200;
            msg  = "审批成功";
            map.put("lendId",lendId);
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

    @RequestMapping("/api/delete/revoke/lend")
    public HashMap LendRevoke(@RequestParam("lendId")Integer lendId,@RequestParam("revokeTime")Timestamp revokeTime){
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();
        HashMap json = new HashMap();
        int count = LendDao.revoke(lendId,revokeTime);
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
