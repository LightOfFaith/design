package com.union.design.dao.jpa.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "`user`")
public class User extends BaseEntity {

    @Column(name = "`name`")
    private String name;
    private Integer age;

    @Builder
    public User(Long id, Date createTime, Date modifyTime, Boolean deleted, String name, Integer age) {
        super(id, createTime, modifyTime, deleted);
        this.name = name;
        this.age = age;
    }

}