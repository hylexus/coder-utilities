package cn.hylexus.db.helper.converter.naming;

import cn.hylexus.db.helper.exception.DBHelperCommonException;

public interface NamingStrategy {

	String getClassName(String tableName) throws DBHelperCommonException;

	String getFieldName(String columnName) throws DBHelperCommonException;

}
