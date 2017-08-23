package cn.hylexus.db.helper.converter;

import cn.hylexus.db.helper.entity.JavaType;
import cn.hylexus.db.helper.exception.UnSupportedDataTypeException;

public interface SqlTypeConverter {

	JavaType convert2JavaType(int typeCode) throws UnSupportedDataTypeException;

	int convert2SqlType(JavaType javaType) throws UnSupportedDataTypeException;
}
