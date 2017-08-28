package cn.hylexus.db.helper.utils;

import java.sql.SQLException;

import cn.hylexus.db.helper.entity.TableInfo;
import cn.hylexus.db.helper.exception.UnSupportedDataTypeException;

public interface DatabaseMetaDataAccessor {

	// 列的元数据信息
	final static String COLUMN_PROP_COLUMN_NAME = "COLUMN_NAME";
	final static String COLUMN_PROP_DECIMAL_DIGITS = "DECIMAL_DIGITS";
	final static String COLUMN_PROP_REMARKS = "REMARKS";
	final static String COLUMN_PROP_DATA_TYPE = "DATA_TYPE";
	final static String COLUMN_PROP_COLUMN_SIZE = "COLUMN_SIZE";

	// 表的元数据信息
	final static String TABLE_PROP_TABLE_TYPE_TABLE = "TABLE";
	final static String TABLE_PROP_TABLE_TYPE_VIEW = "VIEW";

	TableInfo getTableInfo(String schema, String tableName) throws SQLException, UnSupportedDataTypeException, ClassNotFoundException;

}