package vehicle_survey_springboot.New.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import vehicle_survey_springboot.New.Dao.LendDao;
import vehicle_survey_springboot.New.entity.LendLog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ReportLend", value = "/api/add/report/lend")
public class LendAddServlet extends HttpServlet {
        private static final String Drivecard_folderPath = "/home/picture/vehicle_survey/Drivecard_img/";
//    private static final String Drivecard_folderPath = "F:/picture/Drivecard_img/";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String lendLicense = null;
        String lendName = null;
        String lendPhone = null;
        ArrayList<String> lendDrivecardImg = new ArrayList<>();
        ArrayList<String> returnImg = new ArrayList<>();
        Long lendTime1 = null;
        Integer lendDays = null;
        String lendMsg = null;
        String no = null;
        Long time1 = null;
        String vetNo = null;
        String newFileName;
        Timestamp lendTime = null;
        Timestamp time = null;

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
                        String fieldName = fileItem.getFieldName();// name?????????
                        if ("lendLicense".equals(fieldName)) {
                            lendLicense = fileItem.getString("utf-8");
                        } else if ("lendTime".equals(fieldName)) {
                            lendTime1 = Long.valueOf(fileItem.getString("utf-8"));
                            lendTime = new Timestamp(lendTime1);
                        } else if ("lendMsg".equals(fieldName)) {
                            lendMsg = fileItem.getString("utf-8");
                        } else if ("lendName".equals(fieldName)) {
                            lendName = fileItem.getString("utf-8");
                        } else if ("lendPhone".equals(fieldName)) {
                            lendPhone = fileItem.getString("utf-8");
                        } else if ("lendDays".equals(fieldName)) {
                            lendDays = Integer.valueOf(fileItem.getString("utf-8"));
                        } else if ("no".equals(fieldName)) {
                            no = fileItem.getString("utf-8");
                        } else if ("time".equals(fieldName)) {
                            time1 = Long.valueOf(fileItem.getString("utf-8"));
                            time = new Timestamp(time1);
                        } else if("vetNo".equals(fieldName)) {
                            vetNo = fileItem.getString("utf-8");
                        }
                    } else {
                        if ("lendDrivecardImg".equals(fileItem.getFieldName())) {
                            String fileName = fileItem.getName();
                            String suffix = fileName.substring(fileName.lastIndexOf('.'));
                            newFileName = fileItem.hashCode() + "_" + System.currentTimeMillis() + suffix;

                            File imgFolder = new File(Drivecard_folderPath);
                            if (!imgFolder.exists()) {
                                imgFolder.mkdirs();
                            }
                            File imgFile = new File(Drivecard_folderPath, newFileName);
                            fileItem.write(imgFile);
                            fileItem.delete();

                            System.out.println("????????????????????????:" + Drivecard_folderPath+ newFileName);
                            lendDrivecardImg.add(newFileName);
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        count = LendDao.addLend(new LendLog(lendLicense, lendName, lendPhone, lendDrivecardImg, lendTime, lendDays, lendMsg, no, time,vetNo));
        if (count > 0) {
            code = 200;
            msg = "??????????????????";
        } else {
            code = 999;
            msg = "??????????????????????????????";
        }
        data = map;
        json.put("code", code);
        json.put("msg", msg);
        json.put("data", data);
        response.getWriter().write(json.toString());
        return;
    }
}