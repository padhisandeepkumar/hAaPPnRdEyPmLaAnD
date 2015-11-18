package com.replad.security;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.digest.StandardStringDigester;
import org.jasypt.util.password.PasswordEncryptor;

import com.replad.helper.user.registration.UserVO;
import com.replad.init.InitConfiguration;
import com.replad.user.creation.UserPasswordEncryptor;

public class PasswordValidator {
	
	/**
	 * Generating a 6character long random alphanumeric value which considered as default password on registration.
	 * 
	 * @return
	 */
	public String generateDefaultPassword(){
		Map<String, String> commonProperties = InitConfiguration.commonPropertiesMap;
		int pwdLength = Integer.parseInt(null!=commonProperties.get("default.password.length") ? commonProperties.get("default.password.length") : "6");
		String randomPwd = RandomStringUtils.randomAlphanumeric(pwdLength).trim();
		return randomPwd;
	}
	
	/**
	 * Get the salt for password encryption, it is a random alphanumeric.
	 * @return
	 */
	public String generateSalt(){
		String saltKey = RandomStringUtils.randomAlphanumeric(28).trim();
		return saltKey;
	}
	
	public Map<String, String> generateRandomPassword(String password){
		Map<String, String> map = null;
		try {
			String salt = generateSalt();
			String finalPwd = getFinalEncryptedPassword(salt, password);
			map = new HashMap<String, String>();
			map.put("RANDPWD", password);
			map.put("SALT", salt);
			map.put("FINALPWD", finalPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String, String> generateRandomPassword(){
		Map<String, String> map = null;
		try {
			String randPwd = generateDefaultPassword();
			String salt = generateSalt();
			String finalPwd = getFinalEncryptedPassword(salt, randPwd);
//			System.out.println("On user creation----salt--["+salt+"]--randPwd--["+randPwd+"]----finalPwd--["+finalPwd+"]");
			map = new HashMap<String, String>();
			map.put("RANDPWD", randPwd);
			map.put("SALT", salt);
			map.put("FINALPWD", finalPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * It returns the encrypted password for saltkey+password
	 * 
	 * @param saltKey
	 * @param password
	 * @param passwordEncryptor
	 * @return
	 */
	public String getFinalEncryptedPassword(String saltKey, String password){
		String encryptedPwd = "";
		try {
			String userPassword = getFinalPassword(saltKey, password);
			PasswordEncryptor passwordEncryptor = UserPasswordEncryptor.getPasswordEncryptor();
			encryptedPwd = passwordEncryptor.encryptPassword(userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedPwd;
	}
	
	public String getFinalPassword(String saltKey, String password){
		String finalPassword = (saltKey+password);
		return finalPassword;
	}
	
	/**
	 * Validate the logged in user password
	 * 
	 * @param vo is the value object of the logged in user
	 * @param password user inout logging in password
	 * @param pwdEncType password encryption type
	 * @return true if matched else false
	 */
	public boolean validatePassword(UserVO vo, String password){
		boolean flag = false;
		String salt = vo.getSaltkey();
		String dbPassword = vo.getPassword();
		PasswordEncryptor passwordEncryptor = UserPasswordEncryptor.getPasswordEncryptor();
		password = (salt+password);
		if(passwordEncryptor.checkPassword(password,dbPassword)){
			System.out.println("password matched---------------------");
			flag = true;
		}else{
			System.out.println("***password not matched---------------------");
		}
		return flag;
	}
	
	public StandardStringDigester getDigester(){
		StandardStringDigester digester = new StandardStringDigester();
		digester.setAlgorithm("SHA-256");   // optionally set the algorithm
		digester.setIterations(100000);  // increase security by performing 50000 hashing iterations
		digester.setSaltSizeBytes(16);
		return digester;
	}
}
