package com.vehicle_survey.controller.update;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.EmployeeDao;
import com.vehicle_survey.entity.Employee;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Verify", value = "/api/update/verify")
public class Verify extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<String, Object> map = new HashMap<>();

        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String pos = request.getParameter("position");
//        String dept = request.getParameter("dept");
        String wechat = request.getParameter("wechat");
        String pwd = request.getParameter("password");
        int count = EmployeeDao.verify(new Employee(no,name,pos,wechat,pwd));


        /*
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                // 1. 创建DiskFileItemFactory对象，设置缓冲区大小和临时文件目录
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // System.out.println(System.getProperty("java.io.tmpdir"));//默认临时文件夹
                // 2. 创建ServletFileUpload对象，并设置上传文件的大小限制。
                ServletFileUpload sfu = new ServletFileUpload(factory);
                sfu.setSizeMax(10 * 1024 * 1024);// 以byte为单位 不能超过10M
                sfu.setHeaderEncoding("utf-8");
                // 3.调用ServletFileUpload.parseRequest方法解析request对象，得到一个保存了所有上传内容的List对象。
                @SuppressWarnings("unchecked")
                List<FileItem> fileItemList = sfu.parseRequest(request);
                // 4. 遍历list，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
                for (FileItem fileItem : fileItemList) {
                    // 普通表单元素
                    if (fileItem.isFormField()) {
                        String fieldName = fileItem.getFieldName();
                        if ("no".equals(fieldName)) no = fileItem.getString("utf-8");
                        else if ("name".equals(fieldName)) name = fileItem.getString("utf-8");
                        else if ("position".equals(fieldName)) pos = fileItem.getString("utf-8");
                        else if ("dept".equals(fieldName)) dept = fileItem.getString("utf-8");
                        else if ("openId".equals(fieldName)) openId = fileItem.getString("utf-8");
                        else if("password".equals(fieldName)) pwd = fileItem.getString("utf-8");
                    } else {
                        // 人脸信息识别核对
                        // ......
                    }
                }
                if (true) {
                    // 人脸信息匹配通过
                    Employee employee = new Employee(no, name, pos, dept, openId,pwd);
//                            Thread.sleep(200);
                    count = EmployeeDao.verify(employee);
                } else {
                    // -3表示人脸信息不匹配
                    count = -3;
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         */

       if (count == 1) {
            code = 200;
            msg = "认证成功";
            map.put("no",no);
        } else if (count == -1) {
            code = 303;
            msg = "查无此人，请确认填写无误";
        } else if (count == -2) {
            code = 333;
            msg = "此账号已认证，不可重复认证，请选择密码登录";
        } else if (count == -3) {
            code = 333;
            msg = "人脸信息不匹配，请重新认证";
        } else {
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
