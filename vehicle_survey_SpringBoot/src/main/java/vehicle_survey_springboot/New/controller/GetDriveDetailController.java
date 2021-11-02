package vehicle_survey_springboot.New.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vehicle_survey_springboot.New.Dao.DriveDao;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@ResponseBody
public class GetDriveDetailController {

    @RequestMapping("/api/get/getDetail/drive")
    public HashMap getDetail(@RequestParam("driveId") int id){
        int code;
        String msg;
        Object data;
        ArrayList list = DriveDao.getDetail(id);
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
