package cn.hylexus.db.helper.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hylexus.db.helper.converter.SqlTypeConverter;
import cn.hylexus.db.helper.entity.ColumnInfo;
import cn.hylexus.db.helper.entity.JavaType;
import cn.hylexus.db.helper.entity.TableInfo;
import cn.hylexus.db.helper.exception.UnSupportedDataTypeException;

public class DefaultDatabaseMetaDataAccessor implements DatabaseMetaDataAccessor {

	private SqlTypeConverter converter;

	public DefaultDatabaseMetaDataAccessor() {
	}

	public DefaultDatabaseMetaDataAccessor(SqlTypeConverter converter) {
		super();
		this.converter = converter;
	}

	@Override
	public TableInfo getTableInfo(Connection connection, String tableName) throws SQLException, UnSupportedDataTypeException, ClassNotFoundException {
		ResultSet columnSet = null;
		ResultSet pkSet = null;
		try {
			final DatabaseMetaData metaData = connection.getMetaData();

			columnSet = metaData.getColumns(null, null, tableName, null);

			List<String> primaryKeyColNames = new ArrayList<>();
			pkSet = metaData.getPrimaryKeys(null, null, tableName);
			while (pkSet.next()) {
				try {
					primaryKeyColNames.add(pkSet.getString(COLUMN_PROP_COLUMN_NAME));
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			final TableInfo ret = new TableInfo();
			// 获取列信息
			final List<ColumnInfo> cols = getColumnInfos(columnSet, primaryKeyColNames, ret);

			ret.setTableName(tableName);
			ret.setComments(null);
			ret.setCols(cols);
			return ret;
		} finally {
			// connection的关闭由调用方自行关闭
			DBHelperUtils.release(pkSet);
			DBHelperUtils.release(columnSet);
		}
	}

	private List<ColumnInfo> getColumnInfos(ResultSet set, List<String> primaryKeyColNames, TableInfo tableInfo) throws SQLException, UnSupportedDataTypeException {
		final List<ColumnInfo> cols = new ArrayList<>();
		while (set.next()) {
			ColumnInfo info = getColumnInfo(set);
			// 主键信息
			if (primaryKeyColNames.contains(info.getName())) {
				tableInfo.addPrimaryKey(info);
			}
			cols.add(info);
		}
		return cols;
	}

	private ColumnInfo getColumnInfo(ResultSet set) throws SQLException, UnSupportedDataTypeException {
		ColumnInfo info = new ColumnInfo();
		info.setName(set.getString(COLUMN_PROP_COLUMN_NAME));
		info.setPrecision(set.getInt(COLUMN_PROP_DECIMAL_DIGITS));
		info.setRemark(set.getString(COLUMN_PROP_REMARKS));
		info.setTypeCode(set.getInt(COLUMN_PROP_DATA_TYPE));
		info.setLength(set.getInt(COLUMN_PROP_COLUMN_SIZE));
		JavaType javaType = this.converter.convert2JavaType(info.getTypeCode());
		info.setJavaType(javaType);
		return info;
	}

	public SqlTypeConverter getConverter() {
		return converter;
	}

	public void setConverter(SqlTypeConverter converter) {
		this.converter = converter;
	}

	public DefaultDatabaseMetaDataAccessor converter(SqlTypeConverter converter) {
		this.converter = converter;
		return this;
	}
}
