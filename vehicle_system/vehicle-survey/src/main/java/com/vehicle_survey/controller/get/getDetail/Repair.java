package com.vehicle_survey.controller.get.getDetail;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.RepairDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetDetailRepair", value = "/api/get/getDetail/repair")
public class Repair extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Integer repairId = Integer.valueOf(request.getParameter("repairId"));

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        ArrayList list = RepairDao.getDetail(repairId);


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
        response.getWriter().write(json.toString());
        return;
    }
}
