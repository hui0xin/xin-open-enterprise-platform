package com.xin.commons.multi.jpa.annotation.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long userId;
    private String userName;
    private Integer gender;
    private Long phone;
    private String email;
    private Date createTime;
    private Date updateTime;
}
