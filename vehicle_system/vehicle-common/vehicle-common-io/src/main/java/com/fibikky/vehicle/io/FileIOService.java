package com.fibikky.vehicle.io;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件io服务
 *
 * @author Aminor_z
 */
@Service
public class FileIOService {
    public String imgSaveFormat = "JPG";

    public boolean saveImg(BufferedImage img, String path) {
        try {

            FileOutputStream outputStream = new FileOutputStream(path);
            ImageIO.write(img, imgSaveFormat, outputStream);
            return true;
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveImg(byte[] img, String path) {
        try {
            return saveImg(ImageIO.read(new ByteArrayInputStream(img)), path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BufferedImage readImg(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            return ImageIO.read(fileInputStream);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
