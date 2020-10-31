package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 密码身份CID Feip3v2
 *
 * @author wanglin_自动生成
 * @email 532646938@qq.com
 * @date 2020-05-23 18:00:02
 */
@Table(name = "fch_focp3v3")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Focp3v3 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pid")
    private String id;

    /**
     * 协议名称
     **/
    @Column(name = "protocol_name", columnDefinition = "varchar(20) comment '协议名称'")
    private String protocolName;

    /**
     * 协议编号
     **/
    @Column(name = "protocol_no", columnDefinition = "int comment '协议编号'")
    private Integer protocolNo;

    /**
     * 版本号
     **/
    @Column(name = "protocol_version", columnDefinition = "varchar(20) comment '版本号'")
    private String protocolVersion;

    /**
     * 详情
     **/
    @Column(name = "data_hash", columnDefinition = "varchar(200) comment '详情'")
    private String dataHash;

    /**
     * freeDirveId
     **/
    @Column(name = "file_path", columnDefinition = "varchar(200) comment 'FreeDrive存储地址'")
    private String filePath;

    /**
     * 交易ID
     **/
    @Column(name = "tx_id", columnDefinition = "varchar(200) comment '交易ID'")
    private String txId;

    /**
     * cid
     **/
    @Column(name = "cid", columnDefinition = "varchar(200) comment 'cid'")
    private String cid;

    /**
     * 创建时间
     **/
    @Column(name = "create_date", columnDefinition = "datetime comment '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


}