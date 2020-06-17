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
 @Table(name="fch_feip6v2",indexes = {
 		@Index(name="from_to",columnList = "auth_from_address,auth_to_address",unique = true)
 })
 @Entity
 @Data
 @NoArgsConstructor
 @AllArgsConstructor
public class Feip6v2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id",nullable=false)
	private String id;
		
	/** 协议名称 **/
	@Column(name="protocol_name",columnDefinition = "varchar(200) comment '协议名称'")
	private String protocolName="FEIP";
		
	/** 协议编号 **/
	@Column(name="protocol_no",columnDefinition = "int(2) comment '协议编号'")
	private Integer protocolNo=6;
		
	/** 协议版本 **/
	@Column(name="protocol_version",columnDefinition = "varchar(200) comment '协议版本'")
	private String protocolVersion="2";
		
	/** 操作 **/
	@Column(name="nick_name",columnDefinition = "varchar(200) comment '操作'")
	@Enumerated(EnumType.STRING)
	private Feip6v2Otption option;
		
	/** 开始有效期 **/
	@Column(name="begin_date",columnDefinition = "varchar(200) comment '开始有效期'")
	private String beginDate;

	/** 结束有效期 **/
	@Column(name="end_date",columnDefinition = "varchar(200) comment '结束有效期'")
	private String endDate;
		
	/** 适用范围 **/
	@Column(name="protocol_range",columnDefinition = "varchar(200) comment '适用范围'")
	private String protocolRange;
		
	/** 授权原地址 **/
	@Column(name="auth_from_address",columnDefinition = "varchar(200) comment '授权原地址'")
	private String authFromAddress;
		
	/** 授权目标地址 **/
	@Column(name="auth_to_address",columnDefinition = "varchar(200) comment '授权目标地址'")
	private String authToAddress;

}