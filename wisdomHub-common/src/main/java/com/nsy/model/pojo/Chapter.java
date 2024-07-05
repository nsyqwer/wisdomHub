package com.nsy.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName chapter
 */
@TableName(value ="chapter")
@Data
public class Chapter implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 章节标题
     */
    @TableField(value = "chapter_title")
    private String chapterTitle;

    /**
     * 章节序号
     */
    @TableField(value = "chapter_number")
    private Integer chapterNumber;

    /**
     * 章节层级
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 父级章节id
     */
    @TableField(value = "father_id")
    private Integer fatherId;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private Integer courseId;

    /**
     * 任务点标题
     */
    @TableField(value = "task_point_title")
    private String taskPointTitle;

    /**
     * 任务点内容
     */
    @TableField(value = "content")
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}