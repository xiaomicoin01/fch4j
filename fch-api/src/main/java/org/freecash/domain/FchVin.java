package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 知识库 Knowledge
 *
 * @author wanglin_自动生成
 * @date 2020-05-24 19:54:44
 */
@Table(name = "fch_vin",
        indexes = { @Index(name = "vin_txid_n", columnList = "txid,n")})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FchVin implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "pid",columnDefinition = " varchar(255) COMMENT 'ID'")
    private String pid;

    @Column(name = "txid",columnDefinition = " varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易ID'")
    private String txId;

    @Column(name = "n",columnDefinition = "int(11) NULL DEFAULT NULL COMMENT '索引'")
    private int n;

    public FchVin(String txId, int n) {
        this.txId = txId;
        this.n = n;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FchVin)) {
            return false;
        }
        FchVin fchVout = (FchVin) o;
        return getN() == fchVout.getN() &&
                Objects.equals(getTxId(), fchVout.getTxId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTxId(), getN());
    }
}