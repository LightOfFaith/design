package com.union.design.generator.mybatis;

import com.union.design.generator.ClassField;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class MybatisEntity {

    private String packageName;

    private String className;

    private Set<String> importPackages;

    private List<ClassField> classFields;

}