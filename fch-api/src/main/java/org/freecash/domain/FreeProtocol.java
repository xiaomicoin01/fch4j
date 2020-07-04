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
@Table(name = "fch_free_protocol")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreeProtocol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "protocol_name", columnDefinition = "varchar(20) comment '协议类型'")
    private String protocolName;

    @Column(name = "protocol_no", columnDefinition = "int comment '协议编号'")
    private Integer protocolNo;

    @Column(name = "protocol_version", columnDefinition = "varchar(20) comment '版本号'")
    private String protocolVersion;

    @Column(name = "action", columnDefinition = "varchar(20) comment '操作'")
    private String action;

    @Column(name = "data_hash", columnDefinition = "varchar(200) comment '数据哈希'")
    private String dataHash;

    @Column(name = "encrypt", columnDefinition = "varchar(200) comment '加密标志'")
    private String encrypt;

    @Column(name = "encrypted_pwd", columnDefinition = "varchar(1000) comment '密钥'")
    private String encryptedPwd;

    @Column(name = "language", columnDefinition = "varchar(20) comment '语言'")
    private String language;

    @Column(name = "drive_id", columnDefinition = "varchar(200) comment '存储id'")
    private String driveId;

    @Column(name = "file_name", columnDefinition = "varchar(200) comment '文件名'")
    private String fileName;

    @Column(name = "file", columnDefinition = "longtext comment '协议文件'")
    @Lob
    private String file;

    @Column(name = "create_date", columnDefinition = "datetime comment '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}