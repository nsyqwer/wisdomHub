package com.nsy.util.xunfei.face;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nsy.model.pojo.Student;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

public class WebFaceBaidu {

    private static final OkHttpClient client = new OkHttpClient();
    public static final String API_KEY = "EZnsTyDgex36WQD4sz1sLOPc";
    public static final String SECRET_KEY = "5byjjs2mQLmmGHQAIX2Ee0u0FMCnMJwn";
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    /**
     * 创建用户组
     * @author 文旅航
     * @date 2024/8/8 20:52
     * @param groupId 用户组id
    **/

    public static void addGroup(String groupId) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("group_id", groupId);

        Gson gson = new Gson();
        String data = gson.toJson(jsonObject);

        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/add?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());
    }

    /**
     * 组内添加用户，人脸注册
     * @author 文旅航
     * @date 2024/8/8 20:51
     * @param groupId 组id
     * @param imagePath 用户人脸
     * @param userId 用户id
     * @param userInfo 用户信息
    **/

    public static void addUser(String groupId, String imagePath, String userId, String userInfo) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("group_id", groupId);
        jsonObject.addProperty("image", getFileContentAsBase64(imagePath));
        jsonObject.addProperty("image_type", "BASE64");
        jsonObject.addProperty("user_id", userId);
        jsonObject.addProperty("user_info", userInfo);
        jsonObject.addProperty("action_type", "REPLACE");

        Gson gson = new Gson();
        String data = gson.toJson(jsonObject);

