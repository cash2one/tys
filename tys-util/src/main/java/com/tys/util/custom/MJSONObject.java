package com.tys.util.custom;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 当value为null时，改为空字符串，保证该key一直存在
 * @author Administrator
 *
 */
public class MJSONObject extends JSONObject {

	@Override
	public JSONObject put(String key, Object value) throws JSONException {
		// TODO Auto-generated method stub
		if (value == null)
			value = "";
		return super.put(key, value);
	}

}
