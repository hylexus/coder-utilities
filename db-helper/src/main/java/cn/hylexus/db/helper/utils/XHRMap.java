package cn.hylexus.db.helper.utils;

import java.util.HashMap;

public class XHRMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public XHRMap kv(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public XHRMap data(Object data) {
		super.put("data", data);
		return this;
	}
}
