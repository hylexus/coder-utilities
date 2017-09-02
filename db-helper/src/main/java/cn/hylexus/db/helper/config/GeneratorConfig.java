package cn.hylexus.db.helper.config;

import java.util.List;

import cn.hylexus.db.helper.converter.naming.NamingStrategy;

public class GeneratorConfig {

	private Jdbcconnectionconfig jdbcConnectionConfig;
	private Globalconfig globalConfig;
	private List<TableConfig> tableConfig;
	private Modelconfig modelConfig;
	private MapperClassConfig mapperClassConfig;

	public void setMapperClassConfig(MapperClassConfig mapperClassConfig) {
		this.mapperClassConfig = mapperClassConfig;
	}

	public MapperClassConfig getMapperClassConfig() {
		return mapperClassConfig;
	}

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

	public static class MapperClassConfig {
		private String packageName;
		private String appendedSufix;
		private Boolean overrideIfExists;

		private Boolean generateSelectMethodByPrimaryKey = true;
		private Boolean generateInsertMethod = true;
		private Boolean generateUpdateMethod = true;
		private Boolean generateDeleteMethodByPrimaryKey = true;
		private Boolean generateFinalAllMethod = true;

		public Boolean getGenerateInsertMethod() {
			return generateInsertMethod;
		}

		public void setGenerateInsertMethod(Boolean generateInsertMethod) {
			if (generateInsertMethod != null)
				this.generateInsertMethod = generateInsertMethod;
		}

		public Boolean getGenerateUpdateMethod() {
			return generateUpdateMethod;
		}

		public void setGenerateUpdateMethod(Boolean generateUpdateMethod) {
			if (generateUpdateMethod != null)
				this.generateUpdateMethod = generateUpdateMethod;
		}

		public Boolean getGenerateDeleteMethodByPrimaryKey() {
			return generateDeleteMethodByPrimaryKey;
		}

		public void setGenerateDeleteMethodByPrimaryKey(Boolean generateDeleteMethodByPrimaryKey) {
			if (generateDeleteMethodByPrimaryKey != null)
				this.generateDeleteMethodByPrimaryKey = generateDeleteMethodByPrimaryKey;
		}

		public Boolean getGenerateFinalAllMethod() {
			return generateFinalAllMethod;
		}

		public void setGenerateFinalAllMethod(Boolean generateFinalAllMethod) {
			if (generateFinalAllMethod != null)
				this.generateFinalAllMethod = generateFinalAllMethod;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		public String getAppendedSufix() {
			return appendedSufix;
		}

		public void setAppendedSufix(String appendedSufix) {
			this.appendedSufix = appendedSufix;
		}

		public Boolean getOverrideIfExists() {
			return overrideIfExists;
		}

		public void setOverrideIfExists(Boolean overrideIfExists) {
			this.overrideIfExists = overrideIfExists;
		}

		public Boolean getGenerateSelectMethodByPrimaryKey() {
			return generateSelectMethodByPrimaryKey;
		}

		public void setGenerateSelectMethodByPrimaryKey(Boolean generateSelectMethodByPrimaryKey) {
			if (generateSelectMethodByPrimaryKey != null)
				this.generateSelectMethodByPrimaryKey = generateSelectMethodByPrimaryKey;
		}

	}

	public static class Modelconfig {

		private String packageName;
		private String trimmedPrefix;
		private String appendedPrefix;
		private String appendedSufix;
		private Boolean generateChainStyleStterMethod;
		private Boolean overrideIfExists = true;
		private String namingStrategyClassName;
		private NamingStrategy namingStrategy;

		public String getNamingStrategyClassName() {
			return namingStrategyClassName;
		}

		public void setNamingStrategyClassName(String namingStrategyClassName) {
			this.namingStrategyClassName = namingStrategyClassName;
		}

		public NamingStrategy getNamingStrategy() {
			return namingStrategy;
		}

		public void setNamingStrategy(NamingStrategy namingStrategy) {
			this.namingStrategy = namingStrategy;
		}

		public Boolean getOverrideIfExists() {
			return overrideIfExists;
		}

		public void setOverrideIfExists(Boolean overrideIfExists) {
			this.overrideIfExists = overrideIfExists;
		}

		public Boolean getGenerateChainStyleStterMethod() {
			return generateChainStyleStterMethod;
		}

		public void setGenerateChainStyleStterMethod(Boolean generateChainStyleStterMethod) {
			this.generateChainStyleStterMethod = generateChainStyleStterMethod;
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

		public String getAppendedSufix() {
			return appendedSufix;
		}

		public void setAppendedSufix(String appendedSufix) {
			if (appendedSufix != null)
				this.appendedSufix = appendedSufix;
		}

	}

	public static class TableConfig {

		// table
		private String tableName;

		// model
		private String modelPackageName;
		private String modelName;
		private Boolean generateChainStyleStterMethod = null;

		// xxxMapper.java
		private String mapperClassPackageName;
		private String mapperClassName;

		public String getMapperClassPackageName() {
			return mapperClassPackageName;
		}

		public void setMapperClassPackageName(String mapperClassPackageName) {
			this.mapperClassPackageName = mapperClassPackageName;
		}

		public Boolean getGenerateChainStyleStterMethod() {
			return generateChainStyleStterMethod;
		}

		public void setGenerateChainStyleStterMethod(Boolean generateChainStyleStterMethod) {
			if (generateChainStyleStterMethod != null)
				this.generateChainStyleStterMethod = generateChainStyleStterMethod;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String name) {
			this.tableName = name;
		}

		public String getModelPackageName() {
			return modelPackageName;
		}

		public void setModelPackageName(String modelPackageName) {
			this.modelPackageName = modelPackageName;
		}

		public String getModelName() {
			return modelName;
		}

		public void setModelName(String modelName) {
			this.modelName = modelName;
		}

		public String getMapperClassName() {
			return mapperClassName;
		}

		public void setMapperClassName(String mapperClassName) {
			this.mapperClassName = mapperClassName;
		}
	}

	public static class Globalconfig {

		private boolean generateModelClass = true;
		private boolean generateMybatisMapperClass = true;
		private boolean generateMybatisXmlFile = true;
		private String baseDir;
		private String namingStrategyClassName;

		private NamingStrategy namingStrategy;

		public String getNamingStrategyClassName() {
			return namingStrategyClassName;
		}

		public void setNamingStrategyClassName(String namingStrategyClassName) {
			this.namingStrategyClassName = namingStrategyClassName;
		}

		public NamingStrategy getNamingStrategy() {
			return namingStrategy;
		}

		public void setNamingStrategy(NamingStrategy namingStrategy) {
			this.namingStrategy = namingStrategy;
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

		public String getBaseDir() {
			return baseDir;
		}

		public void setBaseDir(String baseDir) {
			this.baseDir = baseDir;
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
