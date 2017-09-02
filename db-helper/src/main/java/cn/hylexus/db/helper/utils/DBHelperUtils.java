package cn.hylexus.db.helper.utils;

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
}
