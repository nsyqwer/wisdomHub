//package com.nsy.controller;
//
//import cn.hutool.core.util.StrUtil;
//
//import com.nsy.component.XfXhStreamClient;
//import com.nsy.config.XfXhConfig;
//import com.nsy.dto.MsgDTO;
//import com.nsy.listener.XfXhWebSocketListener;
//import com.nsy.service.XfxhService;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.WebSocket;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.Collections;
//import java.util.UUID;
//
//
//@RestController
//@RequestMapping("/test")
//@Slf4j
//public class TestController {
//
//    @Resource
//    private XfXhStreamClient xfXhStreamClient;
//
//    @Resource
//    private XfXhConfig xfXhConfig;
//
//
//    @Autowired
//    private XfxhService xfxhService;
//    /**
//     * 发送问题
//     *
//     * @param question 问题
//     * @return 星火大模型的回答
//     */
//    @GetMapping("/sendQuestion")
//    public String sendQuestion(@RequestParam("question") String question) {
//        return xfxhService.sendQuestion(question,1);
//    }
//}