package vehicle_survey_springboot.New.utils;



import com.baidu.aip.util.Base64Util;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import vehicle_survey_springboot.New.Dao.FaceDao;
//import vehicle_survey_springboot.New.utils.FileUtil;
//import vehicle_survey_springboot.New.utils.GsonUtils;
//import vehicle_survey_springboot.New.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceTest {
//    static final String  facePath = "/home/picture/Employee_faceData/";
    static final String  facePath = "F:/picture/Employee_faceData/";

    public static double FacePass(InputStream path, String svId){
        // 官网获取的 API Key 更新为你注册的
        String clientId = "krobMCpPiOkxG7SLdLPpWCoY";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "L2cmBTGSqzanfx94WyapWQUK8EiCstdM";

        String access_token=getAuth(clientId, clientSecret);
        //调用人脸对比
        String request_url="https://aip.baidubce.com/rest/2.0/face/v3/match";
        String url=request_url+"?access_token="+access_token;

        String imgParam1="" ;
        String imgParam2="" ;
        byte[] bbuf1 = new byte[10240];
        byte[] bbuf2 = new byte[10240];
        try {
            bbuf1= IOUtils.toByteArray(path);
            bbuf2=FileUtil.readFileByBytes(facePath+FaceDao.GetPath(svId));
            imgParam1= Base64Util.encode(bbuf1);
            imgParam2=Base64Util.encode(bbuf2);
        } catch (IOException | SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        long startTime = System.currentTimeMillis(); //程序开始记录时间

        // image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
        HashMap<String, Object> map1=new HashMap<String, Object>();
        HashMap<String, Object> map2=new HashMap<String, Object>();
        map1.put("image", imgParam1);
        map1.put("image_type", "BASE64");
        map1.put("face_type", "LIVE");
        map1.put("quality_control", "NONE");
        map1.put("liveness_control", "NORMAL");
        map2.put("image", imgParam2);
        map2.put("image_type", "BASE64");
        map2.put("face_type", "LIVE");
        map2.put("quality_control", "NONE");
        map2.put("liveness_control", "NORMAL");
        ArrayList<HashMap> arr=new ArrayList();
        arr.add(map1);
        arr.add(map2);
        String param = GsonUtils.toJson(arr);


        try {
            double point = 0;
            String result = HttpUtil.post(url, access_token, "application/json", param);
            long endTime = System.currentTimeMillis(); //程序结束记录时间
            long TotalTime = endTime - startTime; //总消耗时间
            /*System.out.println("time cost:"+TotalTime+"ms");
            System.out.println(result);
            System.out.println("------------");
            System.out.println(score.get("score"));*/
            JSONObject json = new JSONObject(result);
            JSONObject score = (JSONObject) json.get("result");
            //System.out.println(score.get("score").getClass().toString());
            if(score.get("score") instanceof Integer){
                point = Double.valueOf(score.get("score").toString());
            }else {
                point = (double) (score.get("score"));
            }
            System.out.println(point);
            return point;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -100;
    }

    public static String getAuth(String ak, String sk)
    {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
}
