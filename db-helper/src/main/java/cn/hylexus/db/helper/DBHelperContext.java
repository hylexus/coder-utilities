package cn.hylexus.db.helper;

import java.sql.Connection;

import cn.hylexus.db.helper.config.GeneratorConfig;

public class DBHelperContext {

	public static final String tmplate_file_name_of_po = "TemplateOfPO.tpl";
	public static final String tmplate_file_name_of_mapper = "TmplateOfMapperClassFile.tpl";
	public static final String tmplate_file_name_of_mapper_xml = "TemplateOfMapperXMLFile.tpl";

	private Connection connection;
	private GeneratorConfig config;

	public DBHelperContext() {
	}

	public void setConfig(GeneratorConfig config) {
		this.config = config;
	}

	public DBHelperContext config(GeneratorConfig config) {
		this.config = config;
		return this;
	}

	public GeneratorConfig getConfig() {
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
