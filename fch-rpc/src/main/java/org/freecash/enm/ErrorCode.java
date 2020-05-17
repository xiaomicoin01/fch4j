package org.freecash.enm;

/**
 * description: ErrorCode <br>
 * date: 2019/10/13 14:26 <br>
 * author: wanglint <br>
 * version: 1.0 <br>
 */
public enum ErrorCode {
    NO_ACCOUNT(-1,"改地址对应的平台账号不存在"),
    MONEY_LOW(-2,"余额不足");

    private int code;
    private String message;


    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
