package com.replad.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

public class DatabaseUtils {
	private static final Logger log = Logger.getLogger(DatabaseUtils.class.getName());	
	/**
	 * Getting database connection
	 * 
	 * @param dbPoolName
	 * @return
	 */
	public Connection getConnection(String... dbPoolName){
		Connection connection = null;
		try {
			String url = null;
				Class.forName("com.mysql.jdbc.Driver");
				url ="jdbc:mysql://127.0.0.1:3306/test";
				connection = DriverManager.getConnection(url, "root", "root");
				log.info("Local Database Connection Sucessful.");
		} catch (ClassNotFoundException e) {
			log.warning("Class Not Found Exception.");
			log.warning(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.warning("SQL Exception.");
			log.warning(e.getMessage());
			e.printStackTrace();
		}
		return connection;
	}
		
	/**
	 * Close database resources
	 * 
	 * @param con
	 * @param stmt
	 * @param prepare
	 * @param rs
	 */
	public static void closeConnection(Connection con,Statement stmt,PreparedStatement pstmt,ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs=null;
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt=null;
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
				pstmt=null;
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		if (con != null) {
			try {
				if(!con.isClosed()) {
					con.close();
					con=null;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();

			}
		}
	}
	
	public String replaceWithPlaceholder(String query, List valList){
		String str = query.replace("#replaceParam#", StringUtilities.repeat("?", ",", valList.size()));
		return str;
	}
	
	
	/**
	 * Setting the values in the prepared statement based on the datatype provided in the given sqlTypeArr for corresponding index.
	 * The last index value will repeat for rest if not provided ant datatypes for the rest indexes.
	 * 
	 * @param preparedStatement
	 * @param values
	 * @param sqlTypeArr : it contains the sql types for the parameter to set
	 * @param firstIndex it is optional. starting index of replacing the values.
	 * @return PreparedStatement
	 * @throws SQLException
	 */
	public PreparedStatement setValues(PreparedStatement preparedStatement, List<String> values, int[] sqlTypeArr, int... firstIndex) throws SQLException {
		int idx = 0;
		String val = "";
		int sqlType = Types.VARCHAR; // default data type set to varchar
		
		for (int i = 0; i < values.size() && null!=values && values.size()>0; i++) {
			if((sqlTypeArr.length>i) && (sqlTypeArr[i]<=0 || sqlTypeArr[i]>=0)){
				sqlType = sqlTypeArr[i];
			}
			idx = i+(firstIndex.length>0 && firstIndex[0]>0 ? firstIndex[0] : 1);
			
			if(StringUtils.isNotEmpty(values.get(i))) 
				val = values.get(i).toString().trim();
			
			switch(sqlType){
				case Types.LONGVARCHAR	:	preparedStatement.setLong(idx, Long.parseLong(val));				break;
				case Types.VARCHAR 		:	preparedStatement.setString(idx, val);								break;
				case Types.NUMERIC		:	preparedStatement.setInt(idx, Integer.parseInt(val));				break;
				case Types.SMALLINT 	:	preparedStatement.setShort(idx, Short.parseShort(val));				break;
				case Types.INTEGER 		:	preparedStatement.setInt(idx, Integer.parseInt(val));				break;
				case Types.FLOAT   		:	preparedStatement.setFloat(idx, Float.parseFloat(val));				break;
				case Types.DOUBLE   	:	preparedStatement.setDouble(idx, Double.parseDouble(val));			break;
				case Types.TIMESTAMP  	:	preparedStatement.setTimestamp(idx, Timestamp.valueOf(val));		break;
				case Types.TIME   		:	preparedStatement.setTime(idx, Time.valueOf(val));					break;
				case Types.BIGINT   	:	preparedStatement.setLong(idx, Long.parseLong(val));				break;
				default : 																						break;
			}
		}
		return preparedStatement;
	}
	/**
	 * Return max id of the given column from the given table.
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public String getMaxId(String tableName, String columnName){
		String maxId = "0";
		PreparedStatement pstmt = null;
		Connection con = null;
		ResultSet rs = null;
		String sql = "select coalesce(max("+columnName+"), 0) maxid from "+tableName;
		try{
			con = new DatabaseUtils().getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				return String.valueOf(Integer.parseInt(rs.getString("maxid"))+1);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return maxId;
	}
}
