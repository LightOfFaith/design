package ${packageName}.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

<#list importPackages as importPackage>import ${importPackage};</#list>

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "`${tableName}`")
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
    @Column(name = "`${classField.tableColumn.columnName}`")
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