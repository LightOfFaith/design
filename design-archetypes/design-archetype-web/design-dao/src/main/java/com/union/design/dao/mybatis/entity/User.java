package com.union.design.dao.mybatis.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

    /**
     * 姓名
     * 默认值：null
     */
    private String name;
    /**
     * 年龄
     * 默认值：0
     */
    private Integer age;
    @Builder
    public User(Long id, Date createTime, Date modifyTime, Boolean deleted, String name, Integer age) {
        super(id, createTime, modifyTime, deleted);
        this.name = name;
        this.age = age;
    }

}