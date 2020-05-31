package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "fch_prop")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "f_id", nullable = false)
    private String id;
    @Column(name = "prop_name", nullable = false)
    private String key;
    @Column(name = "prop_value", nullable = false)
    private String value;
    @Column(name = "prop_desp", nullable = false)
    private String desp;
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

}