package com.nsy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaceImageVo {
    /**
     * 图片路径
    **/
    String path;
    /**
     * 学生id
    **/
    Integer id;
    /**
     * 学习名字
    **/
    String name;
}
