package com.nsy.constant;

public enum AssignmentTypeEnum {

    ASSIGNMENT(1,"作业"),
    EXAM(2,"考试");


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

    AssignmentTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}
