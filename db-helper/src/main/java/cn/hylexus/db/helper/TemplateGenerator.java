package cn.hylexus.db.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hylexus.db.helper.config.GeneratorConfig;
import cn.hylexus.db.helper.config.GeneratorConfig.Globalconfig;
import cn.hylexus.db.helper.config.GeneratorConfig.Modelconfig;
import cn.hylexus.db.helper.config.GeneratorConfig.TableConfig;
import cn.hylexus.db.helper.entity.TableInfo;
import cn.hylexus.db.helper.exception.DBHelperCommonException;
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

	public void generateTemplate() throws DBHelperCommonException, IOException {
		try {

			final GeneratorConfig config = context.getConfig();
			if (config == null) {
				throw new DBHelperCommonException("配置信息为空");
			}
			final Connection connection = context.getConnection();

			final Optional<Modelconfig> modelConfig = Optional.ofNullable(config.getModelConfig());
			final Optional<Globalconfig> globalConfig = Optional.ofNullable(config.getGlobalConfig());

			final List<TableConfig> tableConfigs = config.getTableConfig();
			for (TableConfig tableConfig : tableConfigs) {
				TableInfo info = null;
				try {
					info = this.converte2TableInfo(globalConfig, modelConfig, tableConfig, connection);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Skip template generate , because an error occured on retrieve table infomation with table name <<{}>> , please check your configuration and try again .",
							tableConfig.getName());
					continue;
				}

				// 是否应该生成实体类
				if (globalConfig.map(Globalconfig::isGenerateModelClass).orElse(true)) {
					this.generateModelClass(globalConfig, modelConfig, tableConfig, info);
				}

				// 是否应该生成XXXMapper.java文件
				if (globalConfig.map(Globalconfig::isGenerateMybatisMapperClass).orElse(true)) {
					System.out.println("...");
				}
			}

		} finally {
			this.releaseResource();
		}
	}

	private void generateModelClass(final Optional<Globalconfig> globalConfig, Optional<Modelconfig> modelConfig, TableConfig tableConfig, TableInfo info) throws IOException {
		FileWriter writer = null;
		try {

			String modelClassPath = String.join("/", //
					globalConfig.map(Globalconfig::getBaseDir).orElseThrow(() -> new DBHelperCommonException("请指定模板位置:globalConfig.baseDir")), //
					"src/main/java/", //
					info.getPackageName().replaceAll("\\.", "/"), //
					info.getCamelName()//
			);
			File file = new File(FilenameUtils.normalize(modelClassPath + ".java", true));

			// 不应该覆盖已经存在的实体类???
			if (!DBHelperUtils.shouldBeOverride(//
					globalConfig.map(Globalconfig::getOverrideModelIfExists).orElse(null), //
					modelConfig.map(Modelconfig::getOverrideIfExists).orElse(true))) {
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
			System.out.println(sw);
			writer.write(sw.toString());
		} catch (Exception e) {
			logger.error("Skip template generate , because an error occured on generate template :");
			e.printStackTrace();
			return;
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private TableInfo converte2TableInfo(final Optional<Globalconfig> globalConfig, final Optional<Modelconfig> modelConfig, TableConfig tableConfig, final Connection connection)
			throws SQLException, UnSupportedDataTypeException, ClassNotFoundException, DBHelperCommonException {
		TableInfo info;
		info = this.dataAccessor.getTableInfo(connection, tableConfig.getName());

		// 是否应该覆盖已经存在的实体类 ?
		if (StringUtils.isNotBlank(tableConfig.getPackageName())) {
			info.setPackageName(tableConfig.getPackageName());
		} else {
			info.setPackageName(globalConfig.map(Globalconfig::getModelPackageName).orElseThrow(() -> new DBHelperCommonException("请指定实体类包名")));
		}

		// 是否生成用于链式调用的setter方法
		if (tableConfig.getGenerateChainStyleStterMethod() == null) {
			info.setGenerateChainStyleStterMethod(tableConfig.getGenerateChainStyleStterMethod());
		} else {
			info.setGenerateChainStyleStterMethod(modelConfig.map(Modelconfig::getGenerateChainStyleStterMethod).orElse(false));
		}

		// 生成实体类时替换指定的前缀或后缀
		info.setTrimmedPrefix(modelConfig.map(Modelconfig::getTrimmedPrefix).orElse(""));
		info.setAppendedPrefix(modelConfig.map(Modelconfig::getAppendedPrefix).orElse(""));
		info.setAppendedSufix(modelConfig.map(Modelconfig::getAppendedSufix).orElse(""));
		return info;
	}

	private Template getTemplate(String templateName) throws IOException, TemplateNotFoundException, MalformedTemplateNameException, ParseException {
		final Version version = Configuration.VERSION_2_3_23;
		Configuration configuration = new Configuration(version);
		configuration.setDirectoryForTemplateLoading(new File(template_base_directory));
		configuration.setObjectWrapper(new DefaultObjectWrapper(version));
		configuration.setDefaultEncoding("UTF-8");
		Template template = configuration.getTemplate(templateName);
		return template;
	}

	private void releaseResource() {
		if (this.context != null) {
			if (this.context.getConnection() != null) {
				try {
					this.context.getConnection().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
