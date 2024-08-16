package com.nsy.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/send")
    public String sendMessage() {
        // 主动向前端发送消息
        messagingTemplate.convertAndSend("/topic/messages", "来自服务器的消息");
        return "消息已发送";
    }
}
