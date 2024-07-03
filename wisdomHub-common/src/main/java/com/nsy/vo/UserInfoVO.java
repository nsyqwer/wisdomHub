package com.nsy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @className: UserInfoVO
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/19 14:43
 */
@Data
public class UserInfoVO {
    /**
     * 用户主键id
     */
    private Integer id;


    /**
     * 如果是学生就是studentId,如果是老师就是teacherId
     */
    private Integer roleId;

    /**
     * 账号
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 身份
     */
    private String identity;

}
