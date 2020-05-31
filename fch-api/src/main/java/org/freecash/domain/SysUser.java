package org.freecash.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wanglint
 * @date 2020/5/31 9:17
 **/
@Table(name = "t_sys_user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    @Id
    @Column(name="id",nullable=false)
    private String id;
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

}
