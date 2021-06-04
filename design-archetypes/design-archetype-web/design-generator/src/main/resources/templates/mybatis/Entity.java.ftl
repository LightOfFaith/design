package ${packageName}.entity;

import lombok.*;

<#list importPackages as importPackage>import ${importPackage};</#list>

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ${className} extends BaseEntity {

    <#list classFields as classField>
    /**
     * ${classField.remark}
    <#if classField.fieldDef == ''>
     *
    <#else>
     * 默认值：${classField.fieldDef}
    </#if>
     */
    private ${classField.fieldType.simpleName} ${classField.fieldName};
    </#list>
    @Builder
    public ${className}(Long id, Date createTime, Date modifyTime, Boolean deleted<#list classFields as classField>, ${classField.fieldType.simpleName} ${classField.fieldName}</#list>) {
        super(id, createTime, modifyTime, deleted);
        <#list classFields as classField>
        this.${classField.fieldName} = ${classField.fieldName};
        </#list>
    }

}