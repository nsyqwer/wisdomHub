package com.nsy.util;


import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Slf4j
public class OSSUtils {

    public static final String endpoint = "oss-cn-zhangjiakou.aliyuncs.com";

    public static final String bucket = "yuejuanpt";

    public static final String accessKey = "LTAI5tRhLi2QTrErLgi9xG3K";

    public static final String secretKey = "HkWmmg0a5tWzHuOS1QoJME6Xt3Xbj4";

    public static final String marketHost = "https://{}.[]".replace("{}", bucket).replace("[]", endpoint);


    public static OSS ossClient = new OSSClientBuilder().build(endpoint, accessKey, secretKey);

//    public static void main(String[] args) {
//        saveLocalFile("https://yuejuanpt.oss-cn-zhangjiakou.aliyuncs.com/wisdomHub/00%3A35%3A18-aec0817174c34673871a49a16b0a72da26009030e2920970ba8d656d011f1879.jpg");
//    }

    /**
     * 通过InputStream，上传OSS
    **/
    public static String uploadInputStreamOOS(InputStream input, String originalFilename){
        String cloudPath = getPath(originalFilename);
        ossClient.putObject(bucket, cloudPath, input);

        log.info("处理照片: {}", marketHost + "/" + cloudPath);
        return marketHost + "/" + cloudPath;
    }

    /**
     * 上传文件至OSS
     **/
    public static String uploadFileToOOS(MultipartFile file) {
        // 设置上传到云存储的路径
        String cloudPath = getPath(file.getOriginalFilename());
        // 线程池异步上传图片
        try {
            ossClient.putObject(bucket, cloudPath, file.getInputStream());
        } catch (IOException e) {
            return null;
        }
        log.info("处理照片: {}", marketHost + "/" + cloudPath);
        return marketHost + "/" + cloudPath;
    }

    /**
     * 上传图片至OSS
    **/
    public static String uploadImageToOOS(BufferedImage image, String originalFilename) throws IOException {
        //将BufferedImage转化为InputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos); // 将BufferedImage写入字节流，这里假设图片格式为jpg
        byte[] bytes = baos.toByteArray(); // 获取字节流中的字节
        InputStream input = new ByteArrayInputStream(bytes);

        String cloudPath = getPath(originalFilename);
        ossClient.putObject(bucket, cloudPath, input);

        log.info("处理照片: {}", marketHost + "/" + cloudPath);
        return marketHost + "/" + cloudPath;
    }

    /**
     * 文件下载到本地
    **/
    public static String saveLocalFile(String filePath){
        filePath = filePath.replace("https://yuejuanpt.oss-cn-zhangjiakou.aliyuncs.com/", "");
        int lastDotIndex = filePath.lastIndexOf('.');
        String fileType = filePath.substring(lastDotIndex);

        String localFile = "wisdomHub-common/src/main/java/com/nsy/util/file/666" + fileType;

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, filePath);
        File file = new File(localFile);

        ossClient.getObject(getObjectRequest, file);

        return localFile;
    }

    /**
     * 判断图片是否存在
    **/
    public static boolean checkImage(String image_path){
        try {
            URL url = new URL(image_path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("图片地址有效，可以访问。");
                return true;
            } else {
                System.out.println("图片地址无效或图片不存在。");
                return false;
            }
        } catch (Exception e) {
            System.out.println("发生异常，图片地址无效或访问失败：" + e.getMessage());
            return false;
        }
    }

    public static String getPath(String originalFilename){

        System.out.println("上传的文件名：" + originalFilename);

        // 用户上传文件时指定的前缀，即存放在以时间命名的文件夹内
        String dir = "wisdomHub";

        // 设置上传到云存储的文件名，规则为"当前时间-UUID文件名.源文件后缀名"
        String cloudFileName = new StringBuilder()
                .append(new SimpleDateFormat("HH:mm:ss").format(new Date()))
                .append("-")
                .append(IdUtil.simpleUUID())
                .append(originalFilename)
                .toString();

        // 设置上传到云存储的路径
        return dir + "/" + cloudFileName;
    }
}
