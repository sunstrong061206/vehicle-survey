package com.fibikky.vehicle.network.util;

import com.fibikky.vehicle.network.protobuf.IVDAData;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fibikky.vehicle.network.util.ByteUtil.subBytes;

public class ProtoMessageDecoder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    byte[] symbol = "<W".getBytes();
    private final int PACKET_HEAD_LENGTH = 4;
    private final int SYMBOL_LENGTH = 2;
    private volatile byte[] bytes = new byte[0];

    public static byte[] mergebyte(byte[] a, byte[] b, int begin, int end) {
        byte[] add = new byte[a.length + end - begin];
        int i = 0;
        for (i = 0; i < a.length; i++) {
            add[i] = a[i];
        }
        for (int k = begin; k < end; k++, i++) {
            add[i] = b[k];
        }
        return add;
    }

    public IVDAData.Wrapper decodeWrapper(byte[] data) {
        byte[] d = subBytes(data, 6, data.length - 6);
        IVDAData.Wrapper dd = null;
        try {
            dd = IVDAData.Wrapper.parseFrom(d);
        } catch (InvalidProtocolBufferException e) {
            logger.error(e.getMessage());
        }
        return dd;
    }

}
