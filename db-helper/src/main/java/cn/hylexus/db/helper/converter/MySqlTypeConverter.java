package cn.hylexus.db.helper.converter;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import cn.hylexus.db.helper.entity.JavaType;
import cn.hylexus.db.helper.exception.UnSupportedDataTypeException;

public class MySqlTypeConverter implements SqlTypeConverter {

	private static Map<Integer, JavaType> map = new HashMap<>();

	static {
		map.put(Types.INTEGER, new JavaType().typeCode(Types.INTEGER).class_(Integer.class));
		map.put(Types.BIGINT, new JavaType().typeCode(Types.BIGINT).class_(Long.class));
		map.put(Types.REAL, new JavaType().typeCode(Types.REAL).class_(Float.class));
		map.put(Types.DOUBLE, new JavaType().typeCode(Types.DOUBLE).class_(Double.class));
		map.put(Types.FLOAT, new JavaType().typeCode(Types.FLOAT).class_(Float.class));

		map.put(Types.VARCHAR, new JavaType().typeCode(Types.VARCHAR).class_(String.class));
		map.put(Types.CHAR, new JavaType().typeCode(Types.CHAR).class_(String.class));
		map.put(Types.LONGVARCHAR, new JavaType().typeCode(Types.LONGVARCHAR).class_(String.class));

		map.put(Types.VARBINARY, new JavaType().typeCode(Types.VARBINARY).class_(byte[].class).typeName("byte[]"));
		map.put(Types.LONGVARBINARY, new JavaType().typeCode(Types.LONGVARBINARY).class_(byte[].class).typeName("byte[]"));

		map.put(Types.NUMERIC, new JavaType().typeCode(Types.NUMERIC).class_(BigDecimal.class).needGenerateImportStatement(true));
		map.put(Types.DECIMAL, new JavaType().typeCode(Types.DECIMAL).class_(BigDecimal.class).needGenerateImportStatement(true));
		
		map.put(Types.BIT, new JavaType().typeCode(Types.BIT).class_(Boolean.class).needGenerateImportStatement(true).defaultValStr("false"));

		map.put(Types.DATE, new JavaType().typeCode(Types.DATE).class_(java.util.Date.class).needGenerateImportStatement(true));
		map.put(Types.TIME, new JavaType().typeCode(Types.TIME).class_(java.util.Date.class).needGenerateImportStatement(true));
		map.put(Types.TIMESTAMP, new JavaType().typeCode(Types.TIMESTAMP).class_(java.util.Date.class).needGenerateImportStatement(true));

	}

	@Override
	public JavaType convert2JavaType(int typeCode) throws UnSupportedDataTypeException {
		JavaType javaType = map.get(typeCode);

		if (javaType == null)
			throw new UnSupportedDataTypeException("typeCode=" + typeCode);

		return map.get(typeCode);
	}

	@Override
	public int convert2SqlType(JavaType javaType) throws UnSupportedDataTypeException {
		throw new UnsupportedOperationException();
	}

}
