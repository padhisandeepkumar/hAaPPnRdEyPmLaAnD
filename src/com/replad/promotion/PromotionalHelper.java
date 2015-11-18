package com.replad.promotion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.replad.init.InitConfiguration;
import com.replad.mail.MailBodyUI;
import com.replad.mail.SendMail;
import com.replad.utils.RandomString;

public class PromotionalHelper {

	private static final Logger log = Logger.getLogger(PromotionalHelper.class.getName());
	
	public void generatePromoCodes(int promoId){
		List<String> validPromoCodeAndCondition = new PromotionalDbUtil().getPromotionalCodeAndCondition(promoId);
		if(null != validPromoCodeAndCondition && validPromoCodeAndCondition.size()>0){
			Map<String, String> validPromoUserList = new PromotionalDbUtil().getPromotionalUserList(validPromoCodeAndCondition.get(1));
			for (Map.Entry<String, String> entry : validPromoUserList.entrySet()){
				String finalPromoCode = validPromoCodeAndCondition.get(0)+new RandomString(5).generateRandomString();
				String userName = entry.getKey();
				String emailId = entry.getValue();
				Map<String, Object> promoCodeDetailsMap = new HashMap<String, Object>();
				promoCodeDetailsMap.put("templateFor", "userPromotionCoupon");
				promoCodeDetailsMap.put("name", userName);
				promoCodeDetailsMap.put("coupon_code", finalPromoCode);
				promoCodeDetailsMap.put("promotion_start_date", validPromoCodeAndCondition.get(2));
				promoCodeDetailsMap.put("promotion_end_date", validPromoCodeAndCondition.get(3));
				
				String subject = InitConfiguration.commonPropertiesMap.get("user.promotion.coupon.code.mail.subject");
				String body = new MailBodyUI().renderEmailBody(promoCodeDetailsMap);
				boolean emailStatus = new SendMail().sendEmailToUser(emailId, subject, body);
                
				log.warning("****************Send Email From Here*****************");
				if(emailStatus){
					new PromotionalDbUtil().updatePromoCouponMapping(finalPromoCode, validPromoCodeAndCondition.get(0));
				}
			}
		}
	}
	
	public int isCouponValid(String promoCoupon){
		int isValid = 0;
		String coupnCode = new PromotionalDbUtil().getCouponCodeFromPromotionalCode(promoCoupon);
		isValid = new PromotionalDbUtil().isCouponCodeValid(coupnCode);
		return isValid;
	}
}
