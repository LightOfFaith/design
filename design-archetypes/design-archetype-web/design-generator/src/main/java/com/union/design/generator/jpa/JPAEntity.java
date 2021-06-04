package com.union.design.generator.jpa;

import com.union.design.generator.ClassField;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class JPAEntity {

    private String packageName;

    private Set<String> importPackages;

    private String tableName;

    private String className;

    private List<ClassField> classFields;

}