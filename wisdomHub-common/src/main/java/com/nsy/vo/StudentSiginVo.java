package com.nsy.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 该学生签到情况
 * @author 文旅航
 * @date 2024/7/3 10:43
 * @className StudentSiginVo
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSiginVo {
   /**
    * 签到类型
   **/
   Integer type;
   /**
    * 签到状态，若为空未进行参与活动，需要进行签到
   **/
   String signinStatus;

   /**
    * 签到时间
    */
   @TableField(value = "signin_time")
   private LocalDateTime signinTime;
}
