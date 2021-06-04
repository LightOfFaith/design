package com.union.design.generator.mybatis;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MybatisController {

    private String packageName;

    private String className;

}