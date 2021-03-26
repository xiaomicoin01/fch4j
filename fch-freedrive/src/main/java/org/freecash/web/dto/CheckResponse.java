package org.freecash.web.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckResponse {
    @Builder.Default
    private int code = 0;
    @Builder.Default
    private String msg = "";

    @JsonProperty("data")
    private boolean exist;
}
