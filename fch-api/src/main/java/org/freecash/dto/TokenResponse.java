package org.freecash.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanglint
 * @date 2020/5/31 9:53
 **/
@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    @ApiModelProperty(value = "登陆请求码")
    private String requestCode;
}
