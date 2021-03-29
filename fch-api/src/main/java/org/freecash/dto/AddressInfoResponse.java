package org.freecash.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AddressInfoResponse {
    @ApiModelProperty("助记符")
    private List<String> mnemonics;
    @ApiModelProperty("私钥")
    private String privateKey;
    @ApiModelProperty("公钥")
    private String publicKey;
    @ApiModelProperty("地址")
    private String address;
}
