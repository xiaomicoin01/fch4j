package org.freecash.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class ValidMessageRequest {
    @ApiModelProperty("未签名消息")
    private String message;
    @ApiModelProperty("签名消息")
    private String signMessage;
    @ApiModelProperty("签名地址")
    private String address;
}
