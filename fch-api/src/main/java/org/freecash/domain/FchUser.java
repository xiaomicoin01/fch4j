package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wanglint
 **/
@Table(name = "fch_user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FchUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pid")
    private Integer pid;

    @Column(name="username",nullable=false)
    private String username;

    @Column(name="password",nullable=false)
    private String password;

    @Column(name="nickname",nullable=false)
    private String nickname;

    @Column(name="address",nullable=false)
    private String address;

    @Column(name="privkey",nullable=false)
    private String privkey;

    @Column(name = "create_date", nullable = false)
    @CreatedDate
    private Date createDate;
}
