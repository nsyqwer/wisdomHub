//package com.nsy.service;
//
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessagingService {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public MessagingService(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    public void sendMessage(String message) {
//        messagingTemplate.convertAndSend("/topic/messages", message);
//    }
//}
