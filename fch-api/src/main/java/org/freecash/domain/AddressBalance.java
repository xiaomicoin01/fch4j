package org.freecash.domain;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "fch_balance")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressBalance {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "pid", unique = true,nullable=false)
    private Integer pid;

    @Column(name = "address",columnDefinition = "varchar(255) NULL DEFAULT NULL COMMENT '地址'")
    private String address;

    @Column(name = "amount",columnDefinition = "varchar(255) NULL DEFAULT NULL COMMENT '数量'")
    private BigDecimal amount;
}
