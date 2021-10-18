package com.vehicle_survey.controller.delete.revoke;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.ProtectDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "RevokeProtect", value = "/api/delete/revoke/protect")
public class Protect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Integer protectId =Integer.valueOf( request.getParameter("protectId"));
        Long revokeTime = Long.valueOf(request.getParameter("revokeTime"));

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        int count = ProtectDao.revoke(protectId,revokeTime);
        if(count > 0){
            code = 200;
            msg ="撤销成功";
        }else if(count == -1){
            code = 333;
            msg = "当前状态无法撤销";
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
