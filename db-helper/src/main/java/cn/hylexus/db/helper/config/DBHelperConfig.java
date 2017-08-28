package cn.hylexus.db.helper.config;

import java.util.List;

import cn.hylexus.db.helper.entity.TableInfo;

public class DBHelperConfig {

	private List<String> tableList;

	public List<String> getTableList() {
		return tableList;
	}

	public void setTableList(List<String> tableList) {
		this.tableList = tableList;
	}

	public DBHelperConfig tableList(List<String> tableList) {
		this.tableList = tableList;
		return this;
	}

}
