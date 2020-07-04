package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 知识库 Knowledge
 *
 * @author wanglin_自动生成
 * @date 2020-05-24 19:54:44
 */
@Table(name = "fch_feip1v3")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feip1v3 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "protocol_name", columnDefinition = "varchar(20) comment '协议名称'")
    private String protocolName;

    @Column(name = "protocol_no", columnDefinition = "varchar(20) comment '协议编号'")
    private String protocolNo;

    @Column(name = "protocol_version", columnDefinition = "varchar(20) comment '版本号'")
    private String protocolVersion;

    @Column(name = "custom_type", columnDefinition = "varchar(20) comment '类型'")
    private String customType;

    @Column(name = "custom_no", columnDefinition = "varchar(20) comment '编号'")
    private String customNo;

    @Column(name = "custom_version", columnDefinition = "varchar(20) comment '版本'")
    private String customVersion;

    @Column(name = "custom_name", columnDefinition = "varchar(100) comment '名称'")
    private String customName;

    @Column(name = "language", columnDefinition = "varchar(20) comment '语言'")
    private String language;

    @Column(name = "author", columnDefinition = "varchar(100) comment '发布者'")
    private String author;

    @Column(name = "data_hash", columnDefinition = "varchar(100) comment '数据HASH'")
    private String dataHash;

    @Column(name = "txId", columnDefinition = "varchar(100) comment '交易ID'")
    private String txId;

    @Column(name = "create_date", columnDefinition = "datetime comment '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}