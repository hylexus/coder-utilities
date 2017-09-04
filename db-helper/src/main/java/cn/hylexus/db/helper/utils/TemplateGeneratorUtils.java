package cn.hylexus.db.helper.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.hylexus.db.helper.TemplateGenerator;
import cn.hylexus.db.helper.config.DBHelperContext;
import cn.hylexus.db.helper.config.GeneratorConfig;
import cn.hylexus.db.helper.config.GeneratorConfig.TableConfig;
import cn.hylexus.db.helper.converter.MySqlTypeConverter;
import cn.hylexus.db.helper.exception.DBHelperCommonException;

public class TemplateGeneratorUtils {

	private static Logger logger = LoggerFactory.getLogger(TemplateGeneratorUtils.class);

	public static void main(String[] args) throws DBHelperCommonException {

		if (args == null || args.length == 0)
			throw new DBHelperCommonException("请指定配置文件路径");

		run(args[0]);
	}

	public static void run(String filePath) throws DBHelperCommonException {
		if (StringUtils.isEmpty(filePath))
			throw new DBHelperCommonException("请指定配置文件路径");
		final GeneratorConfig generatorConfig;
		try {
			generatorConfig = parseConfigFile(filePath);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new DBHelperCommonException("解析配置文件出错:" + e1.getMessage());
		}

		final List<TableConfig> tableconfig = generatorConfig.getTableConfig();
		if (tableconfig == null || tableconfig.size() == 0) {
			throw new DBHelperCommonException("No table specified to generate template , please check your configuration of tableConfig and try again");
		}

		Connection connection = null;
		try {
			logger.info("开始生成模板");

			DBHelperContext context = new DBHelperContext().config(generatorConfig);
			final TemplateGenerator generator = new TemplateGenerator(context, new DefaultDatabaseMetaDataAccessor().converter(new MySqlTypeConverter()));
			connection = DBHelperUtils.getConnection(generatorConfig);
			generator.generateTemplate(connection);

			logger.info("模板生成完毕");
		} catch (Exception e) {
			throw new DBHelperCommonException(e);
		} finally {
			DBHelperUtils.release(connection);
		}
	}

	private static GeneratorConfig parseConfigFile(String filePath) throws DBHelperCommonException, IOException {

		InputStream in = null;
		try {
			in = new FileInputStream(FilenameUtils.normalize(filePath, true));
			String str = DBHelperUtils.loadStringContentFromInputStream(in);
			return JSON.parseObject(str, GeneratorConfig.class);
		} finally {
			DBHelperUtils.release(in);
		}
	}
}
