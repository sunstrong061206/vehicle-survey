package com.vehicle_survey.controller.get.getAll;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.VehicleDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "GetAllVehicleInfo", value = "/api/get/getAll/vehicleInfo")
public class VehicleInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String filter = request.getParameter("filter");
        String pos = request.getParameter("position");
        String no = request.getParameter("no");

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        ArrayList list = null;

        if ("all".equals(filter)) list = VehicleDao.getAll_all(no, pos);
        else if ("manageName".equals(filter)) {
            if ("vet".equals(pos)) {
                String manageName = request.getParameter("manageName");
                list = VehicleDao.getAll_name(no, manageName);
            }
        } else if ("status".equals(filter)) {
            Integer status = Integer.valueOf(request.getParameter("status"));
            list = VehicleDao.getAll_status(no, pos, status);
        } else if ("peopleNum".equals(filter)) {
            Integer peopleNumMin = Integer.valueOf(request.getParameter("peopleNumMin")),
                    peopleNumMax = Integer.valueOf(request.getParameter("peopleNumMax"));
            list = VehicleDao.getAll_peopleNum(no, pos, peopleNumMin, peopleNumMax);
        } else if ("lastProtectTime".equals(filter)) {
            Long lastProtectTimeBegin = Long.valueOf(request.getParameter("lastProtectTimeBegin")),
                    lastProtectTimeEnd = Long.valueOf(request.getParameter("lastProtectTimeEnd"));
            list = VehicleDao.getAll_lastProtectTime(no, pos, lastProtectTimeBegin, lastProtectTimeEnd);
        }

        if ("manageName".equals(filter) && ("manage".equals(pos))) {
            code = 303;
            msg = "管理员只能查看自己管理的车辆";
        } else if (list.size() > 0) {
            code = 200;
            msg = "数据不为空，成功";
        } else if (list.size() == 0) {
            code = 200;
            msg = "数据为空";
        } else {
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
