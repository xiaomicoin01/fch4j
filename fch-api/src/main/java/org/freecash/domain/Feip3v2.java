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
	@Column(name="id",nullable=false)
	private String id;
		
	/** 协议名称 **/
	@Column(name="protocol_name",nullable=false)
	private String protocolName;
		
	/** 协议编号 **/
	@Column(name="protocol_no",nullable=false)
	private Integer protocolNo;
		
	/** 版本号 **/
	@Column(name="protocol_version",nullable=false)
	private String protocolVersion;
		
	/** 昵称 **/
	@Column(name="nick_name",nullable=false)
	private String nickName;
		
	/** 标签 **/
	@Column(name="tag",nullable=false)
	private String tag;
		
	/** 邀请人 **/
	@Column(name="invite_user",nullable=false)
	private String inviteUser;
		
	/** 详情 **/
	@Column(name="detail",nullable=false)
	private String detail;
		
	/** 创建时间 **/
	@Column(name="create_date",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
		
	/** 交易ID **/
	@Column(name="tx_hash",nullable=false)
	private String txHash;
		
	/** 地址 **/
	@Column(name="address",nullable=false)
	private String address;
		
	/** 状态 **/
	@Column(name="status",nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean status;

}