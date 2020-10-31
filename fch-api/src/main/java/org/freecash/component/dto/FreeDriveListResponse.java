package org.freecash.component.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanglint
 * @date 2020/5/24 13:31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreeDriveListResponse {
    private String code;
    private String msg;
    @JsonProperty(value="result")
    private Result result;

    @Data
    public static class Result{
        private int page;
        @JsonProperty(value="page_sum")
        private int size;
        @JsonProperty(value="drive_ids")
        private List<String> drive_ids;

    }
}
