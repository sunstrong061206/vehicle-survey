package vehicle_survey_springboot.New.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.service.GetService;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@ResponseBody
public class GetLicenseController {
    @Autowired
    GetService getService;

    @RequestMapping("/api/get/getAll/drive")
    public HashMap getLicense(String no,String license,String filter){
        HashMap json = new HashMap();
        int code;
        String msg;
        Object data;
        ArrayList list =null;
        if("all".equals(filter)) {
            list = getService.getAll_all(no);
        } else if("license".equals(filter)) {
            list = getService.getAll_license(no,license);
        }
        if(list!=null){
            if(list.size()>0){
                code = 200;
                msg = "数据不为空，成功";
            }
            else{
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


}
