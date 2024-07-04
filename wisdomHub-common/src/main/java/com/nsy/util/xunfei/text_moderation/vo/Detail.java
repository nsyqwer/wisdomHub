package com.nsy.util.xunfei.text_moderation.vo;

import lombok.Data;

import java.util.List;
@Data
public class Detail {
    /**
     * 审核文本具体内容
    **/
    String content;
    /**
     * 敏感词列表
    **/
    List<Category> category_list;
}
