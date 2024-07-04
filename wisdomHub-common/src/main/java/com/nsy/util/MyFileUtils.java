package com.nsy.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

/**
 * @className: MyFileUtils
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/3/18 20:15
 */

public class MyFileUtils {


    public static boolean isImage(File imageFile) {
        if (!imageFile.exists()) {
            return false;
        }

        Image img = null;
        try {
            img = ImageIO.read(imageFile);
            return img != null && img.getWidth(null) > 0 && img.getHeight(null) > 0;
        } catch (Exception e) {
            return false;
        } finally {
            // 最终重置为空
            img = null;
            imageFile.delete();
        }
    }

    /**
     * 用io流将MultipartFile类型转成File类型
     * @author 宁舒意
     * @date 22:19 2024/2/29
     * @param multipartFile
     * @return java.io.File
     **/
    public static File multipartFileToFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            OutputStream os = Files.newOutputStream(file.toPath());
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 删除文件夹及所包含的所有文件
     * @param path 文件路径
     **/

    public static void deleteDir(String path) {
        File file = new File(path);
        if(!file.exists()){
            return;
        }
        File[] list = file.listFiles();
        for(File f:list){
            //(f.isDirectory())检查指定的文件对象是否代表一个文件夹。
            if(f.isDirectory()){
                deleteDir(f.getPath());
            }else{
                f.delete();
            }
        }
        boolean deleted = file.delete();
    }


    /**
     * 将文件夹转换成List<File>
     * @author 宁舒意
     * @date 22:19 2024/2/29
     * @param folder
     * @param fileList
     **/
    public static  void addFilesToList(File folder, List<File> fileList) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // 递归调用，处理子文件夹
                        addFilesToList(file, fileList);
                    } else {
                        // 将文件添加到列表中
                        fileList.add(file);
                    }
                }
            }
        }
    }

    /**
     * 获取文件拓展名字，带小数点，如".jpg"
     * @author 宁舒意
     * @date 22:21 2024/2/29
     * @param filename
     * @return java.lang.String
     **/
    public  static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot);
            }
        }
        return filename;
    }
}
