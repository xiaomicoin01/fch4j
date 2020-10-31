package org.freecash.component.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanglint
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreeDriveAuthRequest {

    @JsonProperty(value="user_addr")
    private String userAddress;
    @JsonProperty(value="dapp_addr")
    private String dappAddress;
    @JsonProperty(value="drive_id")
    private String driveId;
    private List<String> admin;
    private List<String>  member;
    private String timestamp;
    @JsonProperty(value="user_signature")
    private String userSignature;
}
