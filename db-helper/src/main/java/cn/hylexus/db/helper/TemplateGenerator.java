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
import cn.hylexus.db.helper.config.GeneratorConfig.TableConfig;
import cn.hylexus.db.helper.entity.TableInfo;
import cn.hylexus.db.helper.exception.DBHelperCommonException;
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
			final Connection connection = context.getConnection();

			List<TableConfig> tableconfig = config.getTableConfig();
			for (TableConfig table : tableconfig) {
				TableInfo info = null;
				try {
					info = this.dataAccessor.getTableInfo(connection, table.getName());

					// override entities location configuration ?
					if (StringUtils.isNotBlank(table.getPackageName())) {
						info.setPackageName(table.getPackageName());
					} else {
						info.setPackageName(config.getGlobalConfig().getModelPackageName());
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Skip template generate , because an error occured on retrieve table infomation with table name <<{}>> , please check your configuration and try again .",
							table.getName());
					continue;
				}

				FileWriter writer = null;

				try {

					final String path = FilenameUtils
							.normalize(config.getGlobalConfig().getBaseDir() + "/" + "src/main/java/" + info.getPackageName().replaceAll("\\.", "/") + "/" + info.getCamelName() + ".java", true);
					File file = new File(path);

					// 不应该覆盖已经存在的实体类???
					if (!DBHelperUtils.shouldBeOverride(//
							Optional.ofNullable(config).map(e -> e.getGlobalConfig()).map(e -> e.isOverrideModelIfExists()).orElse(null), //
							table.isOverrideIfExists())) {
						if (file.exists()) {
							continue;
						}
					}

					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
						file.createNewFile();
					}
					writer = new FileWriter(file);
					final Template template = this.getTemplate("TemplateOfPO.tpl");
					final StringWriter sw = new StringWriter();
					final XHRMap dataModel = new XHRMap().kv("table", info);

					template.process(dataModel, sw);
					System.out.println(sw);
					writer.write(sw.toString());
				} catch (Exception e) {
					logger.error("Skip template generate , because an error occured on generate template :");
					e.printStackTrace();
					continue;
				} finally {
					if (writer != null) {
						writer.close();
					}
				}
			}

		} finally {
			this.releaseResource();
		}
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
