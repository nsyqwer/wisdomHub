package com.nsy.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @className: ChapterMainVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/16 15:48
 */

@Data
public class ChapterMainVO {
    /**
     * 章节集合
     */
    private List<ChapterVO> chapterVOList;


}
