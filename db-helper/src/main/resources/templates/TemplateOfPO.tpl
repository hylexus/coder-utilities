<#if table.packageName?? && (table.packageName!"") != "">
package ${table.packageName};
</#if>

<#list table.cols as c>
	<#if c.javaType.isNeedGenerateImportStatement()>
import ${c.javaType.getClass_().getName()};
	</#if>
</#list>

public class ${table.camelName}{
<#-- 属性 -->
<#list table.cols as c>

	<#-- 生成注释 -->
	<#if c.remark?? && (c.remark!"") != "">
	// ${c.remark}
	</#if>
	private ${c.javaType.getTypeName()} ${c.smallCamelName};
</#list>

<#-- 无参构造器 -->
	public ${table.camelName}(){
		
	}
<#-- set方法 -->
<#list table.cols as c>
	
	public void set${c.bigCamelName}(${c.javaType.getTypeName()} ${c.smallCamelName}){
		this.${c.smallCamelName} = ${c.smallCamelName};
	}
</#list>
<#-- get方法 -->
<#list table.cols as c>
	
	public ${c.javaType.getTypeName()} get${c.bigCamelName}(){
		return this.${c.smallCamelName};
	}
</#list>
<#-- 是否生成链式setter方法 -->
<#if table.generateChainStyleStterMethod>
	<#list table.cols as c>
		
	public ${table.camelName} ${c.smallCamelName}(${c.javaType.getTypeName()} ${c.smallCamelName}){
		this.${c.smallCamelName} = ${c.smallCamelName};
		return this;
	}
	</#list>
</#if>
}