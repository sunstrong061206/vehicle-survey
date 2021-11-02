package vehicle_survey_springboot.New.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.Dao.BaseDao;

import java.util.HashMap;

@Controller
@ResponseBody
public class VetCount {

    // 第一个数是已审批，第二个数是未审批
    @RequestMapping("/api/get/count")
    public HashMap vetCount(@RequestParam("no")String no,@RequestParam("position") String position){



        int code;
        String msg;
        Object data;
        HashMap json = new HashMap();
        HashMap map = new HashMap();
        int[] arr = BaseDao.count(no,position);
        if(arr!=null){
            code = 200;
            msg = "数据不为空，成功";
            map.put("vet",arr[1]);
            map.put("notVet",arr[0]);
        }else{
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
