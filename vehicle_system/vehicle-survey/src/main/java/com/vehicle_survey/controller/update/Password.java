package com.vehicle_survey.controller.update;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.EmployeeDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "updatePassword", value = "/api/update/password")
public class Password extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String no = request.getParameter("no");
        String oldPwd = request.getParameter("oldPassword");
        String newPwd = request.getParameter("newPassword");

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        int count = EmployeeDao.updatePwd(no,oldPwd,newPwd);
        if(count == -1) {
            code = 303;
            msg = "密码错误，无法修改";
        }else if(count == 1){
            code = 200;
            msg="修改成功，请重新登录";
        }else{
            code = 999;
            msg="网络异常，请稍后再试";
        }

        data = map;
        json.put("code",code);
        json.put("msg",msg);
        json.put("data",data);
        response.getWriter().write(json.toString());
        return;

    }
}
