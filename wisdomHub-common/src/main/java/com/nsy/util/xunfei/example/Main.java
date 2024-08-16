package com.nsy.util.xunfei.example;


import com.nsy.util.xunfei.example.dto.UploadResp;
import com.nsy.util.xunfei.example.util.ChatDocUtil;

/**
 * Test
 * 详细接口文档请查看 <a href="https://chatdoc.xfyun.cn/docs">文档知识库API</a>
 *
 * @author ydwang16
 * @version 2023/09/06 13:59
 **/
public class Main {
    private static final String uploadUrl = "https://chatdoc.xfyun.cn/openapi/v1/file/upload";
    private static final String fileStatusUrl = "https://chatdoc.xfyun.cn/openapi/v1/file/status";
    private static final String chatUrl = "wss://chatdoc.xfyun.cn/openapi/chat";
    private static final String appId = "061a30a7";
    private static final String secret = "MmQ2OWM5OTlhNGVlOTUwODc1MTRjZmQz";

//    public static void main(String[] args) {
//        ChatDocUtil chatDocUtil = new ChatDocUtil();
////        // 1、上传
//        UploadResp uploadResp = chatDocUtil.upload("D:\\test.txt", uploadUrl, appId, secret);
//
//        System.out.println(uploadResp);
//        System.out.println("请求sid=" + uploadResp.getSid());
//        System.out.println("文件id=" + uploadResp.getData().getFileId());
//
//        // 2、问答，上传文件状态为vectored时才可以问答，文件状态可以调用【文档状态查询】接口查询
//        String fileId = "3b8715e6e2514c2eaf552acda31f4667";
//        String question = "故事一讲了什么内容";
//        chatDocUtil.chat(chatUrl, fileId, question, appId, secret);
//    }
    public static String getRequestUrl(){
        ChatDocUtil chatDocUtil = new ChatDocUtil();
        return chatDocUtil.getRequestUrl(chatUrl, appId, secret);
    }
}