package org.freecash.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanglint
 * @date 2020/5/31 17:17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PasswordLoginRequest {
    @ApiModelProperty(value = "CID",required = true)
    private String cid;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
