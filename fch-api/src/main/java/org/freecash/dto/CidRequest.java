package org.freecash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidRequest {
    private String nickName;
    private String address;
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
}