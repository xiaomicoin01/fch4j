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
public class ValidMessageResponse {
    @ApiModelProperty("错误码")
    private int code;
    @ApiModelProperty("错误信息")
    private String message;
    @ApiModelProperty("是否正确")
    private boolean valid;
}
