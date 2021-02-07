package org.freecash.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTradeRequest {
    private String from;
    private String message;
    private List<Integer> utxo;
    private List<To> to;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class To{
        private String address;
        private Double amount;
    }
}
