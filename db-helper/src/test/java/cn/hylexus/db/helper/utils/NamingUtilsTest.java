package cn.hylexus.db.helper.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class NamingUtilsTest {

	public static String firstChar2UpperCase(String str) {
		if (StringUtils.isBlank(str))
			return str;
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

	public String underLine2Camel(String str, boolean smallCamel) {
		if (StringUtils.isBlank(str))
			return str;
		final String ret = Arrays.asList(str.split("_")).stream().filter(StringUtils::isNoneBlank).map(NamingUtils::firstChar2UpperCase).collect(Collectors.joining());
		if (smallCamel) {
			if (StringUtils.isNotBlank(ret)) {
				return NamingUtils.firstChar2LowerCase(ret);
			}
		}
		return ret;
	}

	@Test
	public void test1() {
		System.out.println(firstChar2UpperCase("user"));
		System.out.println(firstChar2UpperCase("u"));
		String str = "user_name2_of_user";

		String string = Arrays.asList(str.split("_")).stream().filter(StringUtils::isNoneBlank).map(NamingUtils::firstChar2UpperCase).collect(Collectors.joining());
		System.out.println(string);
	}
}
