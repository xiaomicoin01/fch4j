package org.freecash.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author: create by hayes
 * @version: v1.0
 * @description: cn.enn.rpt.controller.request
 * @date:2020/4/3
 **/
@Data
@ApiModel
public class PasswordRegisterRequest {
    @ApiModelProperty(value = "CID",required = true)
    private String cid;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "签名",required = true)
    private String code;
}
