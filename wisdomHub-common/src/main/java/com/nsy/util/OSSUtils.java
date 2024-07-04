package com.nsy.util;


import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class OSSUtils {

    public static final String endpoint = "oss-cn-zhangjiakou.aliyuncs.com";

    public static final String bucket = "yuejuanpt";

    public static final String accessKey = "LTAI5tRhLi2QTrErLgi9xG3K";

    public static final String secretKey = "HkWmmg0a5tWzHuOS1QoJME6Xt3Xbj4";

    public static final String marketHost = "https://{}.[]".replace("{}", bucket).replace("[]", endpoint);


    public static OSS ossClient = new OSSClientBuilder().build(endpoint, accessKey, secretKey);

    /**
     * 上传文件至OOS
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
     * 上传图片至OOS
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

    public static String getPath(String originalFilename){

        System.out.println("**************" + originalFilename);

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
