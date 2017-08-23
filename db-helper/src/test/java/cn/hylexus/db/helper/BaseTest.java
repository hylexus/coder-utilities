package cn.hylexus.db.helper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hylexus.db.helper.converter.MySqlTypeConverter;
import cn.hylexus.db.helper.converter.SqlTypeConverter;
import cn.hylexus.db.helper.entity.ColumnInfo;
import cn.hylexus.db.helper.entity.JavaType;
import cn.hylexus.db.helper.exception.UnSupportedDataTypeException;

import org.junit.Test;

public class BaseTest {
	private String schema;

	public Connection getConnection() {
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

	@Test
	public void test1() {
		try {
			final SqlTypeConverter converter = new MySqlTypeConverter();
			System.out.println(byte[].class.getName());
			DatabaseMetaData metaData = getConnection().getMetaData();
			ResultSet set2 = metaData.getColumns(null, schema, "t_user", null);
			while (set2.next()) {
				ColumnInfo info = new ColumnInfo();
				info.setName(set2.getString("COLUMN_NAME"));
				info.setPrecision(set2.getInt("DECIMAL_DIGITS"));
				info.setRemark(set2.getString("REMARKS"));
				info.setTypeCode(set2.getInt("DATA_TYPE"));
				info.setLength(set2.getInt("COLUMN_SIZE"));
				try {
					JavaType javaType = converter.convert2JavaType(info.getTypeCode());
					info.setJavaType(javaType);
				} catch (UnSupportedDataTypeException e) {
					e.printStackTrace();
				}
				System.out.println(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
