package com.nsy.util.xunfei.text_moderation.vo;

import lombok.Data;

import java.util.List;
@Data
public class Category {
    /**
     * 内容建议结果
    **/
    String suggest;
    /**
     * 敏感内容
    **/
    String category;
    /**
     * 识别类型
    **/
    String category_description;
    /**
     * 置信度
    **/
    Integer confidence;//置信度
    /**
     * 敏感词列表
    **/
    List<String> word_list;
    /**
     * 敏感词附属信息
    **/
    List<WordInfos> word_infos;
}
