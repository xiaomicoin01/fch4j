package org.freecash.component.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanglint
 * @date 2020/5/24 13:32
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreeDrivePutResponse {
    private String code;
    private String msg;
    @JsonProperty(value="result")
    private String driveId;

}
