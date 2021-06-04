package com.union.design.generator;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClassField {

    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private Class<?>  fieldType;

    /**
     * 字段大小
     */
    private Integer fieldSize;
    /**
     * 字段可空性
     */
    private String isNullable;
    /**
     * 字段默认值
     */
    private String fieldDef;
    /**
     * 字段注释
     */
    private String remark;

    private TableColumn tableColumn;

}