package org.freecash.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class UtxoResponse {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
    private Long count;
    private List<Trans> data;

    @Data
    @Builder
    public static class Trans{
        private int pid;
        private String txid;
        private Integer n;
        private BigDecimal amount;
        private int coinDay;
    }
}
