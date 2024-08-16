package com.nsy.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.nsy.component.XfXhStreamClient;
import com.nsy.config.XfXhConfig;
import com.nsy.dto.MsgDTO;
import com.nsy.listener.XfXhWebSocketListener;
import com.nsy.util.ApiAuthAlgorithm;
import com.nsy.util.ApiClient;
import com.nsy.util.CreateResponse;
import com.nsy.util.ProgressResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

/**
 * @className: XfxhService
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/9 10:21
 */
@Service
@Slf4j
public class XfxhService {

    @Resource
    private XfXhStreamClient xfXhStreamClient;

    @Resource
    private XfXhConfig xfXhConfig;

//    @Resource
//    private MessagingService messagingService;


    private String knowledgeGraphPrompt=" 你是一个聪明的教学知识图谱生成助手，你需要根据我的输入内容用JSON格式返回（不要包含任何除了json格式的以外的内容)，" +
            " 其中你的回答格式为[{\"node_1\":\"知识点1\",\"edge\":\"节点关系（前置，后置，关联）\",\"node_2\":\"知识点2\"},...],这个数组里面每个对象，" +
            "每个节点都只能是知识点名称，每2个知识点之间的节点关系edge只能是（前置，后置，关联）这三种关系之一，你需要保证每个对象都包" +
            "含node_1,edge,node_2三个键，特别注意的是你要结合学科知识判断每个名词是否是知识点名称，因为每个节点都必须是知识点名称，否则没意义，" +
            "并且你的输出千万不要有json数组以外的部分，不要写```json```,因为我直接对你的回答进行json转对象数组，其中我的输入是:";

    private String mindMapPrompt="请使用markdown格式帮我制作一份导图,只能是markdown格式,其中我的输入是:";

    private String correctPrompt="现在你作为教师阅卷助手，我的输入会是这个格式的json：{\n" +
            "  \"questionScore\": null,\n" +
            "  \"studentScore\": null,\n" +
            "  \"type\": null,\n" +
            "  \"title\": null,\n" +
            "  \"studentAnswer\": null,\n" +
            "  \"questionComment\": null,\n" +
            "  \"answer\": null,\n" +
            "  \"answerAnalysis\": null\n" +
            "}\n ,这个json中questionScore根据学生具体作答判断:如果studentAnswer与answer一致，就得到满分，即questionScore,否则为0分，如果不是简答题就按照语义匹配度来打分,将分数填在studentScore属性的值里面，" +
            "并且根据学生作答studentAnswer结合答案解析answerAnalysis或者答案answer给出评语(学生这道题为什么做错了),填在questionComment里面，且大于20字不超过100字，除了studentScore和questionComment外，其他所有字段和我接下来的输入的json一致，返回给我json,这是我的输入：";


    private String createQuestionPrompt="你是一个资深出题老师，你会根据给出的材料和难度系数（0-10）以及要求出题数量给出题目正确答案以及题目分析严格按照以下格式输出：\n" +
            "    选择题\n" +
            "\n" +
            "    1.xxx\n" +
            "    A.xxx\n" +
            "    B.xxx\n" +
            "    C.xxx\n" +
            "    D.xxx\n" +
            "    答案：xxx\n" +
            "    解析：xxx\n" +
            "\n" +
            "    填空题\n" +
            "    1.xxx\n" +
            "    答案：xxx\n" +
            "    解析：xxx\n" +
            "\n" +
            "    问答题\n" +
            "    1.xxx\n" +
            "    答案：xxx\n" +
            "    解析：xxx\n" +
            "    除要求外不要有多余输出，除此之外下面是材料以及难度和各种类型的出题数量";


    private String createPathPrompt ="为这个学生进行个性化学习路径推荐，他在多线程与并发有3道错题，包含了知识点线程同步和并发集合，我需要你为该学生进行个性化学习路径推荐，使用markdown格式回答，除了markdown格式不要有其他额外的输出";

