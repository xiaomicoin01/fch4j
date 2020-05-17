package org.freecash.domain;

import lombok.Data;

/**
 * @Author wanglint
 * @Date 2019/10/13 14:21
 **/
@Data
public class Result {
    private int code;
    private String message;
    private Object data;


    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }}
