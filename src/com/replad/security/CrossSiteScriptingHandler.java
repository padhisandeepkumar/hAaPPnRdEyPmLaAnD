package com.replad.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.owasp.encoder.Encode;

/**
 * Is used to handle the various pitfalls of Cross Site Scripting attacks in the user input data.
 * The moment either the client or server receives the data it encodes them and stores.
 * Which minimizes any malicious attack into the application with the wrong data provided through the user input data.
 * 
 * @author prasmit
 *
 */
public class CrossSiteScriptingHandler {
	public static final int HTMLATTRIBUTE = 1;
	public static final int HTML = 2;
	public static final int JAVASCRIPT = 3;
	public static final int URI = 4;

	/**
	 * used to encode the value which comes along the request.
	 * 
	 * @param value
	 * @param cssType
	 * @return
	 */
	public static String encodeForXss(HttpServletRequest request, String paramName, int cssType){
		String value = (StringUtils.isNotEmpty(request.getParameter(paramName)) && !StringUtils.equalsIgnoreCase(request.getParameter(paramName), "null") ? request.getParameter(paramName) : "");
		String xssString = encodeForXss(value, cssType);

		return xssString;
	}

	@SuppressWarnings("finally")
	public static String encodeForXss(String value, int... cssType){
		if(StringUtils.isEmpty(value)){
			return value;
		}
		String xssString = value;
		
		if(null!=cssType && cssType.length==0){
			cssType = new int[1];
			cssType[0] = 1;
		}

		/**
		 * 1 is for input text fields data
		 * 2 is for text areas
		 */
		try {
			switch(cssType[0]){
								case HTMLATTRIBUTE: xssString = Encode.forHtmlAttribute(value.trim()); 
										break;
								
								case HTML: xssString = Encode.forHtml(value.trim()); 
										break;
								
								case JAVASCRIPT: xssString = Encode.forJavaScriptAttribute(value.trim()); 
										break;
										
								case URI: xssString = Encode.forUri(value.trim()); 
										break;
								
								default:break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			return xssString;
		}
	}
	
	/**
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String decodeForXss(String value, String charset){
		try {
			value = URLDecoder.decode(value, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	 private String stripXSS(String value) {
	        if (value != null) {
	            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
	            // avoid encoded attacks.
	            // value = ESAPI.encoder().canonicalize(value);

	            // Avoid null characters
	            value = value.replaceAll("", "");

	            // Avoid anything between script tags
	            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");

	            // Avoid anything in a src='...' type of expression
	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");

	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");

	            // Remove any lonesome </script> tag
	            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");

	            // Remove any lonesome <script ...> tag
	            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");

	            // Avoid eval(...) expressions
	            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");

	            // Avoid expression(...) expressions
	            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");

	            // Avoid javascript:... expressions
	            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");

	            // Avoid vbscript:... expressions
	            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");

	            // Avoid onload= expressions
	            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");
	        }
	        return value;
	    }
}
