package cn.hylexus.db.helper.config;

import java.util.List;

public class GeneratorConfig {

	private Jdbcconnectionconfig jdbcConnectionConfig;
	private Globalconfig globalConfig;
	private List<TableConfig> tableConfig;
	private Modelconfig modelConfig;

	public Jdbcconnectionconfig getJdbcConnectionConfig() {
		return jdbcConnectionConfig;
	}

	public void setJdbcConnectionConfig(Jdbcconnectionconfig jdbcConnectionConfig) {
		this.jdbcConnectionConfig = jdbcConnectionConfig;
	}

	public Globalconfig getGlobalConfig() {
		return globalConfig;
	}

	public void setGlobalConfig(Globalconfig globalConfig) {
		this.globalConfig = globalConfig;
	}

	public List<TableConfig> getTableConfig() {
		return tableConfig;
	}

	public void setTableConfig(List<TableConfig> tableConfig) {
		this.tableConfig = tableConfig;
	}

	public Modelconfig getModelConfig() {
		return modelConfig;
	}

	public void setModelConfig(Modelconfig modelConfig) {
		this.modelConfig = modelConfig;
	}

	public static class Modelconfig {

		private String packageName;
		private String trimmedPrefix;
		private String appendedPrefix;
		private String trimmedSufix;
		private String appendedSufix;

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		public String getTrimmedPrefix() {
			return trimmedPrefix;
		}

		public void setTrimmedPrefix(String trimmedPrefix) {
			this.trimmedPrefix = trimmedPrefix;
		}

		public String getAppendedPrefix() {
			return appendedPrefix;
		}

		public void setAppendedPrefix(String appendedPrefix) {
			this.appendedPrefix = appendedPrefix;
		}

		public String getTrimmedSufix() {
			return trimmedSufix;
		}

		public void setTrimmedSufix(String trimmedSufix) {
			this.trimmedSufix = trimmedSufix;
		}

		public String getAppendedSufix() {
			return appendedSufix;
		}

		public void setAppendedSufix(String appendedSufix) {
			this.appendedSufix = appendedSufix;
		}

	}

	public static class TableConfig {

		private String name;
		private String packageName;
		private String trimmedPrefix;
		private String appendedPrefix;
		private String trimmedSufix;
		private String appendedSufix;
		private Boolean overrideIfExists;

		public Boolean isOverrideIfExists() {
			return overrideIfExists;
		}

		public void setOverrideIfExists(Boolean overrideIfExists) {
			this.overrideIfExists = overrideIfExists;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		public String getTrimmedPrefix() {
			return trimmedPrefix;
		}

		public void setTrimmedPrefix(String trimmedPrefix) {
			this.trimmedPrefix = trimmedPrefix;
		}

		public String getAppendedPrefix() {
			return appendedPrefix;
		}

		public void setAppendedPrefix(String appendedPrefix) {
			this.appendedPrefix = appendedPrefix;
		}

		public String getTrimmedSufix() {
			return trimmedSufix;
		}

		public void setTrimmedSufix(String trimmedSufix) {
			this.trimmedSufix = trimmedSufix;
		}

		public String getAppendedSufix() {
			return appendedSufix;
		}

		public void setAppendedSufix(String appendedSufix) {
			this.appendedSufix = appendedSufix;
		}

	}

	public static class Globalconfig {

		private boolean generateModelClass;
		private boolean generateMybatisMapperClass;
		private boolean generateMybatisXmlFile;
		private String baseDir;
		private String modelPackageName;
		private Boolean overrideModelIfExists;

		public Boolean isOverrideModelIfExists() {
			return overrideModelIfExists;
		}

		public void setOverrideModelIfExists(Boolean overrideModelIfExists) {
			this.overrideModelIfExists = overrideModelIfExists;
		}

		public String getBaseDir() {
			return baseDir;
		}

		public void setBaseDir(String baseDir) {
			this.baseDir = baseDir;
		}

		public String getModelPackageName() {
			return modelPackageName;
		}

		public void setModelPackageName(String modelPackageName) {
			this.modelPackageName = modelPackageName;
		}

		public boolean isGenerateModelClass() {
			return generateModelClass;
		}

		public void setGenerateModelClass(boolean generateModelClass) {
			this.generateModelClass = generateModelClass;
		}

		public boolean isGenerateMybatisMapperClass() {
			return generateMybatisMapperClass;
		}

		public void setGenerateMybatisMapperClass(boolean generateMybatisMapperClass) {
			this.generateMybatisMapperClass = generateMybatisMapperClass;
		}

		public boolean isGenerateMybatisXmlFile() {
			return generateMybatisXmlFile;
		}

		public void setGenerateMybatisXmlFile(boolean generateMybatisXmlFile) {
			this.generateMybatisXmlFile = generateMybatisXmlFile;
		}

	}

	public static class Jdbcconnectionconfig {

		private String url;
		private String user;
		private String password;
		private String driverClassName;

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUrl() {
			return url;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getUser() {
			return user;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPassword() {
			return password;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

	}
}
