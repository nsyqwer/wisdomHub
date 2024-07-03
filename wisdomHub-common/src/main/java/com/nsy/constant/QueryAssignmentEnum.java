package com.nsy.constant;
//学生查询作业使用的枚举类
public enum QueryAssignmentEnum {
//0表示查询所有，1表示查询已完成的，2表示查询未完成的


    UnFinished(0,"未完成"),
    Finished(1,"已完成"),
    All(2,"全部");
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

QueryAssignmentEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}
