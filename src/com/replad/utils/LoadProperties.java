package com.replad.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import com.replad.init.InitConfiguration;

public class LoadProperties {
	private static final Logger log = Logger.getLogger(LoadProperties.class.getName());	
	private File file = null; //File name will full path
	public Map<String, String> map=null;
	public LoadProperties(){
	}
	public LoadProperties(File file){
		this.file = file;
	}
	public Map<String, String> readProperties() {
		Map<String, String> propMap = new HashMap<String, String>();
		String key="", val="";
		try {
			FileReader inputFileReader = new FileReader(file);
			BufferedReader inputStream = new BufferedReader(inputFileReader);
			String inLine = null;

			while ((inLine = inputStream.readLine()) != null) {
				int index = inLine.indexOf("=");
				if (index > 1) {
					key = (inLine.substring(0, index)).trim().toLowerCase();
					val = (inLine.substring(index + 1)).trim();
					if(!key.trim().startsWith("#"))
						propMap.put(key,val);
				}
			}
			inputStream.close();
		} catch (IOException ioe) {
			propMap = null;
			log.warning("I/O Exception. Reading PROP File : No file existed");
		}
		return propMap;
	}

	public Map<String, String> loadCommonProperties(){
		try {
			String configPath = InitConfiguration.ConfigPath.CONFIG_PATH.getConfigPath();
//			File file = new File("E:/Rasmit/Codebase/prim/WEB-INF/src/com/props/common.properties"); //File name will full path
			File file = new File(configPath+File.separator+"common.properties"); //File name will full path
			map = new LoadProperties(file).readProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public String getPropValue(String key){
		String value = "";
		if(null==map){
			map = loadCommonProperties();
		}
		value = map.get(key);
		return value;
	}
	
	public Properties getProperties()throws IOException {
		Properties prop = new Properties();
		InputStream is = new FileInputStream(file);
		prop.load(is);
		/*for(Object key : prop.keySet()){
			System.out.println(key.toString()+"<--********-->"+prop.getProperty(key.toString()));
		}*/
		return prop;
	}
}
