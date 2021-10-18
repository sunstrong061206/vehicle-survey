package com.fibikky.vehicle.network.websockets;


import com.fibikky.vehicle.network.protobuf.IVDAData.Wrapper;
import com.fibikky.vehicle.network.util.HookData;
import com.fibikky.vehicle.network.util.ProtoMessageDecoder;
import com.fibikky.vehicle.network.util.maps.CachedHashMap;
import com.google.protobuf.ByteString;
import lombok.Getter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

import static com.fibikky.vehicle.network.util.ByteUtil.byteMerge;
import static com.fibikky.vehicle.network.util.ByteUtil.intToByteArray;

/**
 * 使用ProtoBuf传输的 {@link WebSocketClient WebSocketClient}
 *
 * @author Aminor_z
 */
public class ProtobufWebSocketClient extends WebSocketClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProtoMessageDecoder decoder = new ProtoMessageDecoder();

//    @Getter
//    @Setter
//    private String name = "ProtobufWebSocketClient";

    @Getter
    CachedHashMap<String, Consumer<HookData<ProtobufWebSocketClient>>> hooks = new CachedHashMap<>();

    public ProtobufWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public ProtobufWebSocketClient(String serverUri) {
        super(URI.create(serverUri));
    }

    protected void logErrorMessage(Exception exception) {
        logger.error(exception.getMessage());
    }

    @Override
    public void onMessage(String message) {
        logger.info(message);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        Wrapper wrapper = decoder.decodeWrapper(bytes.array());
        Consumer<HookData<ProtobufWebSocketClient>> consumer = hooks.get(wrapper.getType());
        if (consumer != null) {
            consumer.accept(new HookData<>(this, wrapper.getData()));
        } else {
            ;
        }
    }

    public void send(String type, ByteString byteString) {
        Wrapper wrapper = Wrapper.newBuilder().setType(type).setData(byteString).build();
        byte[] byte_data = wrapper.toByteArray();
        byte[] byte_symbol = "<W".getBytes();
        byte[] byte_size = intToByteArray(byte_data.length);
        byte[] result_byte_data = byteMerge(byte_symbol, byte_size, byte_data);
        try {
            super.send(result_byte_data);
        } catch (WebsocketNotConnectedException websocketNotConnectedException) {
            ;
        } catch (Exception e) {
            logErrorMessage(e);
        }
    }

    public void send(String type, byte[] bytes) {
        this.send(type, ByteString.copyFrom(bytes));
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info(serverHandshake.getHttpStatusMessage());
        String info = this.uri.toString() + " connected.";
        logger.info(info);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        String info = " disconnected. [" +
                i + "=" + s +
                "]";
        logger.info(info);
    }

    @Override
    public void onError(Exception e) {
        logErrorMessage(e);
    }
}

