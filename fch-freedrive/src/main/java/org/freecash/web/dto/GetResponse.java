package org.freecash.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetResponse {

    @Builder.Default
    private int code = 0;
    @Builder.Default
    private String msg = "";

    private Data data;

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data{
        private byte[] file;
    }
}
