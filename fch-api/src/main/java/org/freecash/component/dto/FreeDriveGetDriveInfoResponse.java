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
public class FreeDriveGetDriveInfoResponse {
    private String code;
    private String msg;
    @JsonProperty(value="result")
    private Result result;

    @Data
    public static class Result{
        @JsonProperty(value="drive_id")
        private String driveId;
        private int prev;
        private int next;
        private String branch;
        private List<String> admin;
        private List<String>  member;
    }
}


