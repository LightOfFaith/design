package com.union.design.generator;

import com.google.common.base.CaseFormat;
import com.mysql.cj.MysqlType;
import com.union.design.generator.mybatis.MybatisResultMap;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class GeneratorUtils {

    public static List<ClassField> buildClassField(@Nonnull Set<String> importPackages, @Nonnull List<TableColumn> tableColumns
            ) throws ClassNotFoundException {
        List<ClassField> classFields = new ArrayList<>(tableColumns.size());
        for (TableColumn tableColumn : tableColumns) {
            Class<?> clazz = parseType(tableColumn.getTypeName());
            importPackages.add(clazz.getName());
            String columnName = tableColumn.getColumnName();
            String fieldName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName);

            if (StringUtils.equalsAnyIgnoreCase(fieldName,"id","createTime","modifyTime","isDeleted")){
                continue;
            }

            classFields.add(
                    ClassField.builder()
                            .fieldName(fieldName)
                            .fieldType(clazz)
                            .fieldSize(tableColumn.getColumnSize())
                            .isNullable(tableColumn.getIsNullable())
                            .fieldDef(StringUtils.defaultString(tableColumn.getColumnDef(), ""))
                            .remark(tableColumn.getRemark())
                            .tableColumn(tableColumn)
                            .build()
            );
        }
        return classFields;
    }

    public static Class<?> parseType(@Nonnull String typeName) throws ClassNotFoundException {
        MysqlType mysqlType = MysqlType.getByName(typeName);
        // Java 开发手册 【强制】不允许在程序任何地方中使用：1）java.sql.Date。 2）java.sql.Time。3）java.sql.Timestamp。
        if (MysqlType.DATE == mysqlType ||
                MysqlType.DATETIME == mysqlType ||
                MysqlType.TIMESTAMP == mysqlType ||
                MysqlType.TIME == mysqlType
        ) {
            return Class.forName(Date.class.getName());
        }
        return Class.forName(mysqlType.getClassName());
    }

}