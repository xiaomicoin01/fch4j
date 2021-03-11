package org.freecash.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.freecash.enm.TxTypeEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FchUserTxRecordResponse {
    private Integer pageNumber = 1;
    private Integer pageSize = 10;
    private Long count;
    private List<FchUserTxRecordResponse.Record> records;

    @Data
    @Builder
    public static class Record{
        private String address;
        private TxTypeEnum type;
        private String txId;
        @JSONField(format="yyyy-MM-dd HH:mm:ss")
        private Date txDate;
        @JSONField(format = "0.00000000")
        private BigDecimal amount;
    }
}
