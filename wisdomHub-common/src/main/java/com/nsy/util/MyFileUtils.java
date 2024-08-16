package com.nsy.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 通过压缩包,上传并获取字符串，获取字符串
    **/
    public static List<List<String>> uploadUnzippedFiles(MultipartFile multipartFile) throws IOException {
        Map<String, List<MyImageDate>> map = new HashMap<>();

        List<MyPair<byte[], String>> biss = new ArrayList<>();

        try (InputStream is = multipartFile.getInputStream();
             ZipArchiveInputStream zais = new ZipArchiveInputStream(is)) {

            ArchiveEntry entry;

            while ((entry = zais.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    String fileName = entry.getName();
                    System.out.println("进行上传的文件名：" + fileName);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = zais.read(buffer)) > 0) {
                        baos.write(buffer, 0, length);
                    }

                    biss.add(new MyPair<>(baos.toByteArray(), fileName));
                }
            }
        }

        for(MyPair<byte[], String> myPair: biss) {
            String fileName = myPair.getValue();
            byte[] fileContent = myPair.getKey();
            System.out.println("进行上传的文件" + fileName);
            // 上传OSS云端
            String newFileName = OSSUtils.uploadInputStreamOOS(new ByteArrayInputStream(fileContent), fileName);

            MyPair<String, Integer> pair = getNameAndSum(fileName);
            MyImageDate myImageDate = new MyImageDate(pair.getValue(), pair.getKey(), newFileName);
            if(map.get(pair.getKey()) == null){
                List<MyImageDate> list = new ArrayList<>();
                list.add(myImageDate);
                map.put(pair.getKey(), list);
            }
            else{
                List<MyImageDate> list = map.get(pair.getKey());
                list.add(myImageDate);
                map.put(pair.getKey(), list);
            }
        }

        return sortLists(map);
    }

    public static List<List<String>> sortLists(Map<String, List<MyImageDate>> map){
        List<List<String>> lists = new ArrayList<>();
        // 获取键集并遍历
        for (String key : map.keySet()) {
            List<String> stringList = new ArrayList<>();
            List<MyImageDate> list = map.get(key);
            list.sort(Comparator.comparing(MyImageDate::getSum));

            list.forEach(myImageDate -> {
                stringList.add(myImageDate.getImagePath());
            });
            lists.add(stringList);
        }
        return lists;
    }

    @Data
    static class MyPair<K, V> {
        private K key;
        private V value;

        public MyPair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class MyImageDate {
        private Integer sum;
        private String name;
        private String imagePath;
    }

    public static boolean checkZipFileNames(MultipartFile multipartFile) {
        try (InputStream is = multipartFile.getInputStream();
             ZipArchiveInputStream zais = new ZipArchiveInputStream(is)) {

            ArchiveEntry entry;

            while ((entry = zais.getNextEntry()) != null) {
                if (!entry.isDirectory()) {//处理文件
                    System.out.println("解压的文件名：" + entry.getName());
                    if(getNameAndSum(entry.getName()) == null){
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("解压失败");
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 获取XXX(number).jpg
    **/
    public static MyPair<String, Integer> getNameAndSum(String fileName){
        Map<String, Object> map = new HashMap<>();
        // 使用正则表达式匹配
        Pattern pattern = Pattern.compile("(\\w+)\\((\\d+)\\)\\.jpg");
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            // 提取分组
            String prefix = matcher.group(1); // 匹配第一个括号内的文本
            int number = Integer.parseInt(matcher.group(2)); // 第二个括号内的文本并转换为整数

            map.put("name", prefix);
            map.put("sum", number);

            return new MyPair<>(prefix, number);
        } else {
            return null;
        }
    }

//    public static void main(String[] args) {
//        String fileName = "XXX(1).jpg";
//
//        // 使用正则表达式匹配
//        Pattern pattern = Pattern.compile("(\\w+)\\((\\d+)\\)\\.jpg");
//        Matcher matcher = pattern.matcher(fileName);
//
//        if (matcher.find()) {
//            // 提取分组
//            String prefix = matcher.group(1); // 匹配第一个括号内的文本
//            int number = Integer.parseInt(matcher.group(2)); // 第二个括号内的文本并转换为整数
//
//            System.out.println("Prefix: " + prefix);
//            System.out.println("Number: " + number);
//        } else {
//            System.out.println("No match found.");
//        }
//    }

}
