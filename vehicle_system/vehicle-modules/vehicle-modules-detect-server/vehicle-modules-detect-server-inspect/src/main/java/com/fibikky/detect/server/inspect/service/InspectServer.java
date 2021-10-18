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
    ProtobufWebSocketClient front;
    ProtobufWebSocketClient back;
    private final Set<WebSocket> lpr_consumers = new HashSet<>();
    private WebSocket lpr_provider;
    private final Set<WebSocket> front_consumers = new HashSet<>();
    private WebSocket front_provider;
    private final Set<WebSocket> back_consumers = new HashSet<>();
    private WebSocket back_provider;

    private final InspectSynchronizationContext inspectSynchronizationContext = new InspectSynchronizationContext();
    @Autowired
    private InspectCore inspectCore;

    public InspectServer(@Value("${service.server.address}") String address, @Value("${service.lpr.provider.address:}") String lprProviderAddress, @Value("${service.front.provider.address:}") String frontProviderAddress, @Value("${service.back.provider.address:}") String backProviderAddress) throws UnknownHostException {
        try {
            inspectSynchronizationContext.currentCore = inspectCore;
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
            if (!frontProviderAddress.equals("")) {
                logger.info(String.format("Connecting front provider at %s", frontProviderAddress));
                front = startClientConnection(front, frontProviderAddress);
                if (front != null) {
                    initFrontClientHook();
                    front.send("register_as_provider", new byte[0]);
                    logger.info(String.format("front provider at %s connected", lprProviderAddress));
                }
            }
            if (!backProviderAddress.equals("")) {
                logger.info(String.format("Connecting back provider at %s", backProviderAddress));
                back = startClientConnection(back, backProviderAddress);
                if (back != null) {
                    initBackClientHook();
                    back.send("register_as_provider", new byte[0]);
                    logger.info(String.format("back provider at %s connected", lprProviderAddress));
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
                if (ci.getWebSocket().equals(front_provider)) {
                    front_provider = null;
                } else if (front_consumers.contains(ci.getWebSocket())) {
                    front_consumers.remove(ci.getWebSocket());
                } else if (ci.getWebSocket().equals(back_provider)) {
                    back_provider = null;
                } else if (back_consumers.contains(ci.getWebSocket())) {
                    back_consumers.remove(ci.getWebSocket());
                } else if (ci.getWebSocket().equals(lpr_provider)) {
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
        hook.put("register_as_front_consumer", data -> {
            front_consumers.add(data.getSource());
        });
        hook.put("register_as_back_consumer", data -> {
            back_consumers.add(data.getSource());
        });
        hook.put("register_as_lpr_consumer", data -> {
            lpr_consumers.add(data.getSource());
        });
        hook.put("register_as_front_provider", data -> {
            front_provider = data.getSource();
        });
        hook.put("register_as_back_provider", data -> {
            back_provider = data.getSource();
        });
        hook.put("register_as_lpr_provider", data -> {
            lpr_provider = data.getSource();
        });
    }

    public void initBackClientHook() {
        CachedHashMap<String, Consumer<HookData<ProtobufWebSocketClient>>> hook = back.getHooks();
        hook.put("detected_img_frame", backDetectedImgFrameHook);
    }

    public void initFrontClientHook() {
        CachedHashMap<String, Consumer<HookData<ProtobufWebSocketClient>>> hook = front.getHooks();
        hook.put("detected_img_frame", frontDetectedImgFrameHook);

    }

    public void initLprClientHook() {
        CachedHashMap<String, Consumer<HookData<ProtobufWebSocketClient>>> hook = lpr.getHooks();
        hook.put("detected_lp_frame", lprDetectedImgFrameHook);
    }

    Consumer<HookData<ProtobufWebSocketClient>> backDetectedImgFrameHook = (hookData) -> {
        inspectSynchronizationContext.updateBehind(new BehindFrameRaw(hookData.getData()));
        back_consumers.forEach(consumer -> {
            server.send(consumer, "detected_img_frame", hookData.getData());
        });
    };
    Consumer<HookData<ProtobufWebSocketClient>> frontDetectedImgFrameHook = (hookData) -> {
        inspectSynchronizationContext.updateFront(new FrontFrameRaw(hookData.getData()));
        front_consumers.forEach(consumer -> {
            server.send(consumer, "detected_img_frame", hookData.getData());
        });
    };
    Consumer<HookData<ProtobufWebSocketClient>> lprDetectedImgFrameHook = (hookData) -> {
        inspectSynchronizationContext.updateMonitor(new LprFrameRaw(hookData.getData()));
        lpr_consumers.forEach(consumer -> {
            server.send(consumer, "detected_lp_frame", hookData.getData());
        });
    };
    Consumer<HookData<WebSocket>> serverImgFrameHook = (hookData) -> {
        if (hookData.getSource().equals(front_provider)) {
            front.send("img_frame", hookData.getData());
        } else if (hookData.getSource().equals(back_provider)) {
            back.send("img_frame", hookData.getData());
        }
    };
}
