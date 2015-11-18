package com.replad.sub.work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.repald.tickets.TicketBean;
import com.repald.tickets.Tickets;
import com.replad.init.InitConfiguration;
import com.replad.utils.DatabaseUtils;
import com.replad.utils.StringUtilities;

public class UserTicketDbUtils {

	private static final Logger log = Logger.getLogger(UserTicketDbUtils.class.getName());
	private Map<String, String> commonProperties = InitConfiguration.commonPropertiesMap;;
	
	/*public List<UserTicketDetailBean> getAllTicketBean4User(int user_id){
		List<UserTicketDetailBean> beanList = new ArrayList<UserTicketDetailBean>();
		UserTicketDetailBean bean = new UserTicketDetailBean();
		
		Connection con = null;replad_user_work_details
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.id, a.user_id, a.sub_work_id,b.sub_work_desc, a.created_date, a.closed_date, a.cancel_date, a.opted_week_end, a.priority, a.service_request_date from replad_user_work_details a, replad_sub_work_details b where a.sub_work_id=b.sub_work_id and a.user_id=? and a.cancel_date is null";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new UserTicketDetailBean();
				bean.setId(rs.getInt("id"));
				bean.setUser_id(rs.getInt("user_id"));
				bean.setSub_work_id(rs.getInt("sub_work_id"));
				bean.setSub_work_desc(rs.getString("sub_work_desc"));
				bean.setCreated_date(rs.getString("created_date"));
				bean.setServiceRequestDate(rs.getString("service_request_date"));
				
				try{
					bean.setClosed_date(rs.getString("closed_date"));
				} catch(Exception e){
					bean.setClosed_date(null);
				}
				
				try{
					bean.setCancel_date(rs.getString("cancel_date"));
				} catch(Exception e){
					bean.setCancel_date(null);
				}
				bean.setOpted_week_end(rs.getInt("opted_week_end"));
				bean.setPriority(rs.getInt("priority"));
				beanList.add(bean);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return beanList;
	}
	*/
	/**
	 * Return the work details bean for the give user,sub_work_id, and created date.
	 * 
	 * @param user_id
	 * @param sub_work_id
	 * @param created_date
	 * @return
	 */
	/*public UserTicketDetailBean getUserTicketBean(int user_id, int sub_work_id, Timestamp created_date){
		UserTicketDetailBean bean = new UserTicketDetailBean();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from replad_user_work_details where user_id=? and sub_work_id=? and created_date=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, sub_work_id);
			pstmt.setString(3, created_date);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean.setId(rs.getInt(1));
				bean.setUser_id(rs.getInt(2));
				bean.setSub_work_id(rs.getInt(3));
				bean.setCreated_date(rs.getString(4));
				bean.setService_request_date(rs.getString(5));
				bean.setClosed_date(rs.getString(6));
				bean.setCancel_date(rs.getString(7));
				bean.setOpted_week_end(rs.getInt(8));
				bean.setPriority(rs.getInt(9));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return bean;
	}*/
	
	/*public UserTicketDetailBean getUserTicketBean(int user_id, int id, String created_date){
		UserTicketDetailBean bean = new UserTicketDetailBean();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from replad_user_work_details where user_id=? and id=? and created_date=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, id);
			pstmt.setString(3, created_date);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean.setId(rs.getInt("id"));
				bean.setUser_id(rs.getInt("user_id"));
				bean.setSub_work_id(rs.getInt("sub_work_id"));
				bean.setCreated_date(rs.getString("created_date"));
				bean.setServiceRequestDate(rs.getString("service_request_date"));
				bean.setClosed_date(rs.getString("closed_date"));
				bean.setCancel_date(rs.getString("cancel_date"));
				bean.setOpted_week_end(rs.getInt("opted_week_end"));
				bean.setPriority(rs.getInt("priority"));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return bean;
	}
	*/
	/**
	 * Create a new ticket for the user.
	 * 
	 * @param bean
	 * @return
	 */
	
