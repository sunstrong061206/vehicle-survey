package com.vehicle_survey.controller;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.EmployeeDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Login", value = "/api/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String no = request.getParameter("no");
        String pwd = request.getParameter("password");


        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<String, Object> mid = EmployeeDao.login(no,pwd);
        Map<String, Object> map = new HashMap<>();

       if(mid!=null){
           if("登录成功".equals(mid.get("msg"))){
               code = 200;
               msg = "登录成功";
                map.put("employee",mid.get("employee"));
           }else{
               code = 303;
               msg = "工号或密码错误";
           }
       }else{
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
