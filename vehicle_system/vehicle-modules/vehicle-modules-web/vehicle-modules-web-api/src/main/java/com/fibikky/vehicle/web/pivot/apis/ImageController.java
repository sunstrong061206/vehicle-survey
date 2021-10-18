package com.fibikky.vehicle.web.pivot.apis;


import com.fibikky.vehicle.common.web.response.Message;
import com.fibikky.vehicle.common.web.response.Response;
import com.fibikky.vehicle.common.web.response.ReturnCode;
import com.fibikky.vehicle.web.main.config.ServerStorageConfig;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author 16861
 */
@RestController
@RequestMapping("/api/image")
public class ImageController {
    final ServerStorageConfig serverStorageConfig;

    public ImageController(ServerStorageConfig serverStorageConfig) {
        this.serverStorageConfig = serverStorageConfig;
    }


    @RequestMapping("/get")
    public Response<String> downloadImg(@RequestBody Map<String, Integer> request) {
        var path = serverStorageConfig.getB64ImgStoragePath() + request.get("filename") + ".lpb64";
        String img = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String str = in.readLine();
            while (str != null) {
                sb.append(str);
                str = in.readLine();
            }
            img = sb.toString();
        } catch (IOException ignored) {
            return new Response<>(ReturnCode.INTERNAL_SERVER_ERROR, Message.INTERNAL_SERVER_ERROR);
        }
        return new Response<>(ReturnCode.OK, Message.OK, img);
    }
}
