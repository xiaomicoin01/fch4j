package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 密码身份CID Feip3v2
 *
 * @author wanglin_自动生成
 * @email 532646938@qq.com
 * @date 2020-05-23 18:00:02
 */
@Table(name = "fch_feip3")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Feip3 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Integer pid;

    /**
     * 昵称
     **/
    @Column(name = "nick_name", columnDefinition = "varchar(200) comment '昵称'")
    private String name;

    /**
     * 标签
     **/
    @Column(name = "tag", columnDefinition = "varchar(200) comment '标签'")
    private String tag;

    /**
     * 邀请人
     **/
    @Column(name = "invite_user", columnDefinition = "varchar(200) comment '邀请人'")
    private String inviteUser;

    /**
     * 详情
     **/
    @Column(name = "detail", columnDefinition = "varchar(200) comment '详情'")
    private String detail;

    @Column(name = "home_page", columnDefinition = "varchar(200) comment '主页'")
    private String homePage;

    @Column(name = "notice_fee",columnDefinition = "varchar(255) NULL DEFAULT NULL COMMENT '通知费用'")
    private BigDecimal noticeFee;

    /**
     * 交易ID
     **/
    @Column(name = "tx_hash", columnDefinition = "varchar(200) comment '交易ID'")
    private String txHash;

    /**
     * 地址
     **/
    @Column(name = "address", columnDefinition = "varchar(200) comment '地址'", nullable = false)
    private String address;

    /**
     * 状态
     **/
    @Column(name = "status", columnDefinition = "int(1) comment '状态'")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean status;

    /**
     * 创建时间
     **/
    @Column(name = "create_date", nullable = false)
    @CreatedDate
    private Date createDate;
}