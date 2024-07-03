package com.nsy.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    public static String getNewTime(){
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 创建格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化时间
        return now.format(formatter);
    }
}