    private String recommendQuestionPrompt="，这是Question类的所有字段和注释，\n" +
            "id;主键id\n" +
            "String type;题目类型（只能单选题 多选题 填空题 简答题 判断题其中之一）\n" +
            "String title;题干\n" +
            "String answer;答案\n" +
            "String answerAnalysis;答案解析\n" +
            "Integer courseId;课程id\n" +
            "String courseName;课程名字\n" +
            "Integer creatorId;创建者id\n" +
            "String creatorName;创建者名字\n" +
            "" +
            "" +
            "我会给你一个List<Question>转成json的字符串，这个json字符串里面有3道题目，都是我学习过程中产生的错题，你需要根据这3道题目为我生成3道知识点类似的题目，" +
            "让我能掌握这些知识点。type,title,answer,answerAnalysis都要你自己生成，你要根据我的输入的题目格式，自己生成这3道题目，而不是直接把我给你的3到题目返回给我" +
            "其他的字段（courseId，courseName，creatorId，creatorName）都和我传给你的第一道题目一致即可。,你的回答是Json格式，但是一定要注意你的回答一定不要用(```json```)把Json包围起来，因为那样我会报错，不要有其他格式的输出，你的输出以[开始，以]结束";


    //要注意 title这个字段，必须要是这个格式{"text":"计算机的CPU主要由以下哪些部分组成？","options":"[\"<p>1</p>\",\"<p>2</p>\",\"<p>3</p>\",\"<p>4</p>\"]"}
    //即必须是json格式，且包含”text(题干)“和options(选项)这2个字段，不管是不是选择题这个title字段都要有这2个字段，并且是json格式。
    public  String sendQuestion(String question,int type){
        // 如果是无效字符串，则不对大模型进行请求
        if (StrUtil.isBlank(question)) {

            return "无效问题，请重新输入";
        }
        // 获取连接令牌
        if (!xfXhStreamClient.operateToken(XfXhStreamClient.GET_TOKEN_STATUS)) {
            return "当前大模型连接数过多，请稍后再试";
        }
// 创建消息对象
        MsgDTO msgDTO=new MsgDTO();
        if(type==1){
            // 知识图谱
             msgDTO = MsgDTO.createUserMsg(knowledgeGraphPrompt+question);
        }else if(type==2){
            //思维导图
            msgDTO = MsgDTO.createUserMsg(mindMapPrompt+question);
        }else if(type==3){
            //阅卷
            msgDTO = MsgDTO.createUserMsg(correctPrompt+question);
        }else if(type==4){
            //AI出题
            msgDTO = MsgDTO.createUserMsg(createQuestionPrompt+question);
        }else if(type==5){
            //个性化学习路径推荐
            msgDTO = MsgDTO.createUserMsg(createPathPrompt+question);
        }else if (type==6){
            //错题推荐
            msgDTO =MsgDTO.createUserMsg(recommendQuestionPrompt+question);
        }

        // 创建监听器
        XfXhWebSocketListener listener = new XfXhWebSocketListener();
        // 发送问题给大模型，生成 websocket 连接
        WebSocket webSocket = xfXhStreamClient.sendMsg(UUID.randomUUID().toString().substring(0, 10), Collections.singletonList(msgDTO), listener);

        if (webSocket == null) {
            // 归还令牌
            xfXhStreamClient.operateToken(XfXhStreamClient.BACK_TOKEN_STATUS);
            return "系统内部错误，请联系管理员";
        }



        try {
            int count = 0;
            // 为了避免死循环，设置循环次数来定义超时时长
            int maxCount = xfXhConfig.getMaxResponseTime() * 5;
            while (count <= maxCount) {
                Thread.sleep(200);
                if (listener.isWsCloseFlag()) {
                    break;
                }
                count++;
                System.out.println(count+clean(listener.getAnswer().toString()));
               // messagingService.sendMessage(clean(listener.getAnswer().toString()));


            }
            if (count > maxCount) {
                return "大模型响应超时，请联系管理员";
            }
            // 响应大模型的答案
            //这里是最后一次的输出
            System.out.println("非流式输出"+listener.getAnswer().toString());
            //messagingService.sendMessage(clean(listener.getAnswer().toString()));
            String answerString = cleanMarkdown(listener.getAnswer().toString());

            return answerString;
        } catch (InterruptedException e) {
            log.error("错误：" + e.getMessage());
            return "系统内部错误，请联系管理员";
        } finally {
            // 关闭 websocket 连接
            webSocket.close(1000, "");
            // 归还令牌
            xfXhStreamClient.operateToken(XfXhStreamClient.BACK_TOKEN_STATUS);
        }

    }


