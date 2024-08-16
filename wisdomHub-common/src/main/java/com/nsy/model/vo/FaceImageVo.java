package com.nsy.model.vo;

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
     * 学生名字
    **/
    String name;
}
