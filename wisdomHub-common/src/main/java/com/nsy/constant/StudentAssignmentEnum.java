package com.nsy.constant;
/**
 * 学生作业完成状态
 * @author 宁舒意
 * @date 20:31 2024/6/18
 **/
public enum StudentAssignmentEnum {
    //（未提交0，待批阅1，已完成2）
    UNCOMMITTED(0,"未提交"),
    WaitCorrect(1,"待批阅"),
    FINISHED(2,"已完成");

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    StudentAssignmentEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

}
