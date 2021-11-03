package vehicle_survey_springboot.New.utils;

import com.baidu.aip.util.Base64Util;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;


import java.io.InputStream;
import java.net.URLEncoder;

public class LicensePlate {
    public static String licensePlate(InputStream licenseImg) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        try {

            byte[] imgData = IOUtils.toByteArray(licenseImg);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = FaceTest.getAuth("Vv0GzHPGfNPblMb6rAnf9NKn","LaNLrWlQ5wpFMa27CeqOXOp7hc9zkZLV");

            String result = HttpUtil.post(url, accessToken, param);
            JSONObject json = new JSONObject(result);
            JSONObject words_result = json.getJSONObject("words_result");
            String license = words_result.getString("number");
            System.out.println(license);
            return license;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
