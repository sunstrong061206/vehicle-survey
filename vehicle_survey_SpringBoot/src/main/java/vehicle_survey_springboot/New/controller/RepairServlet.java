package vehicle_survey_springboot.New.controller;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import vehicle_survey_springboot.New.Dao.RepairDao;
import vehicle_survey_springboot.New.entity.RepairLog;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ReportRepair", value = "/api/add/report/repair")
public class RepairServlet extends HttpServlet {
    private static final String folderPath = "/home/picture/Repair_vehicle/";
//    private static final String folderPath = "F:/picture/repair_vehicle/";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String repairLicense = null;
        Long repairTime = null;
        String repairMsg = null;
        String position = null;
        Long time = null;
        String no = null;
        ArrayList<String> newFileNames = new ArrayList<>();
        String vetNo = null;
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
                for(FileItem fileItem:fileItemList){
                    if(fileItem.isFormField()){
                        String fieldName = fileItem.getFieldName();// name属性值
                        if("repairLicense".equals(fieldName)) {
                            repairLicense = fileItem.getString("utf-8");
                        } else if("repairTime".equals(fieldName)) {
                            repairTime = Long.valueOf(fileItem.getString("utf-8"));
                        } else if("repairMsg".equals(fieldName)) {
                            repairMsg = fileItem.getString("utf-8");
                        } else if("position".equals(fieldName)) {
                            position = fileItem.getString("utf-8");
                        } else if("no".equals(fieldName)) {
                            no = fileItem.getString("utf-8");
                        } else if("time".equals(fieldName)) {
                            time = Long.valueOf(fileItem.getString("utf-8"));
                        } else if("vetNo".equals(fieldName)) {
                            vetNo = fileItem.getString("utf-8");
                        }
                    }
                    else{
                        if("repairImg".equals(fileItem.getFieldName())){
                            String fileName = fileItem.getName();
                            String suffix = fileName.substring(fileName.lastIndexOf('.'));
                            newFileName = fileItem.hashCode()+"_"+System.currentTimeMillis() + suffix;
                            System.out.println("新文件名:" + newFileName);

                            File imgFolder = new File(folderPath);
                            if(!imgFolder.exists()){
                                imgFolder.mkdirs();
                            }
                            File imgFile = new File(folderPath,newFileName);
                            fileItem.write(imgFile);
                            fileItem.delete();

                            String imgPath = folderPath+"/"+newFileName;
                            System.out.println(imgPath);
                            newFileNames.add(newFileName);
                        }
                    }
                }
            }catch (FileUploadException e){
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        count = RepairDao.add(new RepairLog(repairLicense,repairTime,newFileNames,repairMsg,no,time,vetNo),position);
        if(count > 0){
            code = 200;
            msg = "维修申报成功";
        }else {
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
