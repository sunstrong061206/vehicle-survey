package vehicle_survey_springboot.New;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import vehicle_survey_springboot.New.entity.Vehicle;
import vehicle_survey_springboot.New.Dao.VehicleDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "pickVehicle", value = "/api/add/pickVehicle")
public class PickVehicle extends HttpServlet {
    // 设置文件夹
    private static final String folderPath = "/home/picture/vehicle_survey/Vehicle_img/";
//    private static final String folderPath = "F:/picture/Vehicle_img/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        // 响应体内容
        JSONObject json = new JSONObject();
        int code;
        String msg;
        Object data;
        Map<Object, Object> map = new HashMap<>();


        String license = "";
        int type = -1;
        int peopleNum = -1;
        String emission = "";
        String newFileName = ""; //新生成的文件名

        double price = -1;
        String manageNo = "";


        int count = 0;

        // 判断上传表单是否为multipart/form-data类型
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
                Iterator<FileItem> fileItems = fileItemList.iterator();
                // 4. 遍历list，每迭代一个FileItem对象，调用其isFormField方法判断是否是上传文件
                for (FileItem fileItem : fileItemList) {
                    // 普通表单元素
                    if (fileItem.isFormField()) {
                        String fieldName = fileItem.getFieldName();// name属性值
                        if ("license".equals(fieldName)) {
                            license = fileItem.getString("utf-8");
                        } else if ("manageNo".equals(fieldName)) {
                            manageNo = fileItem.getString("utf-8");
                        } else if ("type".equals(fieldName)) {
                            type = Integer.valueOf(fileItem.getString("utf-8"));
                        } else if ("peopleNum".equals(fieldName)) {
                            peopleNum = Integer.valueOf(fileItem.getString("utf-8"));
                        } else if ("emission".equals(fieldName)) {
                            emission = fileItem.getString("utf-8");
                        } else if ("price".equals(fieldName)) {
                            price = Double.valueOf(fileItem.getString("utf-8"));
                        }
                    }
                    // <input type="file">的上传文件的元素
                    else {
                        String fileName = fileItem.getName();// 获取上传的文件名称
                        String suffix = fileName.substring(fileName.lastIndexOf('.'));//获取文件扩展名
                        // 新文件名（唯一）HashCode+时间戳
                        newFileName = fileItem.hashCode() + "_" + System.currentTimeMillis() + suffix;
                        System.out.println("新文件名:" + newFileName);

                        File imgFolder = new File(folderPath);
                        if (!imgFolder.exists()) {
                            imgFolder.mkdirs();
                        }
                        //将图片存入文件夹
                        File imgFile = new File(folderPath, newFileName);
                        // 将上传的文件写到服务器上指定的文件。
                        fileItem.write(imgFile);
                        // 6. 调用FileItem的delete()方法，删除临时文件
                        fileItem.delete();

                        //7.接下来就是把图片路径（headUrl）存储到自己的数据库中去了
                        String imgPath = folderPath + "/" + newFileName; // 拼接相对路径 imgFile/1478509873038.jpg
                        System.out.println(imgPath);

                    }
                }
                // 没有此车，才能添加
                if (VehicleDao.isExist(license) == false) {
                    //现场休眠100毫秒（作用是使当前线程暂时睡眠指定的时间）
                    Thread.sleep(300);
                    Vehicle vehicle = new Vehicle(license, type, peopleNum, emission, manageNo, newFileName, price);
                    count = VehicleDao.add(vehicle);
                } else if (VehicleDao.isExist(license) == true) {
                    count = -1;
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (count > 0) {
            // 车辆添加成功
            code = 200;
            msg = "车辆添加成功";

        } else if (count == -1) {
            // 车辆已存在，无法添加
            code = 303;
            msg = "车辆已存在，无法添加";
        } else {
            // 异常导致失败
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


    public static String getFolderPath() {
        return folderPath;
    }

}