	/*public int createNewTicket(int user_id, String[] subworkidArr, String created_date, int opted_week_end, int priority, String service_request_date, String custAddress, String extraInfo, String promoCode){
		int status = 0;
		int discountPercentage = 0;
		int totalPrice = 0;
		UserTicketDetailBean bean = null;
 		if(!ArrayUtils.isEmpty(subworkidArr)){
// 			bean = new UserTicketHelper().createUserTicketBean(user_id, 0, created_date, null, null, opted_week_end, priority, service_request_date, custAddress, extraInfo);
			if(!ArrayUtils.isEmpty(subworkidArr)){
				for(String sub_work_id : subworkidArr){
					bean.setSubWorkId(Integer.parseInt(sub_work_id));
//					status = createUserTicket(bean);
					totalPrice += getPriceForSubWork(bean.getSubWorkId());
				}
				if(StringUtilities.isNotBlank(promoCode)){
					String couponCode = new PromotionalDbUtil().getCouponCodeFromPromotionalCode(promoCode);
					discountPercentage = getDiscountFromPromoCode(couponCode);
				}
				
				if(status>0){
					String subject = InitConfiguration.commonPropertiesMap.get("guest.service.create.subject");
					StringBuilder sb = new StringBuilder();
			        for(String str : subworkidArr) sb.append(str+",");
			        String subWorkIds = sb.toString().substring(0, sb.toString().length()-1);
					Map<String,String> map = new SubworkDbUtils().getSubworksList(subWorkIds);
					
					List<String> list = new ArrayList<String>();
					for(String subWorkId:map.keySet()){
						list.add(map.get(subWorkId));
					}
					UserVO vo = new UserDAO().getUserData(user_id);
					
					Map<String, Object> emailDtlsMap = new HashMap<String, Object>();
					emailDtlsMap.put("userName", vo.getUserName());
					emailDtlsMap.put("createdDate", bean.getCreatedDate());
					emailDtlsMap.put("serviceRequestDate", bean.getCustomerAddress());
					emailDtlsMap.put("ticketNumber", bean.getTicketId());
					emailDtlsMap.put("serviceDesc", list);
					emailDtlsMap.put("otherDtls", bean.getCustomerServiceMoreInfo());
					emailDtlsMap.put("templateFor", "newServiceRequest");
					emailDtlsMap.put("service_charges", "Rs."+totalPrice+"%");
					int totalDiscount = (StringUtilities.isNotBlank(promoCode) ? (totalPrice * discountPercentage/100) : 0);
					emailDtlsMap.put("discount", "Rs."+totalDiscount+" %");
					emailDtlsMap.put("total_amount", "Rs."+Integer.toString(totalPrice - totalDiscount));
					
					String body = new MailBodyUI().renderEmailBody(emailDtlsMap);
					new SendMail().sendEmailToUser(vo.getEmail(), subject, body);
				}
			}
 		}
 		return status;
	}
	*/
	private int getPriceForSubWork(int sub_work_id) {
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		int fee = 0;
		String feeSql = "SELECT fees FROM replad_sub_work_details WHERE sub_work_id=?";
		try {
			con = new DatabaseUtils().getConnection();
			pstmt = con.prepareStatement(feeSql);
			pstmt.setInt(1, sub_work_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				fee = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return fee;
	}

	/**
	 * Creating tickets for register user.
	 * 
	 * @param bean
	 * @return
	 */
	/*public int createUserTicket(UserTicketDetailBean bean){
		int status =0;
		PreparedStatement pstmt = null;
		Connection con = null;
		String sql = "insert into replad_user_work_details(id, user_id, sub_work_id, created_date, opted_week_end, priority, service_request_date, address, ticket_id, extra_information) values (?,?,?,?,?,?,?,?,?,?)";
		String ticketId  = "R"+"-"+new Tickets(8).getTicketId();
		try{
			con = new DatabaseUtils().getConnection();
			pstmt = con.prepareStatement(sql);
			con.setAutoCommit(false);
			String maxID = new DatabaseUtils().getMaxId("replad_user_work_details", "id");
			pstmt.setInt(1, Integer.parseInt(maxID));
			pstmt.setInt(2, bean.getId());
			pstmt.setInt(3, bean.getSubWorkId());
			pstmt.setString(4, bean.getCreatedDate());
			pstmt.setInt(5, bean.getOpted_week_end());
			pstmt.setInt(6, bean.getPriority());
			pstmt.setString(7, bean.getServiceRequestDate());
			pstmt.setString(8, bean.getRequestAtAddress());
			pstmt.setString(9, ticketId);
			pstmt.setString(10, bean.getExtraServiceInfo());
			status = pstmt.executeUpdate();
			if(status>0){
				status = 0;
				TicketBean tbean = new TicketBean();
				tbean.setTicket_id(ticketId);
				tbean.setEmployee_id(0);
				tbean.setCustomer_id(bean.getUser_id());
				status = createTicket(tbean, con);
			}
			con.commit();
			status = 1;
		} catch (NumberFormatException e) {
			status = 0;
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
			status = 0;
			if (con != null) {
				try {
					log.warning("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
		} finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
	*/
	/**
	 * Creating Guest User ticket details.
	 * 
	 * @param bean
	 * @return
	 */
	public int createCustomerTicket(UserTicketDetailBean bean){
		log.warning("inside createCustomerTicket");
		int status =0;
		PreparedStatement pstmt = null;
		Connection con = null;
		String sql = "insert into replad_guest_service_request(id,work_group_id, sub_work_id, guest_email, guest_mobile, guest_address, ticket_id, name, "
				+ "extra_information, service_request_date, price_per_hour, discount_percentage, total_price, promo_code, ticket_status, total_work_hour) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String ticketId = "G"+"-"+new Tickets(8).getTicketId();
		bean.setTicketId(ticketId);
		try{
			con = new DatabaseUtils().getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(sql);
			String maxID = new DatabaseUtils().getMaxId("replad_guest_service_request", "id");
			pstmt.setInt(1, Integer.parseInt(maxID));
			pstmt.setInt(2, bean.getWorkId());
			pstmt.setInt(3, bean.getSubWorkId());
			pstmt.setString(4, bean.getGuestEmail());
			pstmt.setString(5, bean.getCustomerMobile());
			pstmt.setString(6, bean.getCustomerAddress());
			pstmt.setString(7, bean.getTicketId());
			pstmt.setString(8, bean.getCustomerName());
			pstmt.setString(9, bean.getCustomerServiceMoreInfo());
			pstmt.setString(10, bean.getScheduleDate());
			pstmt.setDouble(11, bean.getPricePerHour());
			pstmt.setInt(12, bean.getDiscountPercentage());
			pstmt.setDouble(13, bean.getTotalPrice());
			pstmt.setString(14, bean.getPromoCode());
			pstmt.setString(15, bean.getTicketStatus());
			pstmt.setInt(16, bean.getTotalWorkHour());
			status = pstmt.executeUpdate();

			if(status>0){
				status = 0;
				TicketBean tbean = new TicketBean();
				tbean.setTicket_id(ticketId);
				tbean.setEmployee_id(0);
				tbean.setCustomer_id(0);
				createTicket(tbean, con);
			}
			con.commit();
			status = 1;

		}catch(SQLException e){
			e.printStackTrace();
			status = 0;
			if (con != null) {
				try {
					log.warning("Transaction is being rolled back");
					con.rollback();
				} catch(SQLException excep) {
					excep.printStackTrace();
				}
			}
			log.warning("exception createCustomerTicket==>"+e.getMessage());
		}catch(Exception e){
			status = 0;
			e.printStackTrace();
		}
		finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		log.warning("createCustomerTicket----before return ===>"+status);
		return status;
	}
	
	/**
	 * Close user created ticket once served.
	 * 
	 * @param bean
	 * @return
	 */
	/*public int closeUserTicket(UserTicketDetailBean bean){
		int status =0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update replad_user_work_details set closed_date=? where user_id=? and sub_work_id=? and created_date=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, new DateUtils().getCurrentDate().toString());
			pstmt.setInt(2, bean.getId());
			pstmt.setInt(3, bean.getSubWorkId());
			pstmt.setString(4, bean.getCreated_date());

			status = pstmt.executeUpdate();
			status = 1;
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return status;
	}
	*/
	/**
	 * Cancel user created ticket.
	 * 
	 * @param bean
	 * @return
	 */
	/*public int cancelUserTicket(int user_id, int id, String created_date){
		int status =0;
		UserTicketDetailBean bean = new UserTicketDbUtils().getUserTicketBean(user_id, id, created_date);
 		bean.setCancel_date(new DateUtils().getCurrentDate().toString());
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update replad_user_work_details set cancel_date=? where user_id=? and id=? and created_date=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, new DateUtils().getCurrentDate().toString());
			pstmt.setInt(2, bean.getUser_id());
			pstmt.setInt(3, bean.getId());
			pstmt.setString(4, bean.getCreated_date());

			status = pstmt.executeUpdate();
			status = 1;
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return status;
	}
	*/
	/**
	 * Re-schedule a service.
	 * 
	 * @param bean
	 * @return
	 */
	/*public int rescheduleUserTicket(UserTicketDetailBean bean){
		int status =0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update replad_user_work_details set service_request_date=? where user_id=? and sub_work_id=? and created_date=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, bean.getServiceRequestDate());
			pstmt.setInt(2, bean.getUser_id());
			pstmt.setInt(3, bean.getSub_work_id());
			pstmt.setString(4, bean.getCreated_date());

			status = pstmt.executeUpdate();
			status = 1;
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return status;
	}
	*/
	/**
	 * Counts user pending services.
	 * 
	 * @param user_id
	 * @return
	 */
	public int getUserTicketCount(int user_id){
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) count from replad_user_work_details a join replad_ticket_details b on a.ticket_id=b.ticket_id and a.user_id=? and a.cancel_date is null and a.closed_date is null";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
		}catch (Exception ex) {
			count=0;
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return count;
	}
	
	public int updateUserFeedback(int user_id, String subworkId, String feedback){
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update replad_user_work_details set feedback=? where user_id=? and sub_work_id=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, feedback);
			pstmt.setInt(2, user_id);
			pstmt.setInt(3, Integer.parseInt(subworkId));
			status = pstmt.executeUpdate();
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		
		return status;
	}
	
