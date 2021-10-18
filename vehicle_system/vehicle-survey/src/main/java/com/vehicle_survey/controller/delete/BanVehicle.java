package com.vehicle_survey.controller.delete;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.VehicleDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "BanVehicle", value = "/api/delete/banVehicle")
public class BanVehicle extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        String license = request.getParameter("license");
        String endMsg = request.getParameter("endMsg");
        String manageNo = request.getParameter("manageNo");


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
        response.getWriter().write(json.toString());
        return;
    }
}
