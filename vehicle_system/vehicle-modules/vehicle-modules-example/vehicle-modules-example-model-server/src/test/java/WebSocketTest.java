import com.fibikky.vehicle.common.log.LogUtil;
import com.fibikky.vehicle.example.model.server.provider.ExampleWebSocketApplication;
import com.fibikky.vehicle.network.util.maps.CachedHashMap;
import com.fibikky.vehicle.network.websockets.ProtobufWebSocketClient;
import com.fibikky.vehicle.network.websockets.ProtobufWebSocketServer;
import com.google.protobuf.ByteString;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.UnknownHostException;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleWebSocketApplication.class)
@SpringBootConfiguration
public class WebSocketTest {
    @org.junit.Test
    public void serverTest() throws UnknownHostException, InterruptedException {
        ProtobufWebSocketServer server = new ProtobufWebSocketServer("127.0.0.1", 9000);
        CachedHashMap<String, Consumer<ByteString>> hooks = new CachedHashMap<>();
        hooks.put("hello", bytes -> System.out.println(bytes.toString()));
        server.start();
        System.out.println("Server is running...");
        LogUtil.info(this, "Server is running...");
        //测试时阻止程序结束退出
        synchronized (this) {
            this.wait();
        }
    }

    @org.junit.Test
    public void clientTest() throws UnknownHostException, InterruptedException {
        ProtobufWebSocketClient client = new ProtobufWebSocketClient("ws://127.0.0.1:50003");
        CachedHashMap<String, Consumer<ByteString>> hooks = new CachedHashMap<>();
        hooks.put("hello", bytes -> System.out.println(bytes.toString()));
        client.connect();
        System.out.println("Client is running...");
        LogUtil.info(this, "Client is running...");
        //测试时阻止程序结束退出
        synchronized (this) {
            this.wait();
        }
    }
}