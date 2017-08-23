package cn.hylexus.db.helper.entity;

public class JavaType {

	private int typeCode;
	private Class<?> class_;
	private boolean needGenerateImportStatement = false;
	private String defaultValStr;
	private String typeName = null;

	public String getTypeName() {
		if (this.typeName == null) {
			if (this.class_ != null) {
				return this.class_.getSimpleName();
			}
		}
		return typeName;
	}

	public void setTypeName(String name) {
		this.typeName = name;
	}

	public JavaType typeName(String name) {
		this.typeName = name;
		return this;
	}

	public String getDefaultValStr() {
		return defaultValStr;
	}

	public void setDefaultValStr(String defaultValStr) {
		this.defaultValStr = defaultValStr;
	}

	public JavaType defaultValStr(String defaultValStr) {
		this.defaultValStr = defaultValStr;
		return this;
	}

	public int getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}

	public JavaType typeCode(int typeCode) {
		this.typeCode = typeCode;
		return this;
	}

	public Class<?> getClass_() {
		return class_;
	}

	public void setClass_(Class<?> class_) {
		this.class_ = class_;
	}

	public JavaType class_(Class<?> class_) {
		this.class_ = class_;
		return this;
	}

	public boolean isNeedGenerateImportStatement() {
		return needGenerateImportStatement;
	}

	public void setNeedGenerateImportStatement(boolean needGenerateImportStatement) {
		this.needGenerateImportStatement = needGenerateImportStatement;
	}

	public JavaType needGenerateImportStatement(boolean needGenerateImportStatement) {
		this.needGenerateImportStatement = needGenerateImportStatement;
		return this;
	}

	@Override
	public String toString() {
		return "JavaType [typeName=" + getTypeName() + ", typeCode=" + typeCode + ", class_=" + class_ + ", defaultValStr=" + defaultValStr + ", needGenerateImportStatement=" + needGenerateImportStatement + "]";
	}

}
