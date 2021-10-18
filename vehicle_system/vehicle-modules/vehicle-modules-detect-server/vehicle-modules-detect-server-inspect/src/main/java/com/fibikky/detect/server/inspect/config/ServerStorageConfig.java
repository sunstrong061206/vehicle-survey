package com.fibikky.detect.server.inspect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:/serverStorage.properties"})
public class ServerStorageConfig {
    @Value("${server.storage.img}")
    private String b64ImgStoragePath;

    public ServerStorageConfig() {
    }

    public String getB64ImgStoragePath() {
        return this.b64ImgStoragePath;
    }
}