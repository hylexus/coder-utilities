package ${table.mapperClassPackageName};

<#if table.primaryKeys[0].javaType.isNeedGenerateImportStatement()>
import ${table.primaryKeys[0].javaType.getClass_().getName()};
</#if>
import ${table.modelPackageName}.${table.getModelName()};
import org.apache.ibatis.annotations.Mapper;

<#if (mapperConfig.getGenerateSelectMethodByPrimaryKey()) || (mapperConfig.getGenerateDeleteMethodByPrimaryKey())>
import org.apache.ibatis.annotations.Param;
</#if>

<#if mapperConfig.getGenerateFinalAllMethod()>
import java.util.List;
</#if>

@Mapper
public interface ${table.getMapperClassName()} {

<#if mapperConfig.getGenerateSelectMethodByPrimaryKey()>
	${table.getModelName()} findByPrimaryKey(@Param("id") ${table.primaryKeys[0].javaType.getTypeName()} id);
</#if>

<#if mapperConfig.getGenerateInsertMethod()>
	int doCreate(${table.getModelName()} e);
</#if>

<#if mapperConfig.getGenerateUpdateMethod()>
	int update(${table.getModelName()} e);
</#if>

<#if mapperConfig.getGenerateDeleteMethodByPrimaryKey()>
	int deleteByPrimaryKey(@Param("id") ${table.primaryKeys[0].javaType.getTypeName()} id);
</#if>

<#if mapperConfig.getGenerateFinalAllMethod()>
	List<${table.getModelName()}> findAll();
</#if>
}