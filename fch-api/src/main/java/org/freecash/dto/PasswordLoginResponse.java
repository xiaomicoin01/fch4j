package org.freecash.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanglint
 * @date 2020/5/31 9:50
 **/
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordLoginResponse {
    @ApiModelProperty(value = "登陆token")
    private String token;
}
