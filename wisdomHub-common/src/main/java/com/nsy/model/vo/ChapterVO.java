package com.nsy.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * @className: ChapterVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/16 16:23
 */
@Data
public class ChapterVO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 章节标题
     */
    private String chapterTitle;

    /**
     * 章节序号
     */
    private Integer chapterNumber;



    /**
     * 章节的子章节集合
     */
    private List<ChapterVO> chapterVOList;

}
