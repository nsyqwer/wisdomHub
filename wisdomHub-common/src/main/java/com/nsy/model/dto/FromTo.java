package com.nsy.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: FromTo
 * @author: 宁舒意
 * @description: TODO
 * @date: 2024/7/14 9:52
 */
@Data
public class FromTo implements Serializable {
    private Long from;
    private Long to;
    private String text;
}
