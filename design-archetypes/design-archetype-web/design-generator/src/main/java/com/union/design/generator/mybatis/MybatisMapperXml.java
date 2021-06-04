package com.union.design.generator.mybatis;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MybatisMapperXml {

    private String packageName;

    private String className;

    private String tableName;

    private List<MybatisResultMap> resultMaps;

}