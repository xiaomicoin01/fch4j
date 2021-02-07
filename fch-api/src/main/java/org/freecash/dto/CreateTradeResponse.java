package org.freecash.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreateTradeResponse {
    private Message message;
    private List<Input> inputs;
    private List<Output> outputs;

    @Data
    @Builder
    public static class Input{
        private String address;
        private BigDecimal amount;
        private String txid;
        private int dealType = 1;
        private int index;
        private int seq;
    }

    @Data
    @Builder
    public static class Output{
        private String address;
        private Double amount;
        private int dealType = 2;
        private int seq;
    }

    @Data
    @Builder
    public static class Message{
        private String msg;
        private int msgtype;
        private int dealType = 3;
    }
}
