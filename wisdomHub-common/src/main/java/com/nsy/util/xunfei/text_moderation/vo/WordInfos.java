package com.nsy.util.xunfei.text_moderation.vo;

import lombok.Data;

import java.util.List;
@Data
public class WordInfos {
    String word;//敏感词
    List<Integer> positions;//敏感词位置下标信息
    List<String> lib_name_list;//敏感词属于的黑名单库
}
