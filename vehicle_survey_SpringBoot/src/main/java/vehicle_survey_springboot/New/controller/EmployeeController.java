package vehicle_survey_springboot.New.controller;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vehicle_survey_springboot.New.Dao.EmployeeDao;
import vehicle_survey_springboot.New.entity.Employee;
import vehicle_survey_springboot.New.service.EmployeeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/api/login")
    public HashMap login(@RequestParam("no")String no, @RequestParam("password")String password){
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        HashMap json = new HashMap();
        map = employeeService.login(no,password);
        int code;
        String msg;
        Object data;
        if(map != null){
            if("登录成功".equals(map.get("msg"))){
                code = 200;
                msg = "登录成功";
                map2.put("employee",map.get("employee"));
            }else{
                code = 303;
                msg = "工号或密码错误";
            }
        }else{
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        data = map2;
        json.put("code",code);
        json.put("msg",msg);
        json.put("data",data);
        return json;
    }

    @RequestMapping("/api/update/password")
    public HashMap updatePwd(@RequestParam("no")String no,@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword){
        int count = employeeService.updatePwd(no,oldPassword,newPassword);
        int code;
        String msg;
        Object data;
        HashMap map = new HashMap();
        HashMap json = new HashMap();
        if(count == -1) {
            code = 303;
            msg = "密码错误，无法修改";
        }else if(count == 1){
            code = 200;
            msg="修改成功，请重新登录";
        }else{
            code = 999;
            msg="网络异常，请稍后再试";
        }
        data = map;
        json.put("code",code);
        json.put("msg",msg);
        json.put("data",data);
        return json;
    }

    @RequestMapping("/api/get/employee")
    public HashMap getEmployee(@RequestParam("filter")String filter,String position,String no){
        int code;
        String msg;
        Object data;
        ArrayList list = null;
        HashMap json = new HashMap();
        if("position".equals(filter)){
            list = employeeService.getEmployee_position(position);
        }else if("no".equals(filter)){
            list = employeeService.getEmployee_no(no);
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

    @RequestMapping("/api/update/verify")
    public HashMap EmployeeVerify(@RequestParam("no")String no,@RequestParam("name")String name,@RequestParam("position")String position,@RequestParam("wechat")String wechat,@RequestParam("password")String pwd){
        int code;
        String msg;
        Object data;
        Map<String, Object> map = new HashMap<>();
        HashMap json = new HashMap();
        int count = EmployeeDao.verify(new Employee(no,name,position,wechat,pwd));

        if (count == 1) {
            code = 200;
            msg = "认证成功";
            map.put("no",no);
        } else if (count == -1) {
            code = 303;
            msg = "查无此人，请确认填写无误";
        } else if (count == -2) {
            code = 333;
            msg = "此账号已认证，不可重复认证，请选择密码登录";
        } else if (count == -3) {
            code = 333;
            msg = "人脸信息不匹配，请重新认证";
        } else {
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
