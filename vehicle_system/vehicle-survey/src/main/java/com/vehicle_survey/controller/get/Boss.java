package com.vehicle_survey.controller.get;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.EmployeeDao;
import com.vehicle_survey.entity.Employee;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "BossNo", value = "/api/get/boss")
public class Boss extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String no = request.getParameter("no");

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map map = new HashMap();
        Employee boss =  EmployeeDao.getBoss(no);

        if(boss!=null){
            code = 200;
            msg = "数据不为空";
            map.put("Boss",boss);
        }
        else{
            code = 999;
            msg = "网络异常，请稍后再试";
        }
        data = map;
        json.put("code",code);
        json.put("msg",msg);
        json.put("data",data);
        response.getWriter().write(json.toString());
        return;
    }
}
