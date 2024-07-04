package com.nsy.util.xunfei.text_moderation.vo;

import lombok.Data;

@Data
public class Response {
    private String code;
    private String desc;
    private MyData data;
    private String sid;
}
