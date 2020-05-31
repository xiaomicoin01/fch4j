package org.freecash.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanglint
 * @date 2020/5/31 10:10
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel
public class ValidTokenReponse {
    @ApiModelProperty(value="CID")
    private String userName;
    @ApiModelProperty(value="刷新后的Token")
    private String freshToken;
}
