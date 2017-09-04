package cn.hylexus.db.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hylexus.db.helper.config.DBHelperContext;
import cn.hylexus.db.helper.config.GeneratorConfig;
import cn.hylexus.db.helper.config.GeneratorConfig.Globalconfig;
import cn.hylexus.db.helper.config.GeneratorConfig.MapperClassConfig;
import cn.hylexus.db.helper.config.GeneratorConfig.Modelconfig;
import cn.hylexus.db.helper.config.GeneratorConfig.TableConfig;
import cn.hylexus.db.helper.entity.TableInfo;
import cn.hylexus.db.helper.exception.DBHelperCommonException;
import cn.hylexus.db.helper.exception.NotYetImplementedException;
import cn.hylexus.db.helper.exception.UnSupportedDataTypeException;
import cn.hylexus.db.helper.utils.DBHelperUtils;
import cn.hylexus.db.helper.utils.DatabaseMetaDataAccessor;
import cn.hylexus.db.helper.utils.DefaultDatabaseMetaDataAccessor;
import cn.hylexus.db.helper.utils.XHRMap;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

public class TemplateGenerator {

	private static final Logger logger = LoggerFactory.getLogger(TemplateGenerator.class);
	private static final String template_base_directory = "src/main/resources/templates";
	private DBHelperContext context;
	private DatabaseMetaDataAccessor dataAccessor = new DefaultDatabaseMetaDataAccessor();

	public TemplateGenerator() {
	}

	public TemplateGenerator(DBHelperContext context, DatabaseMetaDataAccessor dataAccessor) {
		super();
		this.context = context;
		this.dataAccessor = dataAccessor;
	}

	public DBHelperContext getContext() {
		return context;
	}

	public void setContext(DBHelperContext context) {
		this.context = context;
	}

