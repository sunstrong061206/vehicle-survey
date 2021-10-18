package com.fibikky.detect.server.inspect.service.inspectContext;

import com.fibikky.vehicle.common.Pair;
import com.fibikky.vehicle.network.protobuf.IVDAData;
import com.fibikky.vehicle.network.protobuf.IVDAData.Detected_Img_Frame;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 16861, Aminor_z
 */
@NoArgsConstructor
public class FrontFrameRaw extends AbstractProtobufFrameRaw<Detected_Img_Frame> {
    public FrontFrameRaw(ByteString data) {
        try {
            this.proto = Detected_Img_Frame.parseFrom(data);
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


}
