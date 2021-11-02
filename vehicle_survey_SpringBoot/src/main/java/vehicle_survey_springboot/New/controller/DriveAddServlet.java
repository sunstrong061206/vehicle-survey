package vehicle_survey_springboot.New.controller;


import com.alibaba.fastjson.JSONObject;
import vehicle_survey_springboot.New.Dao.DriveDao;
import vehicle_survey_springboot.New.entity.DriveLog;
import vehicle_survey_springboot.New.entity.Place;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;

@WebServlet(name = "Drive", value = "/api/add/drive")
public class DriveAddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Integer assignId = Integer.valueOf(request.getParameter("assignId"));
        String no = request.getParameter("no");
        String license = request.getParameter("license");
        Long time1 = Long.valueOf(request.getParameter("time"));
        String x = request.getParameter("latitude");
        String y = request.getParameter("longtitude");
        Timestamp time = new Timestamp(time1);


//        String plaStr  = request.getParameter("place");
//        JSONArray jsonArray = JSONArray.parseArray(plaStr);
//        JSONObject jsonObject = jsonArray.getJSONObject(0);
//        System.out.println(jsonObject);

        //Place place = new Place(jsonObject.getBigDecimal("latitude"),jsonObject.getBigDecimal("longitude"));
        Place place = new Place(new BigDecimal(x), new BigDecimal(y));

        int count = 0;
        count = DriveDao.begin(new DriveLog(assignId, license, time, no, place));

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        HashMap<Object, Object> map = new HashMap<>();

        if (count > 0) {
            code = 200;
            msg = "成功";
        } else {
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        data = map;
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);
        response.getWriter().write(json.toString());
        return;


    }
}
