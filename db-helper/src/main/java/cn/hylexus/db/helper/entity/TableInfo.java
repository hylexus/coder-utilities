package cn.hylexus.db.helper.entity;

import java.util.ArrayList;
import java.util.List;

public class TableInfo {

	private String name;
	private String comments;
	private List<ColumnInfo> cols = new ArrayList<>();

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

}
