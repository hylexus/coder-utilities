package cn.hylexus.db.helper.config;

import java.util.List;

public class GeneratorConfig {

	private Jdbcconnectionconfig jdbcconnectionconfig;
	private Globalconfig globalconfig;
	private List<Tableconfig> tableconfig;
	private Modelconfig modelconfig;

	public void setJdbcconnectionconfig(Jdbcconnectionconfig jdbcconnectionconfig) {
		this.jdbcconnectionconfig = jdbcconnectionconfig;
	}

	public Jdbcconnectionconfig getJdbcconnectionconfig() {
		return jdbcconnectionconfig;
	}

	public void setGlobalconfig(Globalconfig globalconfig) {
		this.globalconfig = globalconfig;
	}

	public Globalconfig getGlobalconfig() {
		return globalconfig;
	}

	public void setTableconfig(List<Tableconfig> tableconfig) {
		this.tableconfig = tableconfig;
	}

	public List<Tableconfig> getTableconfig() {
		return tableconfig;
	}

	public void setModelconfig(Modelconfig modelconfig) {
		this.modelconfig = modelconfig;
	}

	public Modelconfig getModelconfig() {
		return modelconfig;
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

	public static class Tableconfig {

		private String name;
		private String packageName;
		private String trimmedPrefix;
		private String appendedPrefix;
		private String trimmedSufix;
		private String appendedSufix;

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
