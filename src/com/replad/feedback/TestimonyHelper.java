package com.replad.feedback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.replad.init.InitConfiguration;
import com.replad.utils.LoadProperties;
import com.replad.utils.StringUtilities;

public class TestimonyHelper {

	public Map<String, TestimonyBean> getFeedbackDetails(){
		Map<String, TestimonyBean> fedbackMap = new HashMap<String, TestimonyBean>();
		int numberOfFeedbackToDisplay = Integer.parseInt(null!=InitConfiguration.commonPropertiesMap.get("replad.count.of.feedback.to.display") ? InitConfiguration.commonPropertiesMap.get("replad.count.of.feedback.to.display") : "4");
		TestimonyBean feedbackBean = new TestimonyBean();

		if(numberOfFeedbackToDisplay>0){
			String filepath = StringUtilities.getConfigPath()+File.separator+"feedback.properties";
			LoadProperties loadProperties = new LoadProperties(new File(filepath));
			Map<String, String> propMap = loadProperties.readProperties();
			if(!propMap.isEmpty()){
				String feedbackKey = "", signatureKey="", imageKey ="";
				for(int i=1;i<=numberOfFeedbackToDisplay;i++){
					feedbackBean = new TestimonyBean();
					feedbackKey = ""; signatureKey=""; imageKey ="";
					feedbackKey 	= "replad.feedback.comment."+i;
					signatureKey 	= "replad.feedback.signature."+i;
					imageKey 		= "replad.feedback.image."+i;

					feedbackBean.setCustomerComment(propMap.get(feedbackKey));
					feedbackBean.setCustomerSignature(propMap.get(signatureKey));
					feedbackBean.setImage("./images/testimony"+ File.separator + propMap.get(imageKey));
					
					fedbackMap.put(i+"", feedbackBean);
				}
			}
		}
		return fedbackMap;
	}
	
	public int submitUserFeedback(HttpServletRequest request){
		int status = 0;
		String feedbackEmail = (StringUtilities.isNotBlank(request.getParameter("feedbackEmail"))?request.getParameter("feedbackEmail"):null);
		String feedbackMobile = (StringUtilities.isNotBlank(request.getParameter("feedbackMobile"))?request.getParameter("feedbackMobile"):null);
		String feedbackDescription = (StringUtilities.isNotBlank(request.getParameter("feedbackDescription"))?request.getParameter("feedbackDescription"):null);
		status = new TestimonyDbUtils().submitTestimony(feedbackEmail, feedbackMobile, feedbackDescription);
		if(status == 1){
			// Send feedback email to replad admin
		}
		return status;
	}
}

