package cn.hylexus.db.helper;

import java.sql.Connection;

import cn.hylexus.db.helper.config.DBHelperConfig;

public class DBHelperContext {
	private Connection connection;
	private DBHelperConfig config;

	public DBHelperContext() {
	}

	public void setConfig(DBHelperConfig config) {
		this.config = config;
	}

	public DBHelperContext config(DBHelperConfig config) {
		this.config = config;
		return this;
	}

	public DBHelperConfig getConfig() {
		return config;
	}

	public DBHelperContext(Connection connection) {
		super();
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public DBHelperContext connection(Connection connection) {
		this.connection = connection;
		return this;
	}

}
