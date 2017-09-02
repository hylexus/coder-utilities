package cn.hylexus.db.helper.converter.naming;

import org.apache.commons.lang3.StringUtils;

import cn.hylexus.db.helper.exception.DBHelperCommonException;
import cn.hylexus.db.helper.utils.NamingUtils;

public class CamelNamingStrategy implements NamingStrategy {

	@Override
	public String getClassName(String tableName) throws DBHelperCommonException {
		if (StringUtils.isBlank(tableName))
			throw new DBHelperCommonException("名称转换异常");
		return NamingUtils.underLine2Camel(tableName, false);
	}

	@Override
	public String getFieldName(String columnName) throws DBHelperCommonException {
		if (StringUtils.isBlank(columnName))
			throw new DBHelperCommonException("名称转换异常");
		return NamingUtils.underLine2Camel(columnName, true);
	}

}
