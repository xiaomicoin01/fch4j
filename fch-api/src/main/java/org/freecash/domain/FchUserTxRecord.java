package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.freecash.enm.TxTypeEnum;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wanglint
 **/
@Table(name = "fch_user_tx_record")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class FchUserTxRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pid")
    private Integer pid;

    @Column(name="from_address")
    private String fromAddress;

    @Column(name="to_address")
    private String toAddress;

    @Column(name="in_or_out",nullable=false)
    @Enumerated(EnumType.STRING)
    private TxTypeEnum type;

    @Column(name="txId",nullable=false)
    private String txId;

    @Column(name="txDate",nullable=false)
    private BigDecimal txDate;

    @Column(precision = 23, scale = 8)
    private BigDecimal amount;

    @Column(name = "create_date", nullable = false)
    @CreatedDate
    private Date createDate;
}
