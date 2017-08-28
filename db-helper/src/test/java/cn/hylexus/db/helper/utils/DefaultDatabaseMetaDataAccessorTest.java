package cn.hylexus.db.helper.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.hylexus.db.helper.DBHelperContext;
import cn.hylexus.db.helper.converter.MySqlTypeConverter;
import cn.hylexus.db.helper.entity.TableInfo;
import cn.hylexus.db.helper.exception.UnSupportedDataTypeException;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

public class DefaultDatabaseMetaDataAccessorTest {

	private DefaultDatabaseMetaDataAccessor accessor;
	private Connection conn;

	private void initConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/test";
		String username = "root";
		String password = "root";
		this.conn = DriverManager.getConnection(url, username, password);
	}

	@Before
	public void init() throws SQLException {
		this.initConnection();
		this.accessor = new DefaultDatabaseMetaDataAccessor();
		this.accessor.setConverter(new MySqlTypeConverter());
		this.accessor.setContext(new DBHelperContext().connection(this.conn));
	}

	@After
	public void destroy() throws SQLException {
		if (this.conn != null)
			conn.close();
	}

	@Test
	public void test() {
		try {
			TableInfo tableInfo = this.accessor.getTableInfo("test", "t_user");
			System.out.println(tableInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Template getTemplate(String templateName) throws IOException, TemplateNotFoundException, MalformedTemplateNameException, ParseException {
		final Version version = Configuration.VERSION_2_3_23;
		Configuration configuration = new Configuration(version);
		String directory = "src/main/resources/templates";
		configuration.setDirectoryForTemplateLoading(new File(directory));
		configuration.setObjectWrapper(new DefaultObjectWrapper(version));
		configuration.setDefaultEncoding("UTF-8"); // 这个一定要设置，不然在生成的页面中 会乱码
		// 获取或创建一个模版。
		Template template = configuration.getTemplate(templateName);
		return template;
	}

	@Test
	public void test1() {
		try {
			TableInfo tableInfo = this.accessor.getTableInfo("test", "t_user");
			tableInfo.setPackageStr("cn.hylexus.app.entity");
			tableInfo.getCols().stream().forEach(System.out::println);
			StringWriter sw = new StringWriter();
			XHRMap dataModel = new XHRMap()//
					.kv("table", tableInfo)//
					;
			
			this.getTemplate("TemplateOfPO.tpl").process(dataModel, sw);
			System.out.println(sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
