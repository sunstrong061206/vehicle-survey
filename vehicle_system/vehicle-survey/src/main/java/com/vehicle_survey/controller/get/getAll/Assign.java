package com.vehicle_survey.controller.get.getAll;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.AssignDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetAllAssign", value = "/api/get/getAll/assign")
public class Assign extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String filter = request.getParameter("filter");
        String no = request.getParameter("no");
        String pos = request.getParameter("position");


        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        ArrayList list = null;

        if("all".equals(filter)) list = AssignDao.getAll_all(no,pos);
        else if("time".equals(filter)) {
            Long begin = Long.valueOf(request.getParameter("begin")),
                    end = Long.valueOf(request.getParameter("end"));
            list = AssignDao.getAll_time(no,pos,begin,end);
        }
        else if("license".equals(filter)) {
            String license = request.getParameter("license");
            list = AssignDao.getAll_license(no,pos,license);
        }
        else if("result".equals(filter)) {
            Integer result = Integer.valueOf(request.getParameter("result"));
            list = AssignDao.getAll_result(no,pos,result);
        }else if("isVet".equals(filter)){
            Boolean isVet = Boolean.valueOf(request.getParameter("isVet"));
            list  = AssignDao.getAll_isVet(no,isVet);
        }

        if(list != null){
            if(list.size()>0){
                code = 200;
                msg = "数据不为空，成功";
            }else{
                code = 200;
                msg = "数据为空";
            }
        }else{
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
