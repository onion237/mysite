package com.douzone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douzone.util.ConnectionProvider;
import com.douzone.vo.UserVo;

public class UserDao {
	ConnectionProvider connectionProvider;

	public UserDao(ConnectionProvider connectionProvider) {
		super();
		this.connectionProvider = connectionProvider;
	}
	
	public boolean insert(UserVo user) {
		boolean result = false;
		
		String sql = "insert into user(name, email, password, gender, join_date) values(?,?,?,?,curdate())";
		
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getGender());
	
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UserVo findByUserNo(Integer no) {
		UserVo result = null;
		String sql = "select no,name,email, gender, from user where no = ?";
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setLong(1, no);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					result = new UserVo();
					result.setNo(rs.getLong("no"));
					result.setName(rs.getString("name"));
					result.setEmail(rs.getString("email"));
					result.setGender(rs.getString("gender"));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public UserVo findByEmailAndPassword(UserVo user) {
		UserVo result = null;
		String sql = "select no, name, email, password, gender, date_format(join_date, '%Y/%m/%d %H:%i:%s') as d from user where email = ? and password = ?";
	
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getPassword());
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					result = new UserVo();
					result.setNo(rs.getLong("no"));
					result.setName(rs.getString("name"));
					result.setEmail(rs.getString("email"));
					result.setPassword(rs.getString("password"));
					result.setGender(rs.getString("gender"));
					result.setJoinDate(rs.getString("d"));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

		

}
