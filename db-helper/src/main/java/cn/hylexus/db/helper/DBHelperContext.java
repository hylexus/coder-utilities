package cn.hylexus.db.helper;

import java.sql.Connection;

public class DBHelperContext {
	private Connection connection;

	public DBHelperContext() {
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
