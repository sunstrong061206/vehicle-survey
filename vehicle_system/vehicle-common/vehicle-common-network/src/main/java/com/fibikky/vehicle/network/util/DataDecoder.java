package com.fibikky.vehicle.network.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * protobuf 载荷二进制解码
 *
 * @author Aminor_z
 */
@Component
public class DataDecoder {
    public BufferedImage decodeImg(byte[] data) {
        try {
            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(data);
            return ImageIO.read(fileInputStream);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
