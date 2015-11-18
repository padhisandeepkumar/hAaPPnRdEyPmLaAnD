package com.replad.utils;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.replad.init.InitConfiguration.ConfigPath;

public class StringUtilities extends StringUtils{
	/*public boolean isEmpty(String value){
		return StringUtils.isEmpty(value);
	}
	
	public boolean isNotEmpty(String value){
		return StringUtils.isNotEmpty(value);
	}
	
	public boolean isBlank(String value){
		return StringUtils.isBlank(value);
	}
	
	public boolean isNotBlank(String value){
		return StringUtils.isNotBlank(value);
	}
	
	public boolean equalsIgnoreCase(String value, String match){
		return StringUtils.equalsIgnoreCase(value, match);
	}
	
	public boolean equals(String value, String match){
		return StringUtils.equals(value, match);
	}*/
	
	/**
	 * Converts the Map<String, String> to JSON string
	 */
	public String getJsonObject(Map<String, String> map){
		if(null==map){
			return "";
		}
		try {
			JSONObject jo = new JSONObject();
			for(Map.Entry<String, String> entry : map.entrySet()){
				jo.put(entry.getKey(), entry.getValue());
			}
			return (jo.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getJsonObject(List<Map<String, String>> list){
		if(null==list){
			return "";
		}
		try {
			JSONArray jsonArr = new JSONArray();
			JSONObject jo = null;
			String id = "";
			for(Map<String, String> innerMap : list){
				jo = new JSONObject();
				for(Map.Entry<String, String> entry1 : innerMap.entrySet()){
					jo.put(entry1.getKey(), entry1.getValue());
				}
				jsonArr.put(jo);
			}
			return (jsonArr.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	
	
	public static String getConfigPath(){
		String path = "";
		EnumSet cp = EnumSet.allOf(ConfigPath.class);
		for(Object d: cp){
			ConfigPath c = (ConfigPath) d;
			if(equalsIgnoreCase(c.name(), "CONFIG_PATH")){
				path = c.getConfigPath();
			}
		}
		return path;
	}
	
	/*public void closeQuitely(BufferedReader inputStream){
		IOUtils.closeQuietly(inputStream);
	}*/
	
	/**
	 * Building the json object for AutoComplte. 
	 * Refer:registration page
	 * 
	 * @param states
	 * @return
	 */
	public String getJsonString4AutoComplete(Map<String,String> states, boolean...bs ){
		JSONArray jsonArr = new JSONArray();
		JSONObject json = null;
		try{
			if(null!=bs && bs.length>0 && bs[0]){
				json = new JSONObject();
				json.put("value", "-1");
				json.put("label", "Select");
				jsonArr.put(json);
			}
			for(String key : states.keySet()){
				json = new JSONObject();
				new StringUtilities();
				json.put("value", (StringUtils.isNotEmpty(key.toString()) ? key.toString() : ""));
				new StringUtilities();
				json.put("label", (StringUtils.isNotEmpty(states.get(key)) ? states.get(key) : ""));
				jsonArr.put(json);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return (jsonArr.toString());
	}
}
