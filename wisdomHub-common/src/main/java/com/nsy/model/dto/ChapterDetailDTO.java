package com.nsy.model.dto;

import com.nsy.model.pojo.TaskPoint;
import com.nsy.model.vo.ChapterVO;
import lombok.Data;

import java.util.List;

/**
 * @className: ChapterDetailDTO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/19 17:03
 */
@Data
public class ChapterDetailDTO {
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
     * 章节任务点集合
     */
    private List<TaskPoint> taskPointList;


    /**
     * 章节的子章节集合
     */
    private List<ChapterVO> chapterVOList;


}
