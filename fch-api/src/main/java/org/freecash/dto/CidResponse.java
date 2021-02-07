package org.freecash.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CidResponse {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
    private Long count;
    private List<Cid> data;

    @Data
    @Builder
    public static class Cid{
        private String address;
        private String nickName;
        private boolean status;
        private String tag;
        private String invite_user;
        private String detail;
    }
}
