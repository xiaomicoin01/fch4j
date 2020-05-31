package org.freecash.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登陆方式
 * @author wanglint
 */
@ApiModel
public enum LoginType {
    @ApiModelProperty(value="密码登陆")
    PASSWORD,
    @ApiModelProperty(value="签名登陆")
    SIGNATRUE
}
