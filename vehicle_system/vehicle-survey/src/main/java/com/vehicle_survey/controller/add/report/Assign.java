package com.vehicle_survey.controller.add.report;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.AssignDao;
import com.vehicle_survey.entity.AssignLog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ReportAssign", value = "/api/add/report/assign")
public class Assign extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String assignLicense = request.getParameter("assignLicense");
        Long assignTime = Long.valueOf(request.getParameter("assignTime"));
        String assignPoint = request.getParameter("assignPoint");
        String assignMsg = request.getParameter("assignMsg");
        String surveyNo = request.getParameter("surveyNo");
        String manageNo = request.getParameter("manageNo");
        String vetNo = request.getParameter("vetNo");
        Long manageTime = Long.valueOf(request.getParameter("manageTime"));

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        int count = AssignDao.add(new AssignLog(assignLicense,assignTime,assignPoint,assignMsg,surveyNo,manageNo,manageTime,vetNo));

        if(count > 0){
            code = 200;
            msg = "派车申报成功";
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