	/**
	 * Created a Adhic service for the guest user/unregister user.
	 * Note: If the user is already a registered user than create the ticket/service under the user account
	 * @param bean
	 * @return
	 */
	public int createGuestTicket(UserTicketDetailBean bean){
		return createCustomerTicket(bean); // Creating the service/ticket for the un-register/guest user;
	}
		
	public List<UserTicketDetailBean> getAllTickets(String ticketNumber, int categoryCombo,String employeeId, String fromDate, String toDate){
		List<UserTicketDetailBean> beanList = new ArrayList<UserTicketDetailBean>();
		UserTicketDetailBean bean = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select a.id, a.work_group_id, a.sub_work_id, a.guest_email, a.guest_mobile, a.guest_address, a.createddate, a.guest_service_more_info, a.ticket_id, name, a.extra_information, a.service_request_date, a.price_per_hour, a.discount_percentage, a.total_price, a.total_work_hour, a.mst_employee_id, c.first_name, b.work_group, a.promo_code, a.commision, a.ticket_status from replad_guest_service_request a join replad_work_group_details b on a.work_group_id=b.work_group_id  #conditional_block# LEFT join replad_employee_details c on a.mst_employee_id=c.id";
		
		int pStmtNumber=0;
		StringBuffer conditional_blaock = new StringBuffer();
		if(StringUtils.isNotBlank(ticketNumber)){
			conditional_blaock.append(" and ticket_id=?");
			pStmtNumber++;
		}
		if(categoryCombo>0){
			conditional_blaock.append(" and a.work_group_id=?");
			pStmtNumber++;
		}
		if(Integer.parseInt(employeeId)>0){
			conditional_blaock.append(" and mst_employee_id=?");
			pStmtNumber++;
		}
		
		// From and To date..
		if(StringUtilities.isNotEmpty(fromDate) && StringUtilities.isNotEmpty(toDate)){
			conditional_blaock.append(" and a.createddate between ? and ?");
			pStmtNumber+=2;
		}
		else if(StringUtilities.isNotEmpty(fromDate)){
			conditional_blaock.append(" and a.createddate>=?");
			pStmtNumber++;
		}
		else if(StringUtilities.isNotEmpty(toDate)){
			conditional_blaock.append(" and a.createddate<=?");
			pStmtNumber++;
		}
		if(null!=conditional_blaock && conditional_blaock.length()>0){
			sql = sql.replace("#conditional_blaock#", conditional_blaock);
		}
		System.out.println("SQL for getting all tickets---["+sql+"]");	
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			if(StringUtilities.isNotEmpty(toDate)){
				pstmt.setString(pStmtNumber, toDate);
				pStmtNumber--;
			}
			if(StringUtilities.isNotEmpty(fromDate)){
				pstmt.setString(pStmtNumber, fromDate);
				pStmtNumber--;
			}
			
			if(Integer.parseInt(employeeId)>0){
				pstmt.setString(pStmtNumber, employeeId);
				pStmtNumber--;

			}
			if(categoryCombo>0){
				pstmt.setInt(pStmtNumber, categoryCombo);
				pStmtNumber--;
			}
			if(StringUtils.isNotBlank(ticketNumber)){
				pstmt.setString(pStmtNumber, ticketNumber);
				pStmtNumber--;
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new UserTicketDetailBean();
				bean.setId(rs.getInt("id"));
				bean.setWorkId(Integer.parseInt(rs.getString("work_group_id")));
				bean.setSubWorkId(Integer.parseInt(rs.getString("sub_work_id")));
				bean.setCustomerEmail(rs.getString("guest_email"));
				bean.setCustomerMobile(rs.getString("guest_mobile"));
				bean.setCustomerAddress(rs.getString("guest_address"));
				bean.setCreatedDate(rs.getString("createddate"));
				bean.setCustomerServiceMoreInfo(rs.getString("guest_service_more_info"));
				bean.setTicketId(rs.getString("ticket_id"));
				bean.setCustomerName(rs.getString("name"));
				bean.setCustomerServiceMoreInfo(rs.getString("extra_information"));
				bean.setScheduleDate(rs.getString("service_request_date"));
				bean.setPricePerHour(rs.getDouble("price_per_hour"));
				bean.setDiscountPercentage(rs.getInt("discount_percentage"));
				bean.setTotalPrice(rs.getDouble("total_price"));
				bean.setTotalWorkHour(rs.getInt("total_work_hour"));
				bean.setEmployeeId(rs.getInt("mst_employee_id"));
				bean.setPromoCode(rs.getString("promo_code"));
				bean.setTicketStatus(rs.getString("ticket_status"));
				bean.setEmployeeName(rs.getString("first_name"));
				bean.setCommision(rs.getDouble("commision"));
				bean.setWorkDesc(rs.getString("work_group"));
				bean.setSubworkDesc(rs.getString(""));
				beanList.add(bean);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return beanList;
	}
	
	/**
	 * Create ticket.
	 * 
	 * @param bean
	 * @return
	 */
	public int createTicket(TicketBean bean, Connection con){
		log.warning("inside createTicket");
		int status =0;
		PreparedStatement pstmt = null;
		String sql = "insert into replad_ticket_details (id,ticket_id, employee_id, customer_id) values(?,?,?,?)";
		try{
			pstmt = con.prepareStatement(sql);
			String maxID = new DatabaseUtils().getMaxId("replad_ticket_details", "id");
			pstmt.setInt(1, Integer.parseInt(maxID));
			pstmt.setString(2, bean.getTicket_id());
			pstmt.setInt(3, bean.getEmployee_id());
			pstmt.setInt(4, bean.getCustomer_id());
			status = pstmt.executeUpdate();
		}catch (Exception ex) {
			log.warning("error createTicket="+ex.getMessage());
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(null, null, pstmt, null);
		}
		log.warning("before return createTicket--->"+status);
		return status;
	}
	
	public int deleteTicket(String ticketId){
		int status =0;
		PreparedStatement pstmt = null;
		Connection con = null;
		String sql = "delete from replad_ticket_details where ticket_id=?";
		try{
			con = new DatabaseUtils().getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ticketId);
			status = pstmt.executeUpdate();
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
	
	public Map<String, String> getAllCoustmerCountDetails() {
		Map<String, String> countMap = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String totalCustomer = commonProperties.get("replad.no.of.coustmers");
		String repeatedCustomer = commonProperties.get("replad.no.of.repeated.coustmers");
		String allWorkDetails = commonProperties.get("replad.no.of.services");
		String totalEmployee = commonProperties.get("replad.no.of.workforces");
		
		String sql = "select (select count(*) from replad_sub_work_details) as allWorkDetails, "
				+ "(select count(*) from replad_ticket_details)as total_customer, "
				+ "(select count(DISTINCT customer_id) from replad_ticket_details)as repeated_customer, "
				+ "(select count(*) from replad_employee_details)as noOfEmployees;";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				countMap.put("totalCustomer", ""+(Integer.parseInt(totalCustomer)+Integer.parseInt(rs.getString("total_customer"))));
				countMap.put("repeatedCustomer", ""+(Integer.parseInt(repeatedCustomer)+Integer.parseInt(rs.getString("repeated_customer"))));
				countMap.put("allWorkDetails", ""+(Integer.parseInt(allWorkDetails)+Integer.parseInt(rs.getString("allWorkDetails"))));
				countMap.put("totalEmployee", ""+(Integer.parseInt(totalEmployee)+Integer.parseInt(rs.getString("noOfEmployees"))));
			}
		}catch (Exception ex) {
			countMap.put("totalCustomer", ""+(Integer.parseInt(totalCustomer)));
			countMap.put("repeatedCustomer", ""+(Integer.parseInt(repeatedCustomer)));
			countMap.put("allWorkDetails", ""+(Integer.parseInt(allWorkDetails)));
			countMap.put("totalEmployee", ""+(Integer.parseInt(totalEmployee)));
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(null, null, pstmt, null);
		}
		return countMap;
	}

