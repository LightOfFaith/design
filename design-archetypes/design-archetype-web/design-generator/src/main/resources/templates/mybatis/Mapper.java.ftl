package ${packageName}.mapper;

import org.apache.ibatis.annotations.Mapper;

<#list importPackages as importPackage>import ${importPackage};</#list>

@Mapper
public interface ${className}Mapper {

    int insert${className}(${className} ${className?uncap_first});

    int logicDelete${className}ById(Long id);

    ${className} get${className}ById(Long id);

}