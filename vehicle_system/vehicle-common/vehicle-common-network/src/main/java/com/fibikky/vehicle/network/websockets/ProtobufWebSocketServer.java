package com.fibikky.vehicle.network.websockets;

import com.fibikky.vehicle.common.service.JwtRestService;
import com.fibikky.vehicle.network.exceptions.NoSuchHookException;
import com.fibikky.vehicle.network.protobuf.IVDAData;
import com.fibikky.vehicle.network.util.HookData;
import com.fibikky.vehicle.network.util.ProtoMessageDecoder;
import com.fibikky.vehicle.network.util.maps.CachedHashMap;
import com.google.protobuf.ByteString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static com.fibikky.vehicle.network.util.ByteUtil.byteMerge;
import static com.fibikky.vehicle.network.util.ByteUtil.intToByteArray;

/**
 * 使用ProtoBuf传输的 {@link WebSocketServer WebSocketServer}
 *
 * @author Aminor_z
 */
public class ProtobufWebSocketServer extends WebSocketServer {
    private static final JwtRestService jwtRestService = new JwtRestService();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicInteger onlineNum = new AtomicInteger();
    private final ProtoMessageDecoder decoder = new ProtoMessageDecoder();
    @Getter
    private final List<Consumer<CloseInfo>> serverConnectionCloseEvents = new LinkedList<>();
    @Getter
    @Setter
    private boolean useAuthCheck = true;

    public ProtobufWebSocketServer(String ip, int port) throws UnknownHostException {
        super(new InetSocketAddress(InetAddress.getByName(ip), port));
    }

    protected int addOnlineCount() {
        return onlineNum.incrementAndGet();
    }

    protected int subOnlineCount() {
        return onlineNum.decrementAndGet();
    }

    public void send(WebSocket webSocket, String type, ByteString byteString) {
        IVDAData.Wrapper wrapper = IVDAData.Wrapper.newBuilder().setType(type).setData(byteString).build();
        byte[] byte_data = wrapper.toByteArray();
        byte[] byte_symbol = "<W".getBytes();
        byte[] byte_size = intToByteArray(byte_data.length);
        byte[] result_byte_data = byteMerge(byte_symbol, byte_size, byte_data);
        webSocket.send(result_byte_data);
    }

    public void broadcast(String type, ByteString byteString) {
        IVDAData.Wrapper wrapper = IVDAData.Wrapper.newBuilder().setType(type).setData(byteString).build();
        this.broadcast(wrapper.toByteArray());
    }


    public void send(WebSocket webSocket, String type, byte[] bytes) {
        this.send(webSocket, type, ByteString.copyFrom(bytes));
    }

    @Getter
    CachedHashMap<String, Consumer<HookData<WebSocket>>> hooks = new CachedHashMap<>();

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        int currentOnline = addOnlineCount();
        String info = webSocket.getRemoteSocketAddress().toString() +
                " connected. Online  " + (currentOnline - 1) + " => " + currentOnline;
        logger.info(info);
        if (useAuthCheck) {
            String session = "";
            String token = "";
            String path = clientHandshake.getResourceDescriptor();
            String[] path2 = path.split("\\?");
            if (path2.length == 2) {
                String[] path3 = path2[1].split("&");
                for (String param : path3) {
                    String[] kv = param.split("=");
                    if (kv.length == 2) {
                        if (kv[0].equals("session")) {
                            session = kv[1];
                        } else if (kv[0].equals("token")) {
                            token = kv[1];
                        }
                    }
                }
            }
            if (session == "" || token == "") {
                webSocket.close(4001, "Denied");
            } else if (!jwtRestService.verify(session, token)) {
                webSocket.close(4000, "Invalid token");
            }
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        int currentOnline = subOnlineCount();
        if (i == 1000) {
            s = "normal";
        }
        String info = webSocket.getRemoteSocketAddress().toString() +
                " disconnected. [" + i + "=" + s + "] Online " +
                (currentOnline + 1) + " => " + currentOnline;
        logger.info(info);
        CloseInfo ci = new CloseInfo(webSocket, i, s, b);
        serverConnectionCloseEvents.forEach(c -> {
            c.accept(ci);
        });
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        logger.info(s);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        IVDAData.Wrapper wrapper = decoder.decodeWrapper(message.array());
        Consumer<HookData<WebSocket>> consumer = hooks.get(wrapper.getType());
        if (consumer != null) {
            consumer.accept(new HookData<>(conn, wrapper.getData()));
        } else {
            logErrorMessage(new NoSuchHookException(wrapper.getType()));
        }
    }

    protected void logErrorMessage(Exception exception) {
        logger.error(exception.getMessage());
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }

    @Data
    @AllArgsConstructor
    public static class CloseInfo {
        WebSocket webSocket;
        int i;
        String s;
        boolean b;
    }
}