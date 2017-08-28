package cn.hylexus.db.helper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hylexus.db.helper.config.DBHelperConfig;
import cn.hylexus.db.helper.entity.TableInfo;
import cn.hylexus.db.helper.exception.DBHelperCommonException;
import cn.hylexus.db.helper.exception.UnSupportedDataTypeException;
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

	public void generateTemplate() throws DBHelperCommonException {
		try {

			final DBHelperConfig config = context.getConfig();
			final Connection connection = context.getConnection();
			final List<String> tablesList = config.getTableList();
			if (tablesList == null || tablesList.size() == 0) {
				throw new DBHelperCommonException("No tables specified to generate templates !");
			}

			for (String tableName : tablesList) {
				TableInfo info = null;
				try {
					info = this.dataAccessor.getTableInfo(connection, tableName);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Skip template generate , because an error occured on retrieve table infomation with table name <<{}>> , please check your configuration and try again .", tableName);
					continue;
				}

				try {
					final Template template = this.getTemplate("TemplateOfPO.tpl");
					final StringWriter sw = new StringWriter();
					final XHRMap dataModel = new XHRMap().kv("table", info);
					template.process(dataModel, sw);
					System.out.println(sw);
				} catch (Exception e) {
					logger.error("Skip template generate , because an error occured on generate template :");
					e.printStackTrace();
					continue;
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
