package com.nsy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更改签到状态请求类
 * @author 文旅航
 * @date 2024/7/2 20:42
 * @className UpdateSiginDto
**/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSiginDto{
    /**
     * 签到id
    **/
    Integer siginId;
    /**
     * 签到id
    **/
    Integer studentId;
    /**
     * 更改后的签到状态（已签，缺勤，事假，病假，公假，迟到）
    **/
    String signinStatus;
}
