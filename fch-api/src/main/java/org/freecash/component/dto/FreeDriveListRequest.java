package org.freecash.component.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanglint
 * @date 2020/5/24 13:31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreeDriveListRequest {
    private String protocol;
    private String addr;
    @JsonProperty(value="dapp_addr")
    private String dappAddress;
    private int page;
    private String detail = "0";
    private String timestamp;
    @JsonProperty(value="user_signature")
    private String userSignature;

}
