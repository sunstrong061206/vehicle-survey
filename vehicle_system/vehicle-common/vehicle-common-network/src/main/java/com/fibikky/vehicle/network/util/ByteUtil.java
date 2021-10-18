package com.fibikky.vehicle.network.util;

public class ByteUtil {
    /**
     * int到byte[] 由高位到低位
     *
     * @param i 需要转换为byte数组的整行值。
     * @return byte数组
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static byte[] subBytes(byte[] bytes, int start, int length) {
        byte[] result = new byte[length];
        int end = start + length;
        for (int i = start, index = 0; i < end; ++i, ++index) {
            result[index] = bytes[i];
        }
        return result;
    }

    /**
     * byte[]转int
     *
     * @param bytes 需要转换成int的数组
     * @return int值
     */
    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (3 - i) * 8;
            value += (bytes[i] & 0xFF) << shift;
        }
        return value;
    }

    public static byte[] byteMerge(byte[]... bts) {
        int size = 0;
        for (var bt : bts) {
            size += bt.length;
        }
        byte[] result = new byte[size];
        int index = 0;
        for (byte[] bt : bts) {
            System.arraycopy(bt, 0, result, index, bt.length);
            index += bt.length;
        }
        return result;
    }
}
