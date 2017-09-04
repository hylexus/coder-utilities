package cn.hylexus.db.helper.config;

public class DBHelperContext {

	public static final String tmplate_file_name_of_po = "TemplateOfPO.tpl";
	public static final String tmplate_file_name_of_mapper = "TmplateOfMapperClassFile.tpl";
	public static final String tmplate_file_name_of_mapper_xml = "TemplateOfMapperXMLFile.tpl";

	private GeneratorConfig config;

	public DBHelperContext() {
	}

	public DBHelperContext(GeneratorConfig config) {
		super();
		this.config = config;
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

}
