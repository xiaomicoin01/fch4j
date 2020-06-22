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
 @Table(name="fch_feip3v2")
 @Entity
 @Data
 @NoArgsConstructor
 @AllArgsConstructor
public class Feip3v2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;
		
	/** 协议名称 **/
	@Column(name="protocol_name", columnDefinition = "varchar(20) comment '协议名称'")
	private String protocolName;
		
	/** 协议编号 **/
	@Column(name="protocol_no", columnDefinition = "varchar(20) comment '协议编号'")
	private Integer protocolNo;
		
	/** 版本号 **/
	@Column(name="protocol_version", columnDefinition = "varchar(20) comment '版本号'")
	private String protocolVersion;
		
	/** 昵称 **/
	@Column(name="nick_name", columnDefinition = "varchar(200) comment '昵称'")
	private String nickName;
		
	/** 标签 **/
	@Column(name="tag", columnDefinition = "varchar(200) comment '标签'")
	private String tag;
		
	/** 邀请人 **/
	@Column(name="invite_user", columnDefinition = "varchar(200) comment '邀请人'")
	private String inviteUser;
		
	/** 详情 **/
	@Column(name="detail", columnDefinition = "varchar(200) comment '详情'")
	private String detail;
		
	/** 创建时间 **/
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
		
	/** 交易ID **/
	@Column(name="tx_hash", columnDefinition = "varchar(200) comment '交易ID'")
	private String txHash;
		
	/** 地址 **/
	@Column(name="address", columnDefinition = "varchar(200) comment '地址'")
	private String address;
		
	/** 状态 **/
	@Column(name="status", columnDefinition = "int(20) comment '状态'")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean status;

}