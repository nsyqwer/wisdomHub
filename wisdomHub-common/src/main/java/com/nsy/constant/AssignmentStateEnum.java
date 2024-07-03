package com.nsy.constant;

public enum AssignmentStateEnum {
    UNPUBLISHED(0,"未发布"),

    PUBLISHED(1,"进行中"),
    END(2,"已结束");

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

    AssignmentStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;


}
