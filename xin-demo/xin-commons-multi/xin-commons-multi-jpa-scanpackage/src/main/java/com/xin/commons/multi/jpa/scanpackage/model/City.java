package com.xin.commons.multi.jpa.scanpackage.model;

import javax.persistence.*;

/**
 * 城市信息
 */
@Table(name = "city")
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
