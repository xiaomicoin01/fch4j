package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "fch_prop")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BlockInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "pid", unique = true,nullable=false)
    private Integer pid;

    @Column(name = "prop_name", nullable = false)
    private String key;

    @Column(name = "prop_value", nullable = false)
    private String value;

    @Column(name = "prop_desp", nullable = false)
    private String desp;

    @Column(name = "create_date", nullable = false)
    @CreatedDate
    private Date createDate;

}