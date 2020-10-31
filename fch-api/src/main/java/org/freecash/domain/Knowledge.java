package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.freecash.component.FreeDriveEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 知识库 Knowledge
 *
 * @author wanglin_自动生成
 */
@Table(name = "fch_knowledge")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Knowledge implements FreeDriveEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "protocol_name", columnDefinition = "varchar(20) comment '协议名称'")
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

    @Column(name = "author", columnDefinition = "varchar(200) comment '作者'")
    private String author;

    @Column(name = "title", columnDefinition = "varchar(200) comment '题目'")
    private String title;

    @Column(name = "content", columnDefinition = "longtext comment '内容'")
    @Lob
    private String content;

    @Column(name = "type", columnDefinition = "varchar(200) comment '类型'")
    private String type;

    @Column(name = "create_date", columnDefinition = "datetime comment '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}