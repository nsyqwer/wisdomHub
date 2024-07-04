package com.nsy.util.xunfei.text_moderation.vo;

import lombok.Data;

@Data
public class Result {
    private String suggest;// 审核建议结果：pass通过，block不合格
    private Detail detail;// 审核内容结果详情
}
