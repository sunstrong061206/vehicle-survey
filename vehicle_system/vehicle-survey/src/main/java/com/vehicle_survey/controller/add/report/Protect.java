package com.vehicle_survey.controller.add.report;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.ProtectDao;
import com.vehicle_survey.entity.ProtectLog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ReportProtect", value = "/api/add/report/protect")
public class Protect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String protectLicense = request.getParameter("protectLicense");
        Long protectTime = Long.valueOf(request.getParameter("protectTime"));
        String protectMsg = request.getParameter("protectMsg");
        String manageNo  =request.getParameter("manageNo");
        Long manageTime = Long.valueOf(request.getParameter("manageTime"));
        String vetNo = request.getParameter("vetNo");

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        int count = ProtectDao.add(new ProtectLog(protectLicense,protectTime,protectMsg,manageNo,manageTime,vetNo));
        if(count > 0){
            code = 200;
            msg = "保养申报成功";
        }else {
            // 异常导致失败
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