//        System.out.println(data);

        // image 可以通过 getFileContentAsBase64("C:\fakepath\image1.jpg") 方法获取,如果Content-Type是application/x-www-form-urlencoded时,第二个参数传true
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        System.out.println(response.body().string());
    }

    /**
     * 人脸搜索 1:N识别
     * @author 文旅航
     * @date 2024/8/8 20:58 
     * @param imagePath 人脸地址
     * @param groupId 用户组id
    **/
    
    public static Student getUserByGroupId(String imagePath, String groupId) throws IOException, InterruptedException {
        MediaType mediaType = MediaType.parse("application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("group_id_list", groupId);
        jsonObject.addProperty("image", getFileContentAsBase64(imagePath));
        jsonObject.addProperty("image_type", "BASE64");
        jsonObject.addProperty("match_threshold", 60);

        Gson gson = new Gson();
        String data = gson.toJson(jsonObject);

        long start = System.nanoTime();
//        Integer userId = getUserByGroupId(imagePath, "6");

        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/search?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();

        long end = System.nanoTime();

        System.out.println("运行时长：" + ((end - start) / 1000000000.0));

        // 解析 JSON 字符串
        JSONObject json = new JSONObject(response.body().string());

        System.out.println(json);

        int errorCode = json.getInt("error_code");

        if(errorCode == 18){
            Thread.sleep(100);
            return getUserByGroupId(imagePath, groupId);
        }
        else if(errorCode == 222202 || errorCode == 222207){
            System.out.println("无该人脸照片");
            return null;
        }

        JSONObject result = json.getJSONObject("result");

        JSONArray userList = result.getJSONArray("user_list");
        JSONObject user = userList.getJSONObject(0);

        System.out.println(json);

        Student student = new Student();
        student.setId(Integer.valueOf(user.getString("user_id")));
        student.setName(user.getString("user_info"));

        return student;
    }

    /**
     * 人脸搜索：N:M识别
     * @author 文旅航
     * @date 2024/8/15 10:52
     * @param imagePath 9个人脸合照
     * @param groupId 组id
     * @return java.util.Set<java.lang.Integer>
    **/

    public static Set<Integer> getUsersByGroupId(String imagePath, String groupId) throws IOException, InterruptedException {
        MediaType mediaType = MediaType.parse("application/json");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("group_id_list", groupId);
        jsonObject.addProperty("image", getFileContentAsBase64(imagePath));
        jsonObject.addProperty("image_type", "BASE64");
        jsonObject.addProperty("max_face_num", 9);
        jsonObject.addProperty("match_threshold", 50);
        jsonObject.addProperty("max_user_num", 9);

        Gson gson = new Gson();
        String data = gson.toJson(jsonObject);

        // image 可以通过 getFileContentAsBase64("C:\fakepath\class7.jpg") 方法获取,如果Content-Type是application/x-www-form-urlencoded时,第二个参数传true
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/face/v3/multi-search?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();

        JSONObject json = new JSONObject(response.body().string());

        if(json.getInt("error_code") != 0){
            Thread.sleep(1000);
            return getUsersByGroupId(imagePath, groupId);
        }
        System.out.println(json);

        Set<Integer> userIds = new HashSet<>();
        JSONArray jsonArray = json.getJSONObject("result").getJSONArray("face_list");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject user = new JSONObject();
            try {
                user = jsonArray.getJSONObject(i).getJSONArray("user_list").getJSONObject(0);
            }
            catch (Exception e){
                System.out.println(e);
                continue;
            }
            System.out.println(MessageFormat.format("{0} : {1}, score:{2}", user.getString("user_id"), user.getString("user_info"), user.getDouble("score")));

            userIds.add(Integer.valueOf(user.getString("user_id")));
        }

        return userIds;
    }

    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }

    /**
     * 获取文件base64编码
     *
     * @param filePath      文件路径
//     * @param urlEncode 如果Content-Type是application/x-www-form-urlencoded时,传true
     * @return base64编码信息，不带文件头
     * @throws IOException IO异常
     */
    static String getFileContentAsBase64(String filePath) throws IOException {

        if(filePath.contains("http")) {
            String path = "image.jpg";
            downloadFile(filePath, path);
            filePath = path;
        }

        byte[] b = Files.readAllBytes(Paths.get(filePath));
        String base64 = Base64.getEncoder().encodeToString(b);
//        base64 = URLEncoder.encode(base64, "utf-8");
        return base64;
    }

    /**
     * 将云端文件存储到本地
     * @author 文旅航
     * @date 2024/8/8 19:35
     * @param cloudUrl 云端文件地址
     * @param localFilePath 存储地址
    **/

    public static void downloadFile(String cloudUrl, String localFilePath) throws IOException {

        URL url = null;
        if(cloudUrl != null && !cloudUrl.isEmpty()) {
            url = new URL(cloudUrl);
        }

        try (
                InputStream in = url.openStream(); // 创建输入流
                FileOutputStream fos = new FileOutputStream(localFilePath); // 创建输出流
        ) {
            ReadableByteChannel rbc = Channels.newChannel(in); // 将输入流包装成字节通道
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // 将数据从通道传输到输出流
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        String imagePath = "https://yuejuanpt.oss-cn-zhangjiakou.aliyuncs.com/2024-07-03/nsy.png";
//
//        long start = System.nanoTime();
//        Integer userId = getUserByGroupId(imagePath, "6");
//        long end = System.nanoTime();
//
//        System.out.println("运行时长：" + ((end - start) / 1000000000.0));
//
//        System.out.println(userId);
//        String base = getFileContentAsBase64(imagePath);
//        System.out.println(base);

//        addUser("6", "https://yuejuanpt.oss-cn-zhangjiakou.aliyuncs.com/2024-07-03/nsy.png", "1", "宁舒意");

//        Set<Integer> usersByGroupId = getUsersByGroupId("combined_image2.jpg", "7");
//
//        System.out.println(usersByGroupId);

        Student userByGroupId = getUserByGroupId("https://yuejuanpt.oss-cn-zhangjiakou.aliyuncs.com/wisdomHub/11:48:34-a009022889e243a792eddee272ff884925.jpg", "7");

        System.out.println("fdjskfjs:   " + userByGroupId);
    }
}
