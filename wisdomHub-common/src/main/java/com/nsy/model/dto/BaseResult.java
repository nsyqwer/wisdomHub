package com.nsy.model.dto;

import lombok.Data;

/**
 * @className: BaseResult
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/5/15 16:43
 */
@Data
public class BaseResult<T> {
    // 数据返回统一格式
    private T data;
    //返回状态码
    private Integer code;
    //返回描述信息
    private String message;
    public BaseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public BaseResult(Integer code,  String message,T data) {
        this.data = data;
        this.code = code;
        this.message = message;
    }
}