    public String createPPT(String query) throws IOException, InterruptedException {
        // 输入个人appId
        String appId = "d4e89d02";
        String secret = "OTRiMmZiOWM5ODMyZDNlNGEyOGZiNmQy";
        long timestamp = System.currentTimeMillis()/1000;
        String ts = String.valueOf(timestamp);
        // 获得鉴权信息
        ApiAuthAlgorithm auth = new ApiAuthAlgorithm();
        String signature = auth.getSignature(appId, secret, timestamp);

        // 建立链接
        ApiClient client = new ApiClient("https://zwapi.xfyun.cn");

        // 查询PPT模板信息
        //String templateResult = client.getTemplateList(appId, ts, signature);


        // 发送生成PPT请求
        String resp = client.createPPT(appId, ts, signature,query,"教师助手");
        System.out.println(resp);
        CreateResponse response = JSON.parseObject(resp, CreateResponse.class);

        // 利用sid查询PPT生成进度
        int progress = 0;
        ProgressResponse progressResponse =new ProgressResponse();
        while (progress < 100) {
            String progressResult = client.checkProgress(appId, ts, signature, response.getData().getSid());
            progressResponse = JSON.parseObject(progressResult, ProgressResponse.class);
            progress = progressResponse.getData().getProcess();

            if (progress < 100) {
                Thread.sleep(5000); // 暂停2秒
            }
        }

        System.out.println("ppt下载URL"+ progressResponse.getData().getPptUrl());

        return progressResponse.getData().getPptUrl();
    }


    public static String clean(String input) {
        if (input.startsWith("```markdown")) {
            input =input.substring("```markdown".length());
        }else if(input.startsWith("```json")){
            input =input.substring("```json".length());
        } else if (input.startsWith("```")){
           input= input.substring("```".length());
        }

        if (input.endsWith("```")) {
            // 移除结尾的"```"
            // 注意：substring(0, length - 3) 是因为我们要去掉最后三个字符
            input =input.substring(0, input.length() - 3);
        }

        return input;
    }

    public static String cleanMarkdown(String input) {
        // 检查字符串是否以```markdown开头和结尾
        if (input.startsWith("```markdown") && input.endsWith("```")) {
            // 移除开头的```markdown和结尾的```
            // 使用substring方法，跳过开头的长度，并且避免包含结尾的长度
            int startIndex = "```markdown".length();
            int endIndex = input.lastIndexOf("```");
            return input.substring(startIndex, endIndex);
        }
        if(input.startsWith("```json") && input.endsWith("```")){
            // 移除开头的```markdown和结尾的```
            // 使用substring方法，跳过开头的长度，并且避免包含结尾的长度
            int startIndex = "```json".length();
            int endIndex = input.lastIndexOf("```");
            return input.substring(startIndex, endIndex);
        }
        if(input.startsWith("```") && input.endsWith("```")){
            // 移除开头的```markdown和结尾的```
            // 使用substring方法，跳过开头的长度，并且避免包含结尾的长度
            int startIndex = "```".length();
            int endIndex = input.lastIndexOf("```");
            return input.substring(startIndex, endIndex);
        }
        // 如果字符串不符合预期格式，直接返回原字符串或进行其他处理
        return input;
    }






}
