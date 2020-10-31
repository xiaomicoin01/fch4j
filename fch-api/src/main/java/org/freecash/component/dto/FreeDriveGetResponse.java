package org.freecash.component.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanglint
 * @date 2020/5/24 13:32
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreeDriveGetResponse {
    private String code;
    private String msg;
    @JsonProperty(value="result")
    private List<FreeDriveGetDriveIdResponseItem> result;

    @Data
    public static class FreeDriveGetDriveIdResponseItem{
        @JsonProperty(value="drive_id")
        private String driveId;
        @JsonProperty(value="metadata")
        private String metaData;
        private String data;
        private String type;
        private String branch;
        private int prev;
        private int next;
    }
}


