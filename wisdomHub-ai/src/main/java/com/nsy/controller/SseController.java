package com.nsy.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;  
  
import java.io.IOException;  
  
@RestController
@CrossOrigin
public class SseController {  
  
    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)  
    public ResponseEntity<SseEmitter> streamEvents() {  
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Long.MAX_VALUE is the timeout  
  
        // 使用线程或异步任务来发送数据  
        new Thread(() -> {  
            try {  
                for (int i = 0; i < 10; i++) { // 发送10条消息作为示例  
                    emitter.send(SseEmitter.event().name("message").data("Hello SSE " + i));  
                    Thread.sleep(1000); // 每秒发送一次  
                }  
                emitter.complete(); // 完成发送  
            } catch (Exception e) {  
                emitter.completeWithError(e); // 发送错误  
            }  
        }).start();  
  
        return ResponseEntity.ok().body(emitter);  
    }  
}