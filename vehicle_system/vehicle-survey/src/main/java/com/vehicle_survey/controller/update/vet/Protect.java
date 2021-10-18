package com.vehicle_survey.controller.update.vet;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.ProtectDao;
import com.vehicle_survey.entity.ProtectLog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "VetProtect", value = "/api/update/vet/protect")
public class Protect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Integer protectId = Integer.valueOf(request.getParameter("protectId"));
        String vetNo = request.getParameter("vetNo");
        Long vetTime = Long.valueOf(request.getParameter("vetTime"));
        Integer result = Integer.valueOf(request.getParameter("result"));
        String resultMsg= request.getParameter("resultMsg");

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        int count = ProtectDao.vet(new ProtectLog(protectId,vetNo,vetTime,result,resultMsg));
        if(count >0){
            code = 200;
            msg  = "审批成功";
            map.put("protectId",protectId);
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
