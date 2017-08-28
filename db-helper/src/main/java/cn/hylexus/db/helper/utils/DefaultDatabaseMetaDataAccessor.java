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
		try {
			final DatabaseMetaData metaData = connection.getMetaData();

			columnSet = metaData.getColumns(null, null, tableName, null);

			// 获取列信息
			final List<ColumnInfo> cols = getColumnInfo(columnSet);

			final TableInfo ret = new TableInfo();
			ret.setName(tableName);
			ret.setComments(null);
			ret.setCols(cols);
			return ret;
		} finally {
			// connection的关闭由调用方自行关闭
			if (columnSet != null) {
				columnSet.close();
			}
		}
	}

	private List<ColumnInfo> getColumnInfo(ResultSet set) throws SQLException, UnSupportedDataTypeException {
		final List<ColumnInfo> cols = new ArrayList<>();
		while (set.next()) {
			ColumnInfo info = new ColumnInfo();
			info.setName(set.getString(COLUMN_PROP_COLUMN_NAME));
			info.setPrecision(set.getInt(COLUMN_PROP_DECIMAL_DIGITS));
			info.setRemark(set.getString(COLUMN_PROP_REMARKS));
			info.setTypeCode(set.getInt(COLUMN_PROP_DATA_TYPE));
			info.setLength(set.getInt(COLUMN_PROP_COLUMN_SIZE));
			JavaType javaType = this.converter.convert2JavaType(info.getTypeCode());
			info.setJavaType(javaType);
			cols.add(info);
		}
		return cols;
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
