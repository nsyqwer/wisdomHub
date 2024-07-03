package com.nsy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName task_point
 */
@TableName(value ="task_point")
@Data
public class TaskPoint implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务点的标题
     */
    @TableField(value = "task_point_title")
    private String taskPointTitle;

    /**
     * 任务点类型（视频，文本等）
     */
    @TableField(value = "type")
    private String type;

    /**
     * 任务点序号
     */
    @TableField(value = "tp_number")
    private Integer tpNumber;

    /**
     * 任务点内容，视频url，文本url
     */
    @TableField(value = "content")
    private String content;

    /**
     * 章节id
     */
    @TableField(value = "chapter_id")
    private Integer chapterId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}