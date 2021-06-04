<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packageName}.mapper.${className}Mapper">

    <resultMap id="entityMap" type="${packageName}.entity.BaseEntity">
        <id column="id" property="id"/>
        <result column="create_time" property="createTime"/>
        <result column="modify_time" property="modifyTime"/>
        <result column="is_deleted" property="deleted"/>
    </resultMap>

    <resultMap id="${className?uncap_first}Map" type="${packageName}.entity.${className}" extends="entityMap">
        <#list resultMaps as resultMap>
        <result column="${resultMap.columnName}" property="${resultMap.fieldName}"/>
        </#list>
    </resultMap>

    <insert id="insert${className}" useGeneratedKeys="true" keyProperty="id"
            parameterType="${packageName}.entity.${className}">
        insert into `${tableName}` (<#list resultMaps as resultMap><#if resultMap_has_next>`${resultMap.columnName}`, <#else>`${resultMap.columnName}`</#if></#list>)
        values (<#list resultMaps as resultMap><#if resultMap_has_next><#noparse>#{</#noparse>${resultMap.fieldName}<#noparse>}</#noparse>,<#else><#noparse>#{</#noparse>${resultMap.fieldName}<#noparse>}</#noparse></#if></#list>)
    </insert>

    <update id="logicDelete${className}ById" parameterType="long">
        <bind name="deleted" value="true" />
        update `${tableName}`
        set is_deleted = <#noparse>#{</#noparse>deleted<#noparse>}</#noparse>
        where id = <#noparse>#{</#noparse>id<#noparse>}</#noparse>
    </update>

    <select id="getUserById" parameterType="long" resultMap="${className?uncap_first}Map">
        select `id`, `create_time`, `modify_time`, `is_deleted`, <#list resultMaps as resultMap><#if resultMap_has_next>`${resultMap.columnName}`, <#else>`${resultMap.columnName}`</#if></#list>
        from `user`
        where id = <#noparse>#{</#noparse>id<#noparse>}</#noparse>
    </select>

</mapper>