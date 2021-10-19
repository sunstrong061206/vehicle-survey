package com.fibikky.detect.server.inspect.service;

import com.fibikky.detect.server.inspect.service.inspectContext.BehindFrameRaw;
import com.fibikky.detect.server.inspect.service.inspectContext.FrontFrameRaw;
import com.fibikky.detect.server.inspect.service.inspectContext.InspectSynchronizationContext;
import com.fibikky.detect.server.inspect.service.inspectContext.LprFrameRaw;
import com.fibikky.vehicle.network.util.HookData;
import com.fibikky.vehicle.network.util.maps.CachedHashMap;
import com.fibikky.vehicle.network.websockets.ProtobufWebSocketClient;
import com.fibikky.vehicle.network.websockets.ProtobufWebSocketServer;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Aminor_z
 */
@Service
public class InspectServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // 处理连接服务端请求和视频流输入
    ProtobufWebSocketServer server;
    // 连接至检测提供端
    ProtobufWebSocketClient lpr;
    private final Set<WebSocket> lpr_consumers = new HashSet<>();
    private WebSocket lpr_provider;

    public InspectServer(@Value("${service.server.address}") String address, @Value("${service.lpr.provider.address:}") String lprProviderAddress) throws UnknownHostException {
        try {
            String[] addressArray = address.split(":");
            String ip = addressArray[0];
            int port = Integer.parseInt(addressArray[1]);
            logger.info(String.format("Start Inspect Server at %s:%d", ip, port));
            server = new ProtobufWebSocketServer(ip, port);
            initServerConfig();
            server.setUseAuthCheck(false);
            initServerHook();
            server.start();
            if (!lprProviderAddress.equals("")) {
                logger.info(String.format("Connecting lpr provider at %s", lprProviderAddress));
                lpr = startClientConnection(lpr, lprProviderAddress);
                if (lpr != null) {
                    initLprClientHook();
                    lpr.send("register_as_provider", new byte[0]);
                    logger.info(String.format("lpr provider at %s connected", lprProviderAddress));
                }
            }

            logger.info(String.format("Inspect Server serving at %s:%d", ip, port));
        } catch (Exception exception) {
            throw exception;
        }
    }

    public void initServerConfig() {
        server.getServerConnectionCloseEvents().add(ci -> {
            try {
                if (ci.getWebSocket().equals(lpr_provider)) {
                    lpr_provider = null;
                } else if (lpr_consumers.contains(ci.getWebSocket())) {
                    lpr_consumers.remove(ci.getWebSocket());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        });
    }

    public ProtobufWebSocketClient startClientConnection(ProtobufWebSocketClient client, String address) {
        if (client != null) {
            client.close();
            client = null;
        }
        client = new ProtobufWebSocketClient(address);
        client.connect();
        return client;
    }

    public void initServerHook() {
        CachedHashMap<String, Consumer<HookData<WebSocket>>> hook = server.getHooks();
        hook.put("img_frame", serverImgFrameHook);
        hook.put("register_as_lpr_consumer", data -> {
            lpr_consumers.add(data.getSource());
        });
        hook.put("register_as_lpr_provider", data -> {
            lpr_provider = data.getSource();
        });
    }

    public void initLprClientHook() {
        CachedHashMap<String, Consumer<HookData<ProtobufWebSocketClient>>> hook = lpr.getHooks();
        hook.put("detected_lp_frame", lprDetectedImgFrameHook);
    }

    Consumer<HookData<ProtobufWebSocketClient>> lprDetectedImgFrameHook = (hookData) -> {
        lpr_consumers.forEach(consumer -> {
            server.send(consumer, "detected_lp_frame", hookData.getData());
        });
    };
    Consumer<HookData<WebSocket>> serverImgFrameHook = (hookData) -> {
        lpr.send("img_frame", hookData.getData());
    };
}
