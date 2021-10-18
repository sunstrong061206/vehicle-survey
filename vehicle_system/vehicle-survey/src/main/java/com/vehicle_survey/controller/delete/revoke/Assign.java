package com.vehicle_survey.controller.delete.revoke;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.AssignDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RevokeAssign", value = "/api/delete/revoke/assign")
public class Assign extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Integer assignId =Integer.valueOf( request.getParameter("assignId"));
        Long revokeTime = Long.valueOf(request.getParameter("revokeTime"));

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        int count = AssignDao.revoke(assignId, revokeTime);
        if(count > 0){
            code = 200;
            msg ="撤销成功";
        }else if(count == -1){
            code = 333;
            msg = "通过审批后，无法撤销";
        }
        else{
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
