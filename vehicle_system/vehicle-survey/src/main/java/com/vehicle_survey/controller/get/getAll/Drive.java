package com.vehicle_survey.controller.get.getAll;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.DriveDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetAllDrive", value = "/api/get/getAll/drive")
public class Drive extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String filter = request.getParameter("filter");
        String no = request.getParameter("no");

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        ArrayList list =null;

        if("all".equals(filter)) list = DriveDao.getAll_all(no);
        else if("license".equals(filter)) {
            String license = request.getParameter("license");
            list = DriveDao.getAll_license(no,license);
        }

        if(list!=null){
            if(list.size()>0){
                code = 200;
                msg = "数据不为空，成功";
            }
            else{
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