	public void generateTemplate(final Connection connection) throws DBHelperCommonException, IOException {
		try {

			final GeneratorConfig config = context.getConfig();
			if (config == null) {
				throw new DBHelperCommonException("配置信息为空");
			}

			// 反射名策略实现类实例
			config.getGlobalConfig().setNamingStrategy(DBHelperUtils.getNamingStrategyInstance(config.getGlobalConfig().getNamingStrategyClassName()));
			config.getModelConfig().setNamingStrategy(DBHelperUtils.getNamingStrategyInstance(config.getModelConfig().getNamingStrategyClassName()));

			final Optional<Modelconfig> modelConfig = Optional.ofNullable(config.getModelConfig());
			final Optional<Globalconfig> globalConfig = Optional.ofNullable(config.getGlobalConfig());
			final Optional<MapperClassConfig> mapperConfig = Optional.ofNullable(config.getMapperClassConfig());

			final List<TableConfig> tableConfigs = config.getTableConfig();
			for (int i = 0; i < tableConfigs.size(); i++) {
				final TableConfig tableConfig = tableConfigs.get(i);
				TableInfo info = null;
				logger.info("{}. 为数据表:{} 生成模板", i + 1, tableConfig.getTableName());
				try {
					info = this.converte2TableInfo(globalConfig, modelConfig, mapperConfig, tableConfig, connection);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Skip template generate , because an error occured on retrieve table infomation with table name <<{}>> , please check your configuration and try again .",
							tableConfig.getTableName());
					continue;
				}

				// 是否应该生成实体类
				if (globalConfig.map(Globalconfig::isGenerateModelClass).orElse(true)) {
					this.generateModelClass(globalConfig, modelConfig, tableConfig, info);
					logger.info("{}.java 实体类生成完毕", info.getModelName());
				}

				// 是否应该生成XXXMapper.java文件
				if (globalConfig.map(Globalconfig::isGenerateMybatisMapperClass).orElse(true)) {
					generateMybatisMapperClass(globalConfig, mapperConfig, info);
					logger.info("{}.java 数据访问对象生成完毕", info.getMapperClassName());
				}

				// 是否应该生成XXXMapper.xml文件
				if (globalConfig.map(Globalconfig::isGenerateMybatisXmlFile).orElse(true)) {
					generateMybatisMapperXMLFile(globalConfig, mapperConfig, info);
					logger.info("{}.xml sql文件生成完毕", info.getMapperClassName());
				}
				logger.info("");
			}

		} finally {
			// nothing to do
		}
	}

	private void generateMybatisMapperXMLFile(Optional<Globalconfig> globalConfig, Optional<MapperClassConfig> mapperConfig, TableInfo info) {
		FileWriter writer = null;
		try {

			if (info.getPrimaryKeys().size() > 1) {
				throw new NotYetImplementedException("多个主键的支持会在后续版本实现");
			}

			String modelClassPath = String.join("/", //
					globalConfig.map(Globalconfig::getBaseDir).orElseThrow(() -> new DBHelperCommonException("请指定模板位置:globalConfig.baseDir")), //
					"src/main/resources/", //
					info.getMapperClassPackageName().replaceAll("\\.", "/"), //
					info.getMapperClassName()//
			);
			File file = new File(FilenameUtils.normalize(modelClassPath + ".xml", true));

			// 不应该覆盖已经存在的实体类???
			if (!mapperConfig.map(MapperClassConfig::getOverrideIfExists).orElse(true)) {
				if (file.exists()) {
					return;
				}
			}

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			writer = new FileWriter(file);
			final Template template = this.getTemplate(DBHelperContext.tmplate_file_name_of_mapper_xml);
			final StringWriter sw = new StringWriter();
			final XHRMap dataModel = new XHRMap().kv("table", info).kv("mapperConfig", mapperConfig.get());

			template.process(dataModel, sw);
			// System.out.println(sw);
			writer.write(sw.toString());
		} catch (Exception e) {
			logger.error("Skip template generate , because an error occured on generate template :");
			e.printStackTrace();
		} finally {
			DBHelperUtils.release(writer);
		}
	}

	private void generateMybatisMapperClass(final Optional<Globalconfig> globalConfig, final Optional<MapperClassConfig> mapperConfig, TableInfo info) throws IOException {
		FileWriter writer = null;
		try {

			if (info.getPrimaryKeys().size() > 1) {
				throw new NotYetImplementedException("多个主键的支持会在后续版本实现");
			}

			String modelClassPath = String.join("/", //
					globalConfig.map(Globalconfig::getBaseDir).orElseThrow(() -> new DBHelperCommonException("请指定模板位置:globalConfig.baseDir")), //
					"src/main/java/", //
					info.getMapperClassPackageName().replaceAll("\\.", "/"), //
					info.getMapperClassName()//
			);
			File file = new File(FilenameUtils.normalize(modelClassPath + ".java", true));

			// 不应该覆盖已经存在的实体类???
			if (!mapperConfig.map(MapperClassConfig::getOverrideIfExists).orElse(true)) {
				if (file.exists()) {
					return;
				}
			}

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			writer = new FileWriter(file);
			final Template template = this.getTemplate(DBHelperContext.tmplate_file_name_of_mapper);
			final StringWriter sw = new StringWriter();
			final XHRMap dataModel = new XHRMap().kv("table", info).kv("mapperConfig", mapperConfig.get());

			template.process(dataModel, sw);
			// System.out.println(sw);
			writer.write(sw.toString());
		} catch (Exception e) {
			logger.error("Skip template generate , because an error occured on generate template :");
			e.printStackTrace();
		} finally {
			DBHelperUtils.release(writer);
		}
	}

	private void generateModelClass(final Optional<Globalconfig> globalConfig, Optional<Modelconfig> modelConfig, TableConfig tableConfig, TableInfo info) throws IOException {
		FileWriter writer = null;
		try {

			String modelClassPath = String.join("/", //
					globalConfig.map(Globalconfig::getBaseDir).orElseThrow(() -> new DBHelperCommonException("请指定模板位置:globalConfig.baseDir")), //
					"src/main/java/", //
					info.getModelPackageName().replaceAll("\\.", "/"), //
					info.getModelName()//
			);
			File file = new File(FilenameUtils.normalize(modelClassPath + ".java", true));

			// 不应该覆盖已经存在的实体类???
			if (!modelConfig.map(Modelconfig::getOverrideIfExists).orElse(true)) {
				if (file.exists()) {
					return;
				}
			}

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			writer = new FileWriter(file);
			final Template template = this.getTemplate(DBHelperContext.tmplate_file_name_of_po);
			final StringWriter sw = new StringWriter();
			final XHRMap dataModel = new XHRMap().kv("table", info);

			template.process(dataModel, sw);
			// System.out.println(sw);
			writer.write(sw.toString());
		} catch (Exception e) {
			logger.error("Skip template generate , because an error occured on generate template :");
			e.printStackTrace();
			return;
		} finally {
			DBHelperUtils.release(writer);
		}
	}

	private TableInfo converte2TableInfo(//
			final Optional<Globalconfig> globalConfig, //
			final Optional<Modelconfig> modelConfig, //
			final Optional<MapperClassConfig> mapperConfig, //
			TableConfig tableConfig, //
			final Connection connection) throws SQLException, UnSupportedDataTypeException, ClassNotFoundException, DBHelperCommonException {
		TableInfo info = this.dataAccessor.getTableInfo(connection, tableConfig.getTableName());
		// 命名策略
		info.setNamingStrategy(//
				modelConfig.map(Modelconfig::getNamingStrategy).orElse(//
						globalConfig.map(Globalconfig::getNamingStrategy).orElseThrow(() -> new DBHelperCommonException("未配置任何命名策略"))//
				)//
		);

		info.getCols().stream().forEach(col -> col.setNamingStrategy(info.getNamingStrategy()));

		// 实体类名
		if (StringUtils.isNotBlank(tableConfig.getModelName()))
			info.setSprcifiedModelName(tableConfig.getModelName());

		// 实体类包名
		if (StringUtils.isNotBlank(tableConfig.getModelPackageName())) {
			info.setModelPackageName(tableConfig.getModelPackageName());
		} else {
			info.setModelPackageName(modelConfig.map(Modelconfig::getPackageName).orElseThrow(() -> new DBHelperCommonException("请指定实体类包名")));
		}

		// DAO 包名
		if (StringUtils.isNotBlank(tableConfig.getMapperClassPackageName())) {
			info.setMapperClassPackageName(tableConfig.getMapperClassPackageName());
		} else {
			info.setMapperClassPackageName(mapperConfig.map(MapperClassConfig::getPackageName).orElseThrow(() -> new DBHelperCommonException("请指定DAO包名")));
		}

		// DAO 类名
		if (StringUtils.isNotBlank(tableConfig.getMapperClassName()))
			info.setSprcifiedMapperClassName(tableConfig.getMapperClassName());

		// 是否生成用于链式调用的setter方法
		if (tableConfig.getGenerateChainStyleStterMethod() == null) {
			info.setGenerateChainStyleStterMethod(tableConfig.getGenerateChainStyleStterMethod());
		} else {
			info.setGenerateChainStyleStterMethod(modelConfig.map(Modelconfig::getGenerateChainStyleStterMethod).orElse(false));
		}

		// 生成{实体类,XXXMapper.java}时替换指定的前缀或后缀
		info.setTrimmedPrefix(modelConfig.map(Modelconfig::getTrimmedPrefix).orElse(""));
		info.setAppendedPrefix(modelConfig.map(Modelconfig::getAppendedPrefix).orElse(""));
		info.setAppendedSufix(modelConfig.map(Modelconfig::getAppendedSufix).orElse(""));
		info.setAppendedPrefix4MapperClass(mapperConfig.map(MapperClassConfig::getAppendedSufix).orElse(""));
		return info;
	}

	private Template getTemplate(String templateName) throws IOException, TemplateNotFoundException, MalformedTemplateNameException, ParseException {
		final Version version = Configuration.VERSION_2_3_23;
		Configuration configuration = new Configuration(version);
		URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		URL templatePath = classLoader.findResource("templates");
		configuration.setDirectoryForTemplateLoading(new File(templatePath.getFile()));
		// configuration.setDirectoryForTemplateLoading(new
		// File(System.getProperty("user.dir") + "/" + template_base_directory));
		configuration.setObjectWrapper(new DefaultObjectWrapper(version));
		configuration.setDefaultEncoding("UTF-8");
		Template template = configuration.getTemplate(templateName);
		return template;
	}

}
