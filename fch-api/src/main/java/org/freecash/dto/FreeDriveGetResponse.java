package org.freecash.dto;

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
    private DataInfo put;
    private List<UpdateInfo> update;

    @Data
    public static class DataInfo{
        private String metadata;
        private String data;
    }
    @Data
    public static class UpdateInfo{
        private String metadata;
        private String data;
        @JsonProperty(value="update_id")
        private String updateId;
    }
}




