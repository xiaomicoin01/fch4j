package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 知识库 Knowledge
 *
 * @author wanglin_自动生成
 */
@Table(name = "fch_vout",indexes = { @Index(name = "vout_txid_n", columnList = "txid,n", unique = true)})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FchVout implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pid")
    private Integer pid;

    @Column(name = "parentTxId",columnDefinition = " varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父交易ID'")
    private String parentTxId;

    @Column(name = "txid",columnDefinition = " varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易ID'")
    private String txId;

    @Column(name = "address",columnDefinition = " varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址'")
    private String address;

    @Column(name = "n",columnDefinition = "int(11) NULL DEFAULT NULL COMMENT '索引'")
    private int n;

    @Column(precision = 23, scale = 8)
    private BigDecimal amount;

    @Column(name = "onLineTime",columnDefinition = "int(11) NULL DEFAULT NULL COMMENT '上链时间'")
    private Long onLineTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FchVout)) {
            return false;
        }
        FchVout fchVout = (FchVout) o;
        return getN() == fchVout.getN() &&
                Objects.equals(getTxId(), fchVout.getTxId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTxId(), getN());
    }
}