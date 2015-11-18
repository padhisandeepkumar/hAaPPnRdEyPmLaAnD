package com.replad.promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.replad.utils.DatabaseUtils;

public class PromotionalDbUtil {

	private static final Logger log = Logger.getLogger(PromotionalDbUtil.class.getName());
	
	@SuppressWarnings("resource")
	public Map<String, String> getPromotionalUserList(String conditionId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, String> userNameNEmailMap = new HashMap<String, String>();
		String promotionalConditionalSQL = null;
		String userListSQL = "select promo_condition from replad_promo_condition where id=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(userListSQL);
			pstmt.setString(1, conditionId);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				promotionalConditionalSQL = rs.getString(1);
			}
			pstmt.clearParameters();
			pstmt = con.prepareStatement(promotionalConditionalSQL);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				userNameNEmailMap.put(rs.getString(1), rs.getString(2));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return userNameNEmailMap;
	}

	public List<String> getPromotionalCodeAndCondition(int promoId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> promoCodeAndCondtionList = new ArrayList<String>();
		String promotioConditionSQL = "select promo_code, replad_promo_condition_id, start_date, end_date from replad_promotion_details where id=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(promotioConditionSQL);
			pstmt.setInt(1, promoId);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				promoCodeAndCondtionList.add(0, rs.getString(1));
				promoCodeAndCondtionList.add(1, Integer.toString(rs.getInt(2)));
				promoCodeAndCondtionList.add(2, rs.getString(3));
				promoCodeAndCondtionList.add(3, rs.getString(4));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return promoCodeAndCondtionList;
	}

	public void updatePromoCouponMapping(String finalPromoCode, String couponCode) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String promotioConditionSQL = "insert into replad_promo_coupon_mapping values(?, ?)";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(promotioConditionSQL);
			pstmt.setString(1, finalPromoCode);
			pstmt.setString(2, couponCode);
			pstmt.executeUpdate();
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
	}

	public String getCouponCodeFromPromotionalCode(String promoCoupon) {
		String couponCode = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String promoCouponCodeSQL = "select coupon_code from replad_promo_coupon_mapping where promo_code=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(promoCouponCodeSQL);
			pstmt.setString(1, promoCoupon);
			rs = pstmt.executeQuery();
			while(rs.next()){
				couponCode = rs.getString(1);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return couponCode;
	}

	public int isCouponCodeValid(String coupnCode) {
		int isValid = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String promoCouponCodeSQL = "select count(*) from replad_promotion_details where promo_code=? And status=1 AND start_date> toDay'sDate AND end_date< today'sDate";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(promoCouponCodeSQL);
			pstmt.setString(1, coupnCode);
			rs = pstmt.executeQuery();
			while(rs.next()){
				isValid = 1;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return isValid;
	}

}
