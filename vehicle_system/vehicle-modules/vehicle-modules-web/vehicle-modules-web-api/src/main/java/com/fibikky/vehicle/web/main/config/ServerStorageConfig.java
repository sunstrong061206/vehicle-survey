package com.fibikky.vehicle.web.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/config/serverStorage.properties")
public class ServerStorageConfig {
    @Value("${server.storage.b64Img}")
    private String b64ImgStoragePath;

    public String getB64ImgStoragePath() {
        return b64ImgStoragePath;
    }
}
