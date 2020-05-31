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
@Table(name = "fch_knowledge")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Knowledge implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "id")
    private String id;

    /**
     * 作者
     **/
    @Column(name = "author")
    private String author;

    /**
     * 标题
     **/
    @Column(name = "title")
    private String title;

    /**
     * 内容
     **/
    @Column(name = "content")
    private String content;
    /**
     * 内容
     **/
    @Column(name = "driveId")
    private String driveId;


    /**
     * 类型
     **/
    @Column(name = "type")
    private String type;

    /**
     * 交易ID
     **/
    @Column(name = "txId")
    private String txId;

    /**
     * 创建时间
     **/
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    /**
     * 创建时间
     **/
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
}