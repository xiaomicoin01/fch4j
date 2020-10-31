package org.freecash.enm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author wanglint
 * @date 2020/5/29 17:33
 **/
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    SUCCESS(0,"success"),
    FCH_ERROR(1000,"客户端报错"),
    SERVICE_ERROR(1001,"业务异常"),
    TOKEN_ERROR(1002,"无效token"),
    LOGIN_ERROR(1003,"登录失败"),
    FREE_DRIVE_ERROR(1004,"freedrive报错");
    private int code;
    private String message;
}
