package com.replad.feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.repald.tickets.TicketBean;
import com.repald.tickets.Tickets;
import com.replad.utils.DatabaseUtils;

public class TestimonyDbUtils {

	public int submitTestimony(String feedbackEmail, String feedbackMobile,
			String feedbackDescription) {
		int status = 0;
		PreparedStatement pstmt = null;
		Connection con = null;
		String sql = "insert into replad_user_feedback values (?,?,?,?)";
		try{
			con = new DatabaseUtils().getConnection();
			pstmt = con.prepareStatement(sql);
			String maxID = new DatabaseUtils().getMaxId("replad_user_feedback", "id");
			pstmt.setInt(1, Integer.parseInt(maxID));
			pstmt.setString(2, feedbackEmail);
			pstmt.setString(3, feedbackMobile);
			pstmt.setString(4, feedbackDescription);
			status = pstmt.executeUpdate();
		} catch(SQLException e){
			status = 0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
}
