package com.union.design.generator.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liaox
 * @date 2021/4/26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseEntity implements Serializable {

    private Long id;

    private Date createTime;

    private Date modifyTime;

    private Boolean deleted;

}
