package com.replad.init;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.replad.bean.work.WorkBean;
import com.replad.controllers.ActionHandler;
import com.replad.mail.MailConfigurationBean;
import com.replad.utils.LoadProperties;

public class InitConfiguration implements ServletContextListener {
	static String configPath = "";
	public static List<WorkBean> workDetailList = null;
	public static Map<String, String> commonPropertiesMap= null;
	public static Map<String, MailConfigurationBean> mailSettingList = null;
	
	private static final Logger log = Logger.getLogger(ActionHandler.class.getName());	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String contextPath = context.getRealPath("");
		configPath = contextPath+ File.separator + "WEB-INF"+ File.separator + "config";
        ConfigPath.CONFIG_PATH.setConfigPath(configPath);
        // Work and Subwork details..
        workDetailList = BuildWorkDetailsSingleton.INSTANCE.getWorkDetails();
        this.commonPropertiesMap= new LoadProperties().loadCommonProperties();
        this.mailSettingList = new MailConfigurationBean().getMailConfigurationList();
	}
	
    public enum ConfigPath {
    	CONFIG_PATH("config_path");
    	
    	private String value;
    	private ConfigPath(String value) {
    		this.value = value;
    	}
    	public String getConfigPath() {
            return value;
        }
    	public void setConfigPath(String path) {
    		CONFIG_PATH.value = path;
        }
        @Override
        public String toString() {
            return value;
        }
    }
}
