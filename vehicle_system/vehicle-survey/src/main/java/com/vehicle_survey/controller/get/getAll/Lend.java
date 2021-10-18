package com.vehicle_survey.controller.get.getAll;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.LendDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetAllLend", value = "/api/get/getAll/lend")
public class Lend extends HttpServlet {
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

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        ArrayList list = null;

        if("all".equals(filter)) list = LendDao.getAllList_all(no);
        else if("name".equals(filter)) {
            String name = request.getParameter("name");
            list = LendDao.getAllList_name(no,name);
        }
        else if("license".equals(filter)) {
            String license = request.getParameter("license");
            list = LendDao.getAllList_license(no,license);
        }
        else if("lendTime".equals(filter)) {
            Long lendBegin = Long.valueOf(request.getParameter("lendBegin")),
                    lendEnd = Long.valueOf(request.getParameter("lendEnd"));
            list = LendDao.getAllList_lendTime(no,lendBegin,lendEnd);
        }
        else if("returnTime".equals(filter)) {
            Long returnBegin = Long.valueOf(request.getParameter("returnBegin")),
                    returnEnd = Long.valueOf(request.getParameter("returnEnd"));
            list = LendDao.getAllList_returnTime(no,returnBegin,returnEnd);
        }
        else if("lendResult".equals(filter)){
            Integer lendResult = Integer.valueOf(request.getParameter("lendResult"));
            list = LendDao.getAllList_lendResult(no,lendResult);
        }
        else if("returnResult".equals(filter)) {
            Integer returnResult = Integer.valueOf(request.getParameter("returnResult"));
            list = LendDao.getAllList_returnResult(no,returnResult);
        }else if("isVet".equals(filter)){
            Boolean isVet = Boolean.valueOf(request.getParameter("isVet"));
            list = LendDao.getAllList_isVet(no,isVet);
        }

        if(list.size()>0){
            code = 200;
            msg = "数据不为空，成功";
        }else{
            code = 200;
            msg = "数据为空";
        }

        data = list;
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);
        response.getWriter().write(json.toString());
        return;
    }
}
