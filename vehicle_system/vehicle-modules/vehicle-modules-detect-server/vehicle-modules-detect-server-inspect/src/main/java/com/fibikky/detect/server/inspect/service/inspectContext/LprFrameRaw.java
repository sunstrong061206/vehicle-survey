package com.fibikky.detect.server.inspect.service.inspectContext;

import com.fibikky.vehicle.network.protobuf.IVDAData;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author 16861
 */
public class LprFrameRaw extends AbstractProtobufFrameRaw<IVDAData.Detected_LP_Frame> {
    public LprFrameRaw(ByteString data) {
        try {
            this.proto = IVDAData.Detected_LP_Frame.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片
     * @return byte[] 二进制图片
     */
    public byte[] getImg() {
        return this.proto.getImg().toByteArray();
    }

    /**
     * 获取时间戳
     * @return Long 时间戳
     */
    public Long getTime() {
        return this.proto.getTime();
    }

    /**
     * 获取车牌号
     * @return String 车牌号
     */
    public String getLp() {
        return this.proto.getLp();
    }
}
