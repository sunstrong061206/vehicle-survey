package vehicle_survey_springboot.New.controller;

import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import vehicle_survey_springboot.New.utils.LicensePlate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LicenseServlet", value = "/api/license")
public class LicenseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        int code;
        String msg;

        InputStream licenseImage =null;

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload sfu = new ServletFileUpload(factory);
                sfu.setSizeMax(10 * 1024 * 1024);
                sfu.setHeaderEncoding("utf-8");
                List<FileItem> fileItemList = sfu.parseRequest(request);
                for (FileItem fileItem : fileItemList) {
                        if ("licenseImg".equals(fileItem.getFieldName())) {
                            licenseImage =fileItem.getInputStream();
                        }
                    }
                }
            catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String license = LicensePlate.licensePlate(licenseImage);
        if(license!=null){
            msg = license;
            code = 200;
        }else {
            msg = "网络连接异常";
            code = 999;
        }

        JSONObject json = new JSONObject();

        json.put("code", code);
        json.put("msg", msg);

        response.getWriter().write(json.toString());
        return;


    }
}
