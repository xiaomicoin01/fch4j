package org.freecash.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 会议纪要 Meeting
 *
 * @author wanglin_自动生成
 * @email 532646938@qq.com
 * @date 2020-07-16 20:51:44
 */
@Table(name = "fch_meeting")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     **/
    @Id
    @Column(name = "id")
    private String id;

    /**
     * 时间
     **/
    @Column(name = "date", columnDefinition = "varchar(250) comment '时间'")
    private String date;

    /**
     * 地址
     **/
    @Column(name = "address", columnDefinition = "varchar(250) comment '地址'")
    private String address;

    /**
     * 人员
     **/
    @Column(name = "person", columnDefinition = "varchar(250) comment '人员'")
    private String person;

    /**
     * 话题
     **/
    @Column(name = "title", columnDefinition = "varchar(250) comment '话题'")
    private String title;

    /**
     * 记录员
     **/
    @Column(name = "recorder", columnDefinition = "varchar(250) comment '记录员'")
    private String recorder;

    /**
     * 讨论内容
     **/
    @Column(name = "content", columnDefinition = "blob NULL COMMENT '讨论内容'")
    private String content;

    /**
     * 创建人
     **/
    @Column(name = "createUser", columnDefinition = "varchar(250) comment '创建人'")
    private String createUser;

    /**
     * 保存地址
     **/
    @Column(name = "driveId", columnDefinition = "varchar(250) comment '保存地址'")
    private String driveId;

    /**
     * 创建时间
     **/
    @Column(name = "createDate", columnDefinition = "datetime comment '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}