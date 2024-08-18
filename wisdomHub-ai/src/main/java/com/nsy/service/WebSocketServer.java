package com.nsy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端。
 */
@Component
@Slf4j
@Service
@ServerEndpoint("/apk-info/websocket/{sid}")
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid = "";

    //为每一个用户维护一个消息队列
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, BlockingQueue<String>> messageQueueMap = new ConcurrentHashMap<>();
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     // 加入set中
        this.sid = sid;
        addOnlineCount();           // 在线数加1
        try {
            sendMessage("连接建立成功");
            System.out.println("有新客户端开始监听,sid=" + sid + ",当前在线人数为:" + getOnlineCount());
            webSocketMap.put(sid, this);     // 将当前对象加入到webSocketMap中
            messageQueueMap.put(sid, new LinkedBlockingQueue<>()); // 初始化消息队列
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  // 从set中删除
        subOnlineCount();              // 在线数减1
        // 断开连接情况下，更新主板占用情况为释放
        System.out.println("释放的sid=" + sid + "的客户端");
        releaseResource();
    }

    private void releaseResource() {
        // 这里写释放资源和要处理的业务
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @Param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

//        BlockingQueue<String> messageQueue = messageQueueMap.get(sid);
//
//        //判断消息是不是ai问答
//        if (messageQueue != null&&message.substring(2).equals("ai")) {
//            System.out.println("收到来自客户端 sid=" + sid + " 的信息:" + message.substring(2));
//            messageQueue.offer(message); // 将消息加入队列
//           // queryWenxin();
//        }
//        else {
//            System.out.println("收到来自客户端 sid=" + sid + " 的信息:" + message);
//            // 群发消息
//            HashSet<String> sids = new HashSet<>();
//            for (WebSocketServer item : webSocketSet) {
//                sids.add(item.sid);
//            }
//            try {
//                sendMessage("客户端 " + this.sid + "发布消息：" + message, sids);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 发生错误回调
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println(session.getBasicRemote() + "客户端发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     */
    public static void sendMessage(String message, HashSet<String> toSids) throws IOException {
        System.out.println("推送消息到客户端 " + toSids + "，推送内容:" + message);

        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给传入的sid，为null则全部推送
                if (toSids.size() <= 0) {
                    item.sendMessage(message);
                } else if (toSids.contains(item.sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }


    /**
     * 单独发送自定义消息
     */
    public static void sendSingleMessage(String message, String toSid) throws IOException {
        System.out.println("推送消息到客户端 " + toSid + "，推送内容:" + message);

        for (WebSocketServer item : webSocketSet) {
            try {
                // 这里可以设定只推送给传入的sid
                if (toSid != null && toSid.equals(item.sid)) {
                    item.sendMessage(message);
                    break; // 找到目标客户端后退出循环
                }
            } catch (IOException e) {
                continue;
            }
        }
    }


//    public static void sendSingleMessageByResult(Result message, String toSid) throws IOException {
//        System.out.println("推送消息到客户端 " + toSid + "，推送内容:" + message);
//
//        for (WebSocketServer item : webSocketSet) {
//            try {
//                // 这里可以设定只推送给传入的sid
//                if (toSid != null && toSid.equals(item.sid)) {
//                    item.sendMessageByResult(message);
//                    break; // 找到目标客户端后退出循环
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }
//    }




    /**
     * 实现服务器主动推送消息到 指定客户端
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


//    public void sendMessageByResult(Result message) throws IOException {
//        ObjectMapper objectMapper=new ObjectMapper();
//        this.session.getBasicRemote().sendText(objectMapper.writeValueAsString(message));
//    }





    /**
     * 获取当前在线人数
     *
     * @return
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 当前在线人数 +1
     *
     * @return
     */
    public static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    /**
     * 当前在线人数 -1
     *
     * @return
     */
    public static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }

    /**
     * 获取当前在线客户端对应的WebSocket对象
     *
     * @return
     */
    public static CopyOnWriteArraySet<WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }


    private static String AccessKey="ALTAK2clQqDcmcN5V6TVS6aoPN";

    private static String SecretKey="f1175e8e5c044a7c8edb4658f7a7a31c";

    // 使用安全认证AK/SK鉴权参数，安全认证Access Key替换your_iam_ak，Secret Key替换your_iam_sk
//    static Qianfan qianfan = new Qianfan(AccessKey ,SecretKey);

//    public void queryWenxin(){
//        BlockingQueue<String> messageQueue = messageQueueMap.get(sid);
//       qianfan.chatCompletion().model("ERNIE-4.0-8K");
//       for(String message:messageQueue){
//           qianfan.chatCompletion().addMessage("user",message);
//       }
//       Result result=new Result(200,"ai");
//
//       qianfan.chatCompletion().executeStream()
//                .forEachRemaining(chunk -> {
//                    result.setData(chunk.getResult());
//                    try {
//                        sendSingleMessageByResult(result,sid);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//    }

}


