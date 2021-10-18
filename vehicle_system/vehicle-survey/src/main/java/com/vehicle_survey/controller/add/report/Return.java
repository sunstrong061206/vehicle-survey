package com.vehicle_survey.controller.add.report;

import com.alibaba.fastjson.JSONObject;
import com.vehicle_survey.dao.LendDao;
import com.vehicle_survey.entity.LendLog;
import com.vehicle_survey.entity.RepairLog;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ReportReturn", value = "/api/add/report/return")
public class Return extends HttpServlet {
    private static final String ReturnImg_folderPath = "F:/picture/ReturnVehicle_img";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        Integer lendId = null;
        Long returnTime = null;
        String no = null;
        Long time = null;
        ArrayList<String> returnImgs = new ArrayList<>();
        String newFileName;


        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();

        int count;
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload sfu = new ServletFileUpload(factory);
                sfu.setSizeMax(10 * 1024 * 1024);
                sfu.setHeaderEncoding("utf-8");
                List<FileItem> fileItemList = sfu.parseRequest(request);

                for (FileItem fileItem : fileItemList) {
                    if (fileItem.isFormField()) {
                        String fieldName = fileItem.getFieldName();// name属性值
                        if ("lendId".equals(fieldName)) lendId = Integer.valueOf(fileItem.getString("utf-8"));
                        else if ("returnTime".equals(fieldName)) returnTime = Long.valueOf(fileItem.getString("utf-8"));
                        else if ("no".equals(fieldName)) no = fileItem.getString("utf-8");
                        else if ("time".equals(fieldName)) time = Long.valueOf(fileItem.getString("utf-8"));
                    } else {
                        if ("returnImg".equals(fileItem.getFieldName())) {
                            String fileName = fileItem.getName();
                            String suffix = fileName.substring(fileName.lastIndexOf('.'));
                            newFileName = fileItem.hashCode() + "_" + System.currentTimeMillis() + suffix;
                            System.out.println("新文件名:" + newFileName);

                            File imgFolder = new File(ReturnImg_folderPath);
                            if (!imgFolder.exists()) {
                                imgFolder.mkdirs();
                            }
                            File imgFile = new File(ReturnImg_folderPath, newFileName);
                            fileItem.write(imgFile);
                            fileItem.delete();

                            String imgPath = ReturnImg_folderPath + "/" + newFileName;
                            System.out.println(imgPath);
                            returnImgs.add(newFileName);
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            count = LendDao.addReturn(new LendLog(lendId, returnTime, returnImgs, time));
            if (count > 0) {
                code = 200;
                msg = "归还申报成功";
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
}
