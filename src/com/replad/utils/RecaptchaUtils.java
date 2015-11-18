package com.replad.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import com.replad.init.InitConfiguration;
import com.replad.mail.MailConfigurationBean;

public class RecaptchaUtils {
	
	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
    private final static String USER_AGENT = "Mozilla/5.0";
    
	public static boolean verifyRecaptcha(String gRecaptchaResponse) {
		return true;
		/*if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}

		boolean status = false;
		MailConfigurationBean mailconfigurationbean = null;
		Map<String, MailConfigurationBean> mailSettingList = InitConfiguration.mailSettingList;
		if (!mailSettingList.isEmpty()
				&& mailSettingList.containsKey("localhost.recaptcha")) {
			mailconfigurationbean = mailSettingList.get("localhost.recaptcha");
		}

		String captchaPrivateKey = mailconfigurationbean.getPwd();

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + captchaPrivateKey + "&response="
					+ gRecaptchaResponse;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			JSONObject jObject = new JSONObject(response.toString());
			status = jObject.getBoolean("success");
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
	}
}
