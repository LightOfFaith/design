package com.union.design.generator.jpa;

import com.google.common.base.CaseFormat;
import com.union.design.generator.ClassField;
import com.union.design.generator.FreeMarkerConfig;
import com.union.design.generator.GeneratorUtils;
import com.union.design.generator.TableColumn;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;

public class JPAGeneratorUtils {

    public static void main(String[] args) throws ClassNotFoundException {
        // JDBC驱动程序类名称
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String driverClassNameToUse = driverClassName.trim();
        // 加载
        Class.forName(driverClassNameToUse);
        String url = "jdbc:mysql://localhost:3306/db?characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "x0418";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTableTypes();
            String tableName = "user";
            ResultSet columns = databaseMetaData.getColumns(connection.getCatalog(), connection.getSchema(), tableName, null);
            List<TableColumn> tableColumns = new ArrayList<>();
            while (columns.next()) {
                tableColumns.add(TableColumn.builder()
                        .columnName(columns.getString("COLUMN_NAME"))
                        .typeName(columns.getString("TYPE_NAME"))
                        .columnSize(columns.getInt("COLUMN_SIZE"))
                        .isNullable(columns.getString("IS_NULLABLE"))
                        .columnDef(columns.getString("COLUMN_DEF"))
                        .remark(columns.getString("REMARKS"))
                        .build());
            }
            Set<String> importPackages = new HashSet<>();
            List<ClassField> classFields = GeneratorUtils.buildClassField(importPackages, tableColumns);

            Configuration config = FreeMarkerConfig.getConfig();

            String packageName = "com.union.design.generator.jpa";
            String className = StringUtils.capitalize(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, tableName));

            JPAEntity entity = JPAEntity.builder()
                    .packageName(packageName)
                    .importPackages(importPackages)
                    .tableName(tableName)
                    .className(className)
                    .classFields(classFields)
                    .build();

            File entityFile = new File(Objects.requireNonNull(
                    FreeMarkerConfig.class.getClassLoader().getResource("templates")
            ).getPath() + "/jpa/" + className + ".java");
            FileWriter entityWriter = new FileWriter(entityFile);
            Template entityTemplate = config.getTemplate("/jpa/Entity.java.ftl", StandardCharsets.UTF_8.displayName());
            entityTemplate.process(entity, entityWriter);

//            Set<String>    mapperImportPackages = new HashSet<>();
//            mapperImportPackages.add(packageName + ".entity." + className);
//            MybatisMapper mapper = MybatisMapper.builder()
//                    .packageName(packageName)
//                    .importPackages(mapperImportPackages)
//                    .className(className)
//                    .build();
//
//            File mapperFile = new File(Objects.requireNonNull(
//                    FreeMarkerConfig.class.getClassLoader().getResource("templates")
//            ).getPath() + "/jpa/" + className + "Mapper.java");
//            FileWriter mapperWriter = new FileWriter(mapperFile);
//            Template mapperTemplate = config.getTemplate("/jpa/Mapper.java.ftl", StandardCharsets.UTF_8.displayName());
//            mapperTemplate.process(mapper, mapperWriter);
//
//            MybatisMapperXml mapperXml=MybatisMapperXml.builder()
//                    .packageName(packageName)
//                    .tableName(tableName)
//                    .className(className)
//                    .resultMaps(resultMaps)
//                    .build();
//
//            File mapperXmlFile = new File(Objects.requireNonNull(
//                    FreeMarkerConfig.class.getClassLoader().getResource("templates")
//            ).getPath() + "/jpa/" + className + "Mapper.xml");
//            FileWriter mapperXmlWriter = new FileWriter(mapperXmlFile);
//            Template mapperXmlTemplate = config.getTemplate("/jpa/Mapper.xml.ftl", StandardCharsets.UTF_8.displayName());
//            mapperXmlTemplate.process(mapperXml, mapperXmlWriter);
//
//            MybatisManager manager=MybatisManager.builder()
//                    .packageName(packageName)
//                    .className(className)
//                    .build();
//
//            File managerFile = new File(Objects.requireNonNull(
//                    FreeMarkerConfig.class.getClassLoader().getResource("templates")
//            ).getPath() + "/jpa/" + className + "Manager.java");
//            FileWriter managerWriter = new FileWriter(managerFile);
//            Template managerTemplate = config.getTemplate("/jpa/Manager.java.ftl", StandardCharsets.UTF_8.displayName());
//            managerTemplate.process(manager, managerWriter);
//
//            MybatisService service=MybatisService.builder()
//                    .packageName(packageName)
//                    .className(className)
//                    .build();
//
//            File serviceFile = new File(Objects.requireNonNull(
//                    FreeMarkerConfig.class.getClassLoader().getResource("templates")
//            ).getPath() + "/jpa/" + className + "Service.java");
//            FileWriter serviceWriter = new FileWriter(serviceFile);
//            Template serviceTemplate = config.getTemplate("/jpa/Service.java.ftl", StandardCharsets.UTF_8.displayName());
//            serviceTemplate.process(service, serviceWriter);
//
//            MybatisController controller=MybatisController.builder()
//                    .packageName(packageName)
//                    .className(className)
//                    .build();
//
//            File controllerFile = new File(Objects.requireNonNull(
//                    FreeMarkerConfig.class.getClassLoader().getResource("templates")
//            ).getPath() + "/jpa/" + className + "Controller.java");
//            FileWriter controllerWriter = new FileWriter(controllerFile);
//            Template controllerTemplate = config.getTemplate("/jpa/Controller.java.ftl", StandardCharsets.UTF_8.displayName());
//            controllerTemplate.process(controller, controllerWriter);

        } catch (SQLException | IOException | TemplateException e) {
            System.err.println(ExceptionUtils.getStackTrace(e));
        }

    }

}
