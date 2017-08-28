package cn.hylexus.db.helper.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.hylexus.db.helper.utils.NamingUtils;

public class TableInfo {

	private String packageStr;
	private String name;
	private String comments;
	// 是否生成链式setter方法
	private Boolean generateChainStyleStterMethod = true;
	private List<ColumnInfo> cols = new ArrayList<>();

	public String getPackageStr() {
		return packageStr;
	}

	public void setPackageStr(String packageStr) {
		this.packageStr = packageStr;
	}

	public String getCamelName() {
		if (StringUtils.isBlank(name))
			return name;
		return NamingUtils.underLine2Camel(name, false);
	}

	public Boolean getGenerateChainStyleStterMethod() {
		return generateChainStyleStterMethod;
	}

	public void setGenerateChainStyleStterMethod(Boolean generateChainStyleStterMethod) {
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

	@Override
	public String toString() {
		return JSON.toJSONString(this, true);
	}

}
