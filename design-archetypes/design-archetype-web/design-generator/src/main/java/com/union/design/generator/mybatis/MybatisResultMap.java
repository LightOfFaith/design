package com.union.design.generator.mybatis;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class MybatisResultMap {

    private String columnName;

    private String fieldName;

}