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
 * @author wanglin_自动生成
 * @email 532646938@qq.com
 * @date 2020-05-23 18:00:02
 */
 @Table(name="fch_focp3v1")
 @Entity
 @Data
 @NoArgsConstructor
 @AllArgsConstructor
public class Focp3v1 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="pid")
	private String id;
		
	/** 协议名称 **/
	@Column(name="protocol_name")
	private String protocolName;
		
	/** 协议编号 **/
	@Column(name="protocol_no")
	private Integer protocolNo;
		
	/** 版本号 **/
	@Column(name="protocol_version")
	private String protocolVersion;
		
	/** 昵称 **/
	@Column(name="author")
	private String author;
		
	/** 标签 **/
	@Column(name="file_type")
	private String fileType;
		
	/** 邀请人 **/
	@Column(name="file_no")
	private String fileNo;
		
	/** 详情 **/
	@Column(name="file_version")
	private String fileVersion;
	
	/** 详情 **/
	@Column(name="data_hash")
	private String dataHash;

	/** 详情 **/
	@Column(name="file_path")
	private String filePath;

	/** 创建时间 **/
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	/** 创建时间 **/
	@Column(name="update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
		
	/** 状态 **/
	@Column(name="status")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean status;

}