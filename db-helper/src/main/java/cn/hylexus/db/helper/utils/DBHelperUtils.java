package cn.hylexus.db.helper.utils;

public class DBHelperUtils {

	public static Boolean shouldBeOverride(Boolean globalConfigIsTure, Boolean specificConfigIsTrue) {
		if (specificConfigIsTrue == null)
			return globalConfigIsTure == null ? false : globalConfigIsTure;
		return specificConfigIsTrue;
	}
}