	public int validateCopounCode(String guestPromoCode) {
		int status = 0;
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		Date startDate = new Date();
		Date endDate = null;
		boolean copounStatus = false;
		String copounCondition = null;

		String sql = "select start_date, end_date, status, replad_promo_condition_id from replad_promotion_details WHERE promo_code=?";
		try {
			con = new DatabaseUtils().getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, guestPromoCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				startDate = rs.getDate(1);
				endDate = rs.getDate(2);
				copounStatus = rs.getBoolean(3);
				copounCondition = rs.getString(4);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			status = 0;
		} finally {
			if (null != startDate && null != endDate
					&& startDate.before(new Date())
					&& endDate.after(new Date()) && copounStatus
					&& StringUtils.isEmpty(copounCondition)) {
				status = 1;
			}
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}

	public Map<String, Object> getServicePriceDetails(UserTicketDetailBean bean) {
		Map<String, Object> servicePriceDetails = new HashMap<String, Object>();
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		int fee = 0;
		String workGroup = null;
		String subWorkGroup = null;
		int discount = 0;
		String feeSql = "SELECT s.fees, s.sub_work_desc , w.work_group FROM replad_sub_work_details s, replad_work_group_details w WHERE s.sub_work_id=? and w.work_group_id=?";
		try {
			con = new DatabaseUtils().getConnection();
			pstmt = con.prepareStatement(feeSql);
			pstmt.setInt(1, bean.getSubWorkId());
			pstmt.setInt(2, bean.getWorkId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				fee = rs.getInt(1);
				subWorkGroup = rs.getString(2);
				workGroup = rs.getString(3);
			}
			discount = getDiscountFromPromoCode(bean.getPromoCode());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			servicePriceDetails.put("name", bean.getCustomerName());
			servicePriceDetails.put("requested_date", bean.getCreatedDate().split(" ")[0]);
			servicePriceDetails.put("address", bean.getCustomerAddress());
			servicePriceDetails.put("service_details", workGroup+"/"+subWorkGroup);
			servicePriceDetails.put("other_details", bean.getCustomerServiceMoreInfo());
			servicePriceDetails.put("service_charges", Integer.toString(fee));
			int totalDiscount = (fee * discount/100);
			servicePriceDetails.put("discount", totalDiscount+" ");
			servicePriceDetails.put("total_amount", Integer.toString(fee - totalDiscount));
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return servicePriceDetails;
	}
	
	private int getDiscountFromPromoCode(String promoCode){
		int discountPercentage = 0;
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		String discountPercentageSql = "SELECT discount_percentage FROM replad_promotion_details WHERE promo_code=?";
		try {
			con = new DatabaseUtils().getConnection();
			pstmt = con.prepareStatement(discountPercentageSql);
			pstmt.setString(1, promoCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				discountPercentage = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
		}
		return discountPercentage;
	}

	public UserTicketDetailBean getSelectedTicketDetails(int selectedTicketId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserTicketDetailBean ticketDetailBean = null;
		String sql = "select id, a.work_group_id, b.work_group, a.sub_work_id, c.sub_work_desc, guest_email, guest_mobile, guest_address, createddate, guest_service_more_info, ticket_id, name, extra_information, service_request_date, price_per_hour, discount_percentage, total_price, total_work_hour, mst_employee_id, promo_code, commision from replad_guest_service_request a join replad_work_group_details b on a.work_group_id=b.work_group_id join replad_sub_work_details c on a.sub_work_id=c.sub_work_id where a.id=?";
		
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, selectedTicketId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				ticketDetailBean = new UserTicketDetailBean();
				
				ticketDetailBean.setId(rs.getInt("id"));
				ticketDetailBean.setWorkId(rs.getInt("work_group_id"));
				ticketDetailBean.setWorkDesc(rs.getString("work_group"));
				ticketDetailBean.setSubWorkId(rs.getInt("sub_work_id"));
				ticketDetailBean.setSubworkDesc(rs.getString("sub_work_desc"));
				ticketDetailBean.setCustomerEmail(rs.getString("guest_email"));
				ticketDetailBean.setCustomerMobile(rs.getString("guest_mobile"));
				ticketDetailBean.setCustomerAddress(rs.getString("guest_address"));
				ticketDetailBean.setCreatedDate(rs.getString("createddate"));
				ticketDetailBean.setCustomerServiceMoreInfo(rs.getString("guest_service_more_info"));
				ticketDetailBean.setTicketId(rs.getString("ticket_id"));
				ticketDetailBean.setCustomerName(rs.getString("name"));
				ticketDetailBean.setScheduleDate(rs.getString("service_request_date"));
				ticketDetailBean.setPricePerHour(rs.getDouble("price_per_hour"));
				ticketDetailBean.setDiscountPercentage(rs.getInt("discount_percentage"));
				ticketDetailBean.setTotalPrice(rs.getDouble("total_price"));
				ticketDetailBean.setTotalWorkHour(rs.getInt("total_work_hour"));
				ticketDetailBean.setEmployeeId(rs.getInt("mst_employee_id"));
				ticketDetailBean.setPromoCode(rs.getString("promo_code"));
				ticketDetailBean.setCommision(rs.getDouble("commision"));
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return ticketDetailBean;
	}

	public int cancelSelectedTicket(String selectedId) {
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update replad_guest_service_request set ticket_status='cancelled' where id=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(selectedId));
			status = pstmt.executeUpdate();
		} catch(SQLException e){
			status=0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}

	/**
	 * Delete the selected Tickets.
	 * 
	 * @param selectedId
	 * @return
	 */
	public int deleteSelectedTicket(String selectedId) {
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		List<String> valList = Arrays.asList(selectedId.split(","));
		String sql = "delete from replad_guest_service_request where id in (#replaceParam#)";
		DatabaseUtils databaseUtils = new DatabaseUtils();
		try{
			con = new DatabaseUtils().getConnection(); 
			String query = databaseUtils.replaceWithPlaceholder(sql, valList); 
			pstmt = con.prepareStatement(query);
			int[] sqlTypesArr = {Types.INTEGER};
			databaseUtils.setValues(pstmt, valList, sqlTypesArr);
			status = pstmt.executeUpdate();
		} catch(SQLException e){
			status=0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
	
	
	/**
	 * Close the selected Tickets.
	 * 
	 * @param selectedId
	 * @return
	 */
	public int closeSelectedTicket(String selectedId) {
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		List<String> valList = Arrays.asList(selectedId.split(","));
		String sql = "update replad_guest_service_request set closed_date=curdate(), ticket_status='Closed'  where id in (#replaceParam#)";
		DatabaseUtils databaseUtils = new DatabaseUtils();
		try{
			con = new DatabaseUtils().getConnection(); 
			String query = databaseUtils.replaceWithPlaceholder(sql, valList); 
			pstmt = con.prepareStatement(query);
			int[] sqlTypesArr = {Types.INTEGER};
			System.out.println(pstmt);
			databaseUtils.setValues(pstmt, valList, sqlTypesArr);
			status = pstmt.executeUpdate();
		} catch(SQLException e){
			status=0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
	public int updateTicketDetails(int ticketId, Double totalPrice,
			int totalWorkHours, int employeeId, Double commision) {
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update replad_guest_service_request set total_work_hour=?, total_price=?, mst_employee_id=?, commision=? where id=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, totalWorkHours);
			pstmt.setDouble(2, totalPrice);
			pstmt.setInt(3, employeeId);
			pstmt.setDouble(4, commision);
			pstmt.setInt(5, ticketId);
			status = pstmt.executeUpdate();
		} catch(SQLException e){
			status=0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
}
