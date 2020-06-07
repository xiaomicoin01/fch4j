package org.freecash.dto;

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
    @JsonProperty(value="fch_addr")
    private String address;
    @JsonProperty(value="drive_id")
    private String driveId;
}
