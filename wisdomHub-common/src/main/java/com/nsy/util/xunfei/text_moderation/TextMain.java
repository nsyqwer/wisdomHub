package com.nsy.util.xunfei.text_moderation;

import com.google.gson.Gson;
import com.nsy.util.xunfei.text_moderation.utils.MyUtil;
import com.nsy.util.xunfei.text_moderation.vo.Category;
import com.nsy.util.xunfei.text_moderation.vo.Detail;
import com.nsy.util.xunfei.text_moderation.vo.Response;
import com.nsy.util.xunfei.text_moderation.vo.Result;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 1、文本内容合规审核接口
 * 2、appid与secret信息请在控制台获取 https://console.xfyun.cn/services/text_audit
 */
@Slf4j
public class TextMain {
    private static String url = "https://audit.iflyaisol.com//audit/v2/syncText";
    private static final String APPID = Constants.APPID;
    private static final String APISecret = Constants.APISecret;
    private static final String APIKey = Constants.APIKey;

    private static final String content = "好蠢，傻逼，草你妈";// 送检文本

    // 词库指定
    private static final String lib_ids_1 = "35481c2186a5411f97a5bb79d9cae696"; // 根据自己创建获取词库ID  黑名单
    private static final String lib_ids_2 = ""; // 根据自己创建获取词库ID  白名单

    public static void main(String[] args) throws Exception {
        /**
         * 业务参数
         * --- 如果需要使用黑白名单资源，放开lib_ids与categories参数
         * */
        String json = "{\n" +
                "  \"is_match_all\": 1,\n" +
                "  \"content\": \"" + content + "\",\n" + // 放开lib_ids与categories参数，注意在content后面加逗号使之成为合法json
                "  \"lib_ids\": [\n" +
                "    \"" + lib_ids_1 + "\"\n" +
//                "    \"" + lib_ids_2 + "\"\n" +
                "  ],\n" +
                "  \"categories\": [\n" +
                "    \"pornDetection\",\n" +
                "    \"violentTerrorism\",\n" +
                "    \"political\",\n" +
                "    \"lowQualityIrrigation\",\n" +
                "    \"contraband\",\n" +
                "    \"advertisement\",\n" +
                "    \"uncivilizedLanguage\"\n" +
                "  ]\n" +
                "}";
        System.out.println(json);
        // 获取鉴权
        Map<String, String> urlParams = MyUtil.getAuth(APPID, APIKey, APISecret);
        // 发起请求
        String returnResult = MyUtil.doPostJson(url, urlParams, json);

        Gson gson = new Gson();
        Response response = gson.fromJson(returnResult, Response.class);
        System.out.println("文本合规返回结果：\n" + returnResult);
        System.out.println(response);
    }

    /**
     * 获取文本审核结果
     **/
    public static Response checkText(String text) throws Exception {
        System.out.println("text: " + text);
        /**
         * 业务参数
         * --- 如果需要使用黑白名单资源，放开lib_ids与categories参数
         * */
        String json = "{\n" +
                "  \"is_match_all\": 1,\n" +
                "  \"content\": \"" + text + "\",\n" + // 放开lib_ids与categories参数，注意在content后面加逗号使之成为合法json
                "  \"lib_ids\": [\n" +
                "    \"" + lib_ids_1 + "\"\n" +
//                "    \"" + lib_ids_2 + "\"\n" +
                "  ],\n" +
                "  \"categories\": [\n" +
                "    \"pornDetection\",\n" +
                "    \"violentTerrorism\",\n" +
                "    \"political\",\n" +
                "    \"lowQualityIrrigation\",\n" +
                "    \"contraband\",\n" +
                "    \"advertisement\",\n" +
                "    \"uncivilizedLanguage\"\n" +
                "  ]\n" +
                "}";
//        System.out.println(json);
        // 获取鉴权
        Map<String, String> urlParams = MyUtil.getAuth(APPID, APIKey, APISecret);
        // 发起请求
        String returnResult = MyUtil.doPostJson(url, urlParams, json);

        System.out.println(returnResult);

        Gson gson = new Gson();
        Response response = gson.fromJson(returnResult, Response.class);
        System.out.println(response);

        return response;
    }

    public static List<String> getViolations(String text) throws Exception {
        List<String> violations = new ArrayList<>();

        Response response = checkText(text);
        if(response == null || response.getData() == null) {
            log.error("文本合规模型到期");
            return null;
        }
        Result result = checkText(text).getData().getResult();

        System.out.println("返回的东西");

        if(result.getSuggest().equals("block")){
            Detail detail = result.getDetail();
            List<Category> categoryList = detail.getCategory_list();
            for(Category category : categoryList){
                String violation = category.getCategory_description();
                if(category.getWord_list() != null){
                    violation += " : " + category.getWord_list().toString();
                }
                violations.add(violation);
            }
            return violations;
        }
        return null;
    }
}
