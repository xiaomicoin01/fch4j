package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * FOCP5V1_文件引用
 * @author wanglin_自动生成
 * @email 532646938@qq.com
 * @date 2020-05-23 18:00:02
 */
 @Table(name="fch_focp5v1")
 @Entity
 @Data
 @NoArgsConstructor
 @AllArgsConstructor
public class Focp5v1 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "protocol_name", columnDefinition = "varchar(20) comment '协议名称'")
	private String protocolName="FOCP";

	@Column(name = "protocol_no", columnDefinition = "int comment '协议编号'")
	private Integer protocolNo=5;

	@Column(name = "protocol_version", columnDefinition = "varchar(20) comment '版本号'")
	private String protocolVersion="1";

	@Column(name = "action", columnDefinition = "varchar(20) comment '操作'")
	private String action;

	@Column(name = "data_hash", columnDefinition = "varchar(200) comment '数据哈希'")
	private String dataHash;

	@Column(name = "encrypt", columnDefinition = "varchar(200) comment '加密标志'")
	private String encrypt;

	@Column(name = "encrypted_pwd", columnDefinition = "varchar(1000) comment '密钥'")
	private String encryptedPwd;

	@Column(name = "drive_id", columnDefinition = "varchar(200) comment '存储id'")
	private String driveId;

	@Column(name = "file_type", columnDefinition = "varchar(200) comment '文件类型'")
	private String fileType;

	@Column(name = "file_name", columnDefinition = "varchar(200) comment '文件名'")
	private String fileName;

	@Column(name = "quoter", columnDefinition = "varchar(1000) comment '引用源'")
	private String quoter;

	@Column(name = "file", columnDefinition = "longtext comment '文件'")
	@Lob
	private String file;

	@Column(name = "create_date", columnDefinition = "datetime comment '创建时间'")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;


}