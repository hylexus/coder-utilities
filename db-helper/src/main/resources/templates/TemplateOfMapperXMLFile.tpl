<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//www.mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${table.mapperClassPackageName}.${table.getMapperClassName()}">

<#assign sharp = "#">
<#assign size = table.cols?size>

<#if mapperConfig.getGenerateSelectMethodByPrimaryKey()>
	<select id="findByPrimaryKey" resultType="map">
		SELECT
		<#list table.cols as c>
			${c.name} AS ${c.getFieldName()}<#if (c_index?if_exists+1 !=size)>,</#if>
		</#list>
		FROM
			${table.tableName}
		WHERE
			${table.primaryKeys[0].name} = ${sharp}{id}
	</select>
</#if>

<#if mapperConfig.getGenerateInsertMethod()>
	<insert id="doCreate" useGeneratedKeys="true" keyProperty="id">
		insert into ${table.tableName} (
			<#list table.cols as c>
			${c.name}<#if (c_index?if_exists+1 !=size)>,</#if>
			</#list>
		)
		values (
			<#list table.cols as c>
			${sharp}{${c.getFieldName()}}<#if (c_index?if_exists+1 !=size)>,</#if>
			</#list>
		)
	</insert>
</#if>

</mapper>