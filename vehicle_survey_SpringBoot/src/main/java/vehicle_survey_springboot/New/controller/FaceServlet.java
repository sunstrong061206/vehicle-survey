package vehicle_survey_springboot.New.controller;

import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import vehicle_survey_springboot.New.utils.FaceTest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FaceServlet", value = "/api/face")
public class FaceServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        String svId = null;
        int code;
        String msg;


        ArrayList<InputStream> image = new ArrayList<>();
        Boolean kk = ServletFileUpload.isMultipartContent(request);
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
                        if ("svId".equals(fieldName)) {
                            svId = fileItem.getString("utf-8");
                        }
                    } else {
                        if ("image".equals(fileItem.getFieldName())) {
                            InputStream inputStream = fileItem.getInputStream();
                            image.add(inputStream);
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        double score = FaceTest.FacePass(image.get(0), svId);
        JSONObject json = new JSONObject();
        if (score >= 0) {
            if (score >= 80) {
                msg = "身份验证成功！";
                code = 200;
            } else{
                msg = "身份验证失败！";
                code = 101;
            }
        }else {
            msg = "身份验证失败！";
            code = 999;
        }
        json.put("code", code);
        json.put("msg", msg);

        response.getWriter().write(json.toString());
        return;
    }
}
