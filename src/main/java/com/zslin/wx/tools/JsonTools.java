package com.zslin.wx.tools;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonTools {

	/**
	 * 将String数据格式化成JSONObject对象
	 * @param str JSON数据格式的String
	 * @return
	 */
	public static JSONObject str2JsonObj(String str)  {
		try {
			JSONObject jsonObj = new JSONObject(str);
			return jsonObj;
		} catch (Exception e) {
//			throw new AppException("JSON数据格式化异常", AppConstant.ExceptionCode.JSON_FORMAT_EXCEPTION);
			return null;
		}
	}
	
	/**
	 * 将Stering数据格式化成JSONArray对象
	 * @param str JSON数据格式的String
	 * @return
	 */
	public static JSONArray str2JsonArray(String str) {
		try {
			JSONArray jsonArray = new JSONArray(str);
			return jsonArray;
		} catch (Exception e) {
//			throw new AppException("JSON数据格式化异常", AppConstant.ExceptionCode.JSON_FORMAT_EXCEPTION);
			return null;
		}
	}
	
	/**
	 * 获取JSON数据中的某属性值
	 * @param jsonStr JSON字符串
	 * @param field 字段
	 * @return 返回对应的值，如果返回null则表示没有具属性
	 */
	public static String getJsonParam(String jsonStr, String field) {
		String result = null;
		try {
			if(jsonStr.startsWith("[")) {
				jsonStr = jsonStr.substring(1, jsonStr.length()-1);
			}
			JSONObject jsonObj = new JSONObject(jsonStr);
			Object obj = jsonObj.get(field);
			if(obj!=null) {result = obj.toString();}
		} catch (Exception e) {
		}
		return result;
	}
}
