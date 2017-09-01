package cn.hylexus.db.helper.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.hylexus.db.helper.config.GeneratorConfig.TableConfig;
import cn.hylexus.db.helper.utils.NamingUtils;

public class TableInfo {

	private String packageName;
	private String name;
	private String comments;
	// 是否生成链式setter方法
	private Boolean generateChainStyleStterMethod = false;
	private List<ColumnInfo> cols = new ArrayList<>();
	private TableConfig tableConfig;

	// others
	private String trimmedPrefix = "";
	private String appendedPrefix = "";
	private String appendedSufix = "";

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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCamelName() {
		if (StringUtils.isBlank(this.getName()))
			return this.getName();
		return NamingUtils.underLine2Camel(this.getAppendedPrefix() + this.getName().replaceFirst(this.getTrimmedPrefix(), "") + this.getAppendedSufix(), false);
	}

	public Boolean getGenerateChainStyleStterMethod() {
		return generateChainStyleStterMethod;
	}

	public void setGenerateChainStyleStterMethod(Boolean generateChainStyleStterMethod) {
		if (generateChainStyleStterMethod != null)
			this.generateChainStyleStterMethod = generateChainStyleStterMethod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
