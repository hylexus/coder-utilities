package cn.hylexus.db.helper.entity;

import org.apache.commons.lang3.StringUtils;

import cn.hylexus.db.helper.utils.NamingUtils;

public class ColumnInfo {

	private String name;
	private int typeCode; // java.sql.Types
	private String remark;
	private int length;
	private int precision;
	private JavaType javaType;

	public String getSmallCamelName() {
		if (StringUtils.isBlank(name))
			return name;
		return NamingUtils.underLine2Camel(name, true);
	}

	public String getBigCamelName() {
		if (StringUtils.isBlank(name))
			return name;
		return NamingUtils.underLine2Camel(name, false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public JavaType getJavaType() {
		return javaType;
	}

	public void setJavaType(JavaType javaType) {
		this.javaType = javaType;
	}

	@Override
	public String toString() {
		return "ColumnInfo [name=" + name + ", typeCode=" + typeCode + ", remark=" + remark + ", length=" + length + ", precision=" + precision + ", javaType=" + javaType + "]";
	}

}
