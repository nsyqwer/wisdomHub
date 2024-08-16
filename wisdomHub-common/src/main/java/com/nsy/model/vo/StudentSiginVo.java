package com.nsy.model.vo;

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
    * 签到（0：智能考勤，1：普通签到，2：手势签到，3：签到码签到）
   **/
   Integer type;
   /**
    * 签到状态，若为空未进行参与活动，需要进行签到
   **/
   String signInStatus;
   /**
    * 是否截止（0：未截止，1：截止）
   **/
   Integer timeState;
   /**
    * 开始时间
    */
   @TableField(value = "begin_time")
   private LocalDateTime beginTime;
   /**
    * 结束时间
    */
   @TableField(value = "end_time")
   private LocalDateTime endTime;
   /**
    * 签到时间
    */
   @TableField(value = "signin_time")
   private LocalDateTime signinTime;
}
