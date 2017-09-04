package cn.hylexus.db.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.hylexus.db.helper.config.DBHelperContext;
import cn.hylexus.db.helper.config.GeneratorConfig;
import cn.hylexus.db.helper.config.GeneratorConfig.TableConfig;
import cn.hylexus.db.helper.converter.MySqlTypeConverter;
import cn.hylexus.db.helper.exception.DBHelperCommonException;
import cn.hylexus.db.helper.utils.DefaultDatabaseMetaDataAccessor;

public class TemplateGeneratorTest {

	private Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/test";
		String username = "root";
		String password = "root";
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String loadStringContentFromInputStream(InputStream in) {
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(in, Charset.forName("UTF-8"));
			br = new BufferedReader(isr);
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private TemplateGenerator generator = null;

	public TemplateGeneratorTest() {
		try {
			this.init();
		} catch (DBHelperCommonException e) {
			e.printStackTrace();
		}
	}

	private void init() throws DBHelperCommonException {
		InputStream in = TemplateGeneratorTest.class.getClassLoader().getResourceAsStream("generator-config.json");
		String str = loadStringContentFromInputStream(in);
		GeneratorConfig generatorConfig = JSON.parseObject(str, GeneratorConfig.class);

		final List<TableConfig> tableconfig = generatorConfig.getTableConfig();
		if (tableconfig == null || tableconfig.size() == 0) {
			throw new DBHelperCommonException("No table specified to generate template , please check your configuration of tableConfig and try again");
		}

		try {
			DBHelperContext context = new DBHelperContext().config(generatorConfig);
			this.generator = new TemplateGenerator(context, new DefaultDatabaseMetaDataAccessor().converter(new MySqlTypeConverter()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGenerateTemplate() {
		try {
			this.generator.generateTemplate(getConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
