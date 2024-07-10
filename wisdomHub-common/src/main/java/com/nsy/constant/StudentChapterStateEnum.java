package com.nsy.constant;

public enum StudentChapterStateEnum {
    UnFinished(0,"未完成"),
    Finished(1,"已完成");


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

    StudentChapterStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    }
