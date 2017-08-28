package cn.hylexus.db.helper.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class NamingUtils {

	public static String firstChar2UpperCase(String str) {
		if (StringUtils.isBlank(str))
			return str;
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

	public static String firstChar2LowerCase(String str) {
		if (StringUtils.isBlank(str))
			return str;
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
	}

	public static String underLine2Camel(String str, boolean smallCamel) {
		if (StringUtils.isBlank(str))
			return str;
		
		final String ret = Arrays.asList(str.split("_"))//
				.stream()//
				.filter(StringUtils::isNoneBlank)//
				.map(NamingUtils::firstChar2UpperCase)//
				.collect(Collectors.joining());
		
		if (smallCamel) {
			if (StringUtils.isNotBlank(ret)) {
				return firstChar2LowerCase(ret);
			}
		}
		return ret;
	}
}
