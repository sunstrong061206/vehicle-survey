package com.fibikky.detect.server.inspect.service.inspectContext;

import com.fibikky.vehicle.network.protobuf.IVDAData;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author 16861
 */
public class BehindFrameRaw extends AbstractProtobufFrameRaw<IVDAData.Detected_Img_Frame> {
    public BehindFrameRaw(ByteString data) {
        try {
            this.proto = IVDAData.Detected_Img_Frame.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片
     *
     * @return byte[] 二进制图片
     */
    public byte[] getImg() {
        return this.proto.getImg().toByteArray();
    }

    /**
     * 获取时间戳
     *
     * @return Long 时间戳
     */
    public Long getTime() {
        return this.proto.getTime();
    }

    /**
     * @param labelIndex Label索引Id
     * @return boolean 是否存在对于id的Label
     */
    public boolean containsLabel(Integer labelIndex) {
        boolean flag = true;
        for (var t : this.proto.getLabelList()) {
            if (labelIndex.equals(t.getIndex())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param labelIndex Label索引Id
     * @return boolean 是否存在对于id的Label
     */
    public int countLabel(Integer labelIndex) {
        int count = 0;
        for (var t : this.proto.getLabelList()) {
            if (labelIndex.equals(t.getIndex())) {
                ++count;
            }
        }
        return count;
    }
}
