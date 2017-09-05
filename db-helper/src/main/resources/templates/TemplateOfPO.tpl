<#if table.modelPackageName?? && (table.modelPackageName!"") != "">
package ${table.modelPackageName};
</#if>

<#list table.getShouldBeImportedClassNames() as c>
import ${c};
</#list>

public class ${table.getModelName()}{
<#-- 属性 -->
<#list table.cols as c>

	<#-- 生成注释 -->
	<#if c.remark?? && (c.remark!"") != "">
	// ${c.remark}
	</#if>
	private ${c.javaType.getTypeName()} ${c.getFieldName()};
</#list>

<#-- 无参构造器 -->
	public ${table.getModelName()}(){
		
	}
<#-- set方法 -->
<#list table.cols as c>
	
	public void set${c.getMethodName()}(${c.javaType.getTypeName()} ${c.getFieldName()}){
		this.${c.getFieldName()} = ${c.getFieldName()};
	}
</#list>
<#-- get方法 -->
<#list table.cols as c>
	
	public ${c.javaType.getTypeName()} get${c.getMethodName()}(){
		return this.${c.getFieldName()};
	}
</#list>
<#-- 是否生成链式setter方法 -->
<#if table.generateChainStyleStterMethod>
	<#list table.cols as c>
		
	public ${table.getModelName()} ${c.getFieldName()}(${c.javaType.getTypeName()} ${c.getFieldName()}){
		this.${c.getFieldName()} = ${c.getFieldName()};
		return this;
	}
	</#list>
</#if>
}