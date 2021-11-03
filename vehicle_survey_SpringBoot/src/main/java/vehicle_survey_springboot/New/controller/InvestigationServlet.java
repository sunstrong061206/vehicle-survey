package vehicle_survey_springboot.New.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import vehicle_survey_springboot.New.Dao.InvestigationDao;
import vehicle_survey_springboot.New.Dao.LendDao;
import vehicle_survey_springboot.New.entity.LendLog;
import vehicle_survey_springboot.New.service.AddService;

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

@WebServlet(name = "InvestigationServlet", value = "/api/add/investigation")
public class InvestigationServlet extends HttpServlet {

    private static final String Investigation_folderPath = "/home/picture/vehicle_survey/Investigate_img/";
//    private static final String Investigation_folderPath = "F:/picture/Investigate_img/";



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String license = null;
        String reason = null;
        ArrayList<String> image = new ArrayList<>();

        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        String newFileName = null;
        Long time1 = System.currentTimeMillis();
        String svNo = request.getParameter("svNo");
        Timestamp time = new Timestamp(time1);
        Map<Object, Object> map = new HashMap<>();
        int count = 0;
        Boolean k = ServletFileUpload.isMultipartContent(request);
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
                        if ("reason".equals(fieldName)) {
                            reason = fileItem.getString("utf-8");
                        } else if ("license".equals(fieldName)) {
                            license = fileItem.getString("utf-8");
                        }else if("svNo".equals(fieldName)){
                            svNo = fileItem.getString("utf-8");
                        }
                    } else {
                        if ("image".equals(fileItem.getFieldName())) {
                            String fileName = fileItem.getName();
                            String suffix = fileName.substring(fileName.lastIndexOf('.'));
                            newFileName = fileItem.hashCode() + "_" + System.currentTimeMillis() + suffix;

                            File imgFolder = new File(Investigation_folderPath);
                            if (!imgFolder.exists()) {
                                imgFolder.mkdirs();
                            }
                            File imgFile = new File(Investigation_folderPath, newFileName);
                            fileItem.write(imgFile);
                            fileItem.delete();

                            System.out.println("车牌照片:" + Investigation_folderPath + "/" + newFileName);
                            image.add(newFileName);
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        count = InvestigationDao.addInvestigation(time,license,reason,image.get(0),svNo);
        if (count > 0) {
            code = 200;
            msg = "上传成功";
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
