package com.nsy.util.xunfei.face;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nsy.model.pojo.Student;
import com.nsy.util.OSSUtils;
import com.nsy.util.xunfei.face.util.FileUtil;
import com.nsy.util.xunfei.face.util.HttpUtil;
import com.nsy.util.xunfei.face.vo.RenLianDuiBiView;
import com.nsy.model.vo.FaceImageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;


/**
 * 人脸检测及属性分析 WebAPI 接口调用示例
 * 运行前：请先填写Appid、APIKey、APISecret以及图片路径
 * 运行方法：直接运行 main() 即可
 * 结果： 控制台输出结果信息
 * 接口文档（必看）：https://www.xfyun.cn/doc/face/xf-face-detect/API.html
 * @author main.com.iflytek
 */

@Slf4j
public class WebFaceDetect {

    static List<Student> students = new ArrayList<>();
    static List<String> ren_liangs = new ArrayList<>();
    static WebFaceCompare demo = new WebFaceCompare();

    public static void main(String[] args) throws Exception {
        System.out.println(students);
        WebFaceDetect demo = new WebFaceDetect();
        ResponseData respData = demo.faceContrast(imagePath1);
        if (respData!=null && respData.getPayLoad().getFace_detect_result() != null) {
            String textBase64 = respData.getPayLoad().getFace_detect_result().getText();
            String text = new String(Base64.getDecoder().decode(textBase64));
            System.out.println("人脸检测及属性分析结果(text)base64解码后：");

            System.out.println(text);

            getImage(text);

            System.out.println("未到学生名单");
            for(Student student : students){
                System.out.println(student);
            }
        }
    }

    //获取有头像的学生集合
    public static List<Student> getStudentFaces(List<Student> studentList){
        List<Student> studentNoFaces = new ArrayList<>();
        for(Student student : studentList){
            if(student.getFaceImage() == null || student.getFaceImage().isEmpty()){
                studentNoFaces.add(student);
            }
        }
        for(Student student : studentNoFaces){
            studentList.remove(student);
        }
        return studentList;
    }

    //获取框出头像的每个人的对应的图片
    public static List<FaceImageVo> getFaceImageVos(List<Student> studentList, MultipartFile multipartFile) throws Exception {
        List<FaceImageVo> faceImageVos = new ArrayList<>();

        imagePath1 = OSSUtils.uploadFileToOOS(multipartFile);
        students = getStudentFaces(studentList);

        String text = getJsonText();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(text);

        // 获取总的面部数量
        int totalFaces = rootNode.get("face_num").asInt();

        // 遍历所有面部数据
        for (int i = 1; i <= totalFaces; i++) {
            JsonNode faceNode = rootNode.path("face_" + i);
            if (faceNode.isMissingNode()) {
                System.out.println("Face " + i + " data not found.");
                continue;
            }

            int height = faceNode.get("h").asInt();
            double score = faceNode.get("score").asDouble();
            int width = faceNode.get("w").asInt();
            int x = faceNode.get("x").asInt();
            int y = faceNode.get("y").asInt();

            Path path = Paths.get("D:/");
            URL url = new URL(imagePath1);
            InputStream is = url.openStream();
            BufferedImage image = ImageIO.read(is);
            // 获取Graphics2D对象，用于绘图
            Graphics2D g2d = image.createGraphics();

            // 设置为红色粗线条
            g2d.setColor(Color.RED);
            BasicStroke thickStroke = new BasicStroke(4.0f);
            g2d.setStroke(thickStroke);
            markImage(g2d, x, y, height, width);

            String savePath = OSSUtils.uploadImageToOOS(image, i + ".jpg");

            //进行切割出只有头像的部分
            BufferedImage croppedImage = image.getSubimage(x,y,width,height);
            Path outputImagePath = path.resolve("image.jpg");
            ImageIO.write(croppedImage, "jpg", outputImagePath.toFile());

            //获取该人脸对应的班级中学生姓名以及id
            Student student = getStudentByFace(outputImagePath.toString());
            if(student != null) {
                faceImageVos.add(new FaceImageVo(savePath, student.getId(), student.getName()));
            }
        }

        return faceImageVos;
    }

    //通过照片获取班级中对应的学生
    private static Student getStudentByFace(String ren_liang) throws Exception {
        Student student_1 = null;
        for(Student student : students){
            if(student.getFaceImage() == null || student.getFaceImage().isEmpty())
                continue;
            Double score = get_ren_liang_dui_bi(student.getFaceImage(), ren_liang);
            if(score >= 0.9){
                student_1 = student;
                break;
            }
        }
        if(student_1 != null) {
            students.remove(student_1);
        }
        return student_1;
    }


