package cn.hylexus.db.helper.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import cn.hylexus.db.helper.config.GeneratorConfig;
import cn.hylexus.db.helper.config.GeneratorConfig.Jdbcconnectionconfig;
import cn.hylexus.db.helper.converter.naming.NamingStrategy;
import cn.hylexus.db.helper.exception.DBHelperCommonException;

public class DBHelperUtils {

	public static Boolean shouldBeOverride(Boolean globalConfigIsTure, Boolean specificConfigIsTrue) {
		if (specificConfigIsTrue == null)
			return globalConfigIsTure == null ? false : globalConfigIsTure;
		return specificConfigIsTrue;
	}

	public static NamingStrategy getNamingStrategyInstance(String className) throws DBHelperCommonException {

		try {
			return (NamingStrategy) Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new DBHelperCommonException("命名策略解析异常:" + e.getMessage());
		}
	}

	public static void release(AutoCloseable autoCloseable) {
		if (autoCloseable != null)
			try {
				autoCloseable.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static String loadStringContentFromInputStream(InputStream in) throws IOException {
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
		} finally {
			release(br);
			release(isr);
		}
		return sb.toString();
	}

	public static Connection getConnection(GeneratorConfig generatorConfig) throws DBHelperCommonException, ClassNotFoundException {
		try {
			Optional<Jdbcconnectionconfig> config = Optional.of(generatorConfig).map(e -> e.getJdbcConnectionConfig());
			if (config.map(e -> e.getDriverClassName()).isPresent()) {
				Class.forName(config.map(e -> e.getDriverClassName()).orElse(null));
			}
			return DriverManager.getConnection(//
					config.map(e -> e.getUrl()).orElseThrow(() -> new DBHelperCommonException("未指定 jdbcConnectionConfig.url")), //
					config.map(e -> e.getUser()).orElseThrow(() -> new DBHelperCommonException("未指定 jdbcConnectionConfig.user")), //
					config.map(e -> e.getPassword()).orElseThrow(() -> new DBHelperCommonException("未指定 jdbcConnectionConfig.user")) //
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
