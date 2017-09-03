package cn.hylexus.db.helper.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.hylexus.db.helper.config.GeneratorConfig.TableConfig;
import cn.hylexus.db.helper.converter.naming.NamingStrategy;
import cn.hylexus.db.helper.exception.DBHelperCommonException;

public class TableInfo {

	public static final String escape_sharp = "#";
	private String modelPackageName;
	private String mapperClassPackageName;
	private String tableName;
	private String comments;
	// 是否生成链式setter方法
	private Boolean generateChainStyleStterMethod = false;
	private List<ColumnInfo> cols = new ArrayList<>();
	private List<ColumnInfo> primaryKeys = new ArrayList<>();
	private TableConfig tableConfig;

	// others
	private String trimmedPrefix = "";
	private String appendedPrefix = "";
	private String appendedPrefix4MapperClass = "";
	private String appendedSufix = "";

	// naming strategy
	private NamingStrategy namingStrategy;
	private String sprcifiedModelName;
	private String sprcifiedMapperClassName;

	public List<ColumnInfo> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<ColumnInfo> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public TableInfo addPrimaryKey(ColumnInfo key) {
		this.primaryKeys.add(key);
		return this;
	}

	public String getAppendedPrefix4MapperClass() {
		return appendedPrefix4MapperClass;
	}

	public void setAppendedPrefix4MapperClass(String appendedPrefix4MapperClass) {
		this.appendedPrefix4MapperClass = appendedPrefix4MapperClass;
	}

	public String getMapperClassName() throws DBHelperCommonException {
		if (StringUtils.isBlank(this.getSprcifiedMapperClassName())) {
			return this.getModelName() + this.getAppendedPrefix4MapperClass();
		}
		return this.getSprcifiedMapperClassName();
	}

	public String getSprcifiedMapperClassName() {
		return sprcifiedMapperClassName;
	}

	public void setSprcifiedMapperClassName(String sprcifiedMapperClassName) {
		this.sprcifiedMapperClassName = sprcifiedMapperClassName;
	}

	public String getMapperClassPackageName() {
		return mapperClassPackageName;
	}

	public void setMapperClassPackageName(String mapperClassPackageName) {
		this.mapperClassPackageName = mapperClassPackageName;
	}

	public void setSprcifiedModelName(String sprcifiedModelName) {
		this.sprcifiedModelName = sprcifiedModelName;
	}

	public String getSprcifiedModelName() {
		return sprcifiedModelName;
	}

	public String getModelName() throws DBHelperCommonException {

		if (StringUtils.isBlank(this.getSprcifiedModelName())) {
			return this.namingStrategy.getClassName(this.getTableNameTrimmed());
		}
		return this.getSprcifiedModelName();

	}

	private String getTableNameTrimmed() {
		if (StringUtils.isBlank(this.tableName))
			return null;
		return this.getAppendedPrefix() + tableName.replaceFirst(this.getTrimmedPrefix(), "") + this.getAppendedSufix();
	}

	public void setNamingStrategy(NamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
	}

	public NamingStrategy getNamingStrategy() {
		return namingStrategy;
	}

	public TableConfig getTableConfig() {
		return tableConfig;
	}

	public void setTableConfig(TableConfig tableConfig) {
		this.tableConfig = tableConfig;
	}

	public TableInfo tableConfig(TableConfig tableConfig) {
		this.tableConfig = tableConfig;
		return this;
	}

	public String getModelPackageName() {
		return modelPackageName;
	}

	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}

	public Boolean getGenerateChainStyleStterMethod() {
		return generateChainStyleStterMethod;
	}

	public void setGenerateChainStyleStterMethod(Boolean generateChainStyleStterMethod) {
		if (generateChainStyleStterMethod != null)
			this.generateChainStyleStterMethod = generateChainStyleStterMethod;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String name) {
		this.tableName = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<ColumnInfo> getCols() {
		return cols;
	}

	public void setCols(List<ColumnInfo> cols) {
		this.cols = cols;
	}

	public String getTrimmedPrefix() {
		return trimmedPrefix;
	}

	public void setTrimmedPrefix(String trimmedPrefix) {
		if (trimmedPrefix != null)
			this.trimmedPrefix = trimmedPrefix;
	}

	public String getAppendedPrefix() {
		return appendedPrefix;
	}

	public void setAppendedPrefix(String appendedPrefix) {
		if (appendedPrefix != null)
			this.appendedPrefix = appendedPrefix;
	}

	public String getAppendedSufix() {
		return appendedSufix;
	}

	public void setAppendedSufix(String appendedSufix) {
		if (appendedSufix != null)
			this.appendedSufix = appendedSufix;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, true);
	}

}