    //获取不在教室的学生集合
    public static List<Student> getNoReachStudents(List<Student> studentList, String image) throws Exception {
        imagePath1 = image;
        students = new ArrayList<>(studentList);

        System.out.println(students);

        System.out.println("人脸检测及属性分析结果(text)base64解码后：");

        String text = getJsonText();

        System.out.println(text);

        getImage(text);

        System.out.println("未到达学生");
        System.out.println(students);

        return students;
    }

    //获取检测后的json数据
    private static String getJsonText() throws Exception {
        WebFaceDetect demo = new WebFaceDetect();
        ResponseData respData = demo.faceContrast(imagePath1);
        if (respData!=null && respData.getPayLoad().getFace_detect_result() != null) {
            String textBase64 = respData.getPayLoad().getFace_detect_result().getText();
            String text = new String(Base64.getDecoder().decode(textBase64));
            return text;
        }
        log.error("出现异常，未进行检测image图片");
        return null;
    }

    //获取文件路径
    public static Path get_wen_jian(String str) throws IOException {
        int index = str.lastIndexOf("."); // 获取"."的位置
        if (index != -1) { // 确保找到了"."
            String result = str.substring(0, index); // 移除从"."到最后的所有字符

            Path path = Paths.get(result);

            Files.createDirectories(path);

            return path;
        }
        return null;
    }

    public static String detection_image;
    // 获取绘制人脸后的图片
    public static void getImage(String text) throws Exception {
        Path path = Paths.get("D:");
        URL url = new URL(imagePath1);
        InputStream is = url.openStream();
        BufferedImage image = ImageIO.read(is);

        // 获取Graphics2D对象，用于绘图
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.RED);
        // 创建并设置线条的粗细为5像素
        BasicStroke thickStroke = new BasicStroke(2.0f);
        g2d.setStroke(thickStroke);

        getWeiZhiMark(g2d, text, image, path);

