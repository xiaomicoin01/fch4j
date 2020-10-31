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
public class FreeDriveGetRequest {

    @JsonProperty(value="dapp_addr")
    private String dappAddress;
    @JsonProperty(value="drive_id")
    private String driveId;
    private int prev=0;
    private int next=0;
    private String timestamp;
    @JsonProperty(value="dapp_signature")
    private String userSignature;
}
