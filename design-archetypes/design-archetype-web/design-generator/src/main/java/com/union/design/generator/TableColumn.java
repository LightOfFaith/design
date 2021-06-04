package com.union.design.generator;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TableColumn {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 列大小
     */
    private Integer columnSize;
    /**
     * 列可空性
     */
    private String isNullable;
    /**
     * 列默认值
     */
    private String columnDef;
    /**
     * 列注释
     */
    private String remark;

}