        detection_image = OSSUtils.uploadImageToOOS(image, ".jpg");
    }

    //存储图片
    private static String saveImage(BufferedImage image, String name) throws IOException {
        String path = "src/main/java/main/com/iflytek/markImage/" + name;
        File outputfile = new File(path);
        ImageIO.write(image, "PNG", outputfile);
        return path;
    }

    //获取json数据中的人脸位置信息并进行绘制图片
    private static void getWeiZhiMark(Graphics2D g2d, String text, BufferedImage image, Path path) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(text);

        // 获取总的面部数量
        int totalFaces = rootNode.get("face_num").asInt();

        // 遍历所有面部数据
        for (int i = 1; i <= totalFaces; i++) {
            JsonNode faceNode = rootNode.path("face_" + i);
            if (faceNode.isMissingNode()) {
                System.out.println("Face " + i + " data not found.");
                continue;
            }

            int height = faceNode.get("h").asInt();
            double score = faceNode.get("score").asDouble();
            int width = faceNode.get("w").asInt();
            int x = faceNode.get("x").asInt();
            int y = faceNode.get("y").asInt();

            BufferedImage croppedImage = image.getSubimage(x,y,width,height);
            Path outputImagePath = path.resolve("image.jpg");

            ImageIO.write(croppedImage, "jpg", outputImagePath.toFile());

            getStudentByFace(outputImagePath.toString());

            markImage(g2d, x, y, height, width);
        }
    }

    //获取两张图片人脸对比后的结果
    private static Double get_ren_liang_dui_bi(String image1, String image2) throws Exception {
        WebFaceCompare.ResponseData respData = demo.faceContrast(image1, image2);
        if (respData!=null && respData.getPayLoad().getFaceCompareResult() != null) {
            String textBase64 = respData.getPayLoad().getFaceCompareResult().getText();
            String text = new String(Base64.getDecoder().decode(textBase64));

            ObjectMapper mapper = new ObjectMapper();
            RenLianDuiBiView renLian = mapper.readValue(text, RenLianDuiBiView.class);
            return renLian.getScore();
        }
        return 0.0;
    }

    //绘制图片
    private static void markImage(Graphics2D g2d, int x, int y, int height, int width) {
        g2d.drawRect(x, y, width, height);
    }

    static String imagePath1 = "src/main/java/main/com/iflytek/jian_ce_image/class.jpg";//请填写要检测的图片路径

    class Property {
        public final static String requestUrl = "https://api.xf-yun.com/v1/private/s67c9c78c";
        public final static String appid = "061a30a7"; //请填写控制台获取的APPID,
        public final static String apiSecret = "MmQ2OWM5OTlhNGVlOTUwODc1MTRjZmQz";  //请填写控制台获取的APISecret;
        public final static String apiKey = "bec69e8847182161bcb751644b3f340a";  //请填写控制台获取的APIKey
        public final static String serviceId = "s67c9c78c";
    }

    public String getXParam(String imageBase641, String imageEncoding1) {
        JsonObject jso = new JsonObject();

        /** header **/
        JsonObject header = new JsonObject();
        header.addProperty("app_id", Property.appid);
        header.addProperty("status", 3);

        jso.add("header", header);

        /** parameter **/
        JsonObject parameter = new JsonObject();
        JsonObject service = new JsonObject();
        service.addProperty("service_kind", "face_detect");
        //service.addProperty("detect_points", "1");//检测特征点
        //service.addProperty("detect_property", "1");//检测人脸属性

        JsonObject faceCompareResult = new JsonObject();
        faceCompareResult.addProperty("encoding", "utf8");
        faceCompareResult.addProperty("format", "json");
        faceCompareResult.addProperty("compress", "raw");
        service.add("face_detect_result", faceCompareResult);
        parameter.add(Property.serviceId, service);
        jso.add("parameter", parameter);

        /** payload **/
        JsonObject payload = new JsonObject();
        JsonObject inputImage1 = new JsonObject();
        inputImage1.addProperty("encoding", imageEncoding1);
        inputImage1.addProperty("image", imageBase641);
        payload.add("input1", inputImage1);

        jso.add("payload", payload);
//        System.out.println("输出这个");
//        System.out.println(jso.toString());
        return jso.toString();
    }


    //读取image
    private byte[] readImage(String imagePath) throws IOException {
        if(imagePath.contains("https")){
            URL url = new URL(imagePath);
            try (InputStream in = url.openStream()) {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                return result.toByteArray();
            }
        }
        else {
            InputStream is = new FileInputStream(imagePath);
            byte[] imageByteArray1 = FileUtil.read(imagePath);
            //return is.readAllBytes();
            return imageByteArray1;
        }
    }

    public ResponseData faceContrast(String imageFirstUrl) throws Exception {

        String url = assembleRequestUrl(Property.requestUrl, Property.apiKey, Property.apiSecret);

        String imageBase641 = Base64.getEncoder().encodeToString(readImage(imageFirstUrl));
        String imageEncoding1 = imageFirstUrl.substring(imageFirstUrl.lastIndexOf(".") + 1);

        //System.out.println("url:"+url);
        return handleFaceContrastRes(url, getXParam(imageBase641, imageEncoding1));
    }

    public static final Gson json = new Gson();

    private ResponseData handleFaceContrastRes(String url, String bodyParam) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        String result = HttpUtil.doPost2(url, headers, bodyParam);
        if (result != null) {
            System.out.println("已经输出");
            System.out.println("人脸检测及属性分析接口调用结果：" + result);
            return json.fromJson(result, ResponseData.class);
        } else {
            return null;
        }
    }


    //构建url
    public static String assembleRequestUrl(String requestUrl, String apiKey, String apiSecret) {
        URL url = null;
        // 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
        String httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://", "https://");
        try {
            url = new URL(httpRequestUrl);
            //获取当前日期并格式化
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());

            String host = url.getHost();
            if (url.getPort() != 80 && url.getPort() != 443) {
                host = host + ":" + String.valueOf(url.getPort());
            }
            StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
                    append("date: ").append(date).append("\n").//
                    append("POST ").append(url.getPath()).append(" HTTP/1.1");
            Charset charset = Charset.forName("UTF-8");
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);

            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
            return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));

        } catch (Exception e) {
            throw new RuntimeException("assemble requestUrl error:" + e.getMessage());
        }
    }

    public static class ResponseData {
        private Header header;
        private PayLoad payload;

        public Header getHeader() {
            return header;
        }

        public PayLoad getPayLoad() {
            return payload;
        }
    }

    public static class Header {
        private int code;
        private String message;
        private String sid;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getSid() {
            return sid;
        }
    }

    public static class PayLoad {
        private FaceResult face_detect_result;

        public FaceResult getFace_detect_result() {
            return face_detect_result;
        }
    }

    public static class FaceResult {
        private String compress;
        private String encoding;
        private String format;
        private String text;

        public String getCompress() {
            return compress;
        }

        public String getEncoding() {
            return encoding;
        }

        public String getFormat() {
            return format;
        }

        public String getText() {
            return text;
        }
    }
}