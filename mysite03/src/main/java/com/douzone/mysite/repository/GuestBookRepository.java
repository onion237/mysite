package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.util.ConnectionProvider;

public class GuestBookRepository {
	private final ConnectionProvider connectionProvider;

	public GuestBookRepository(ConnectionProvider connectionProvider) {
		super();
		this.connectionProvider = connectionProvider;
	}
	
	public boolean delete(GuestBookVo vo) {
		boolean result = false;
		
		String sql = "delete from guest_book where no = ? and password = ?";
		
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
	public List<GuestBookVo> findAll(){
		List<GuestBookVo> result = new ArrayList<>();
		String sql = "select no, name, message, date_format(reg_date, '%Y/%m/%d %H:%i:%s') as rd from guest_book order by no desc";
		
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			try(ResultSet rs = pstmt.executeQuery()){
				GuestBookVo vo = null;
				while(rs.next()) {
					vo = new GuestBookVo();
					vo.setNo(rs.getLong("no"));
					vo.setName(rs.getString("name"));
					vo.setMessage(rs.getString("message"));
					vo.setRegDate("rd");
					
					result.add(vo);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
	public boolean insert(GuestBookVo vo) {
		boolean result = false;
		
		String sql = "insert into guest_book(name, password, message) values(?,?,?)";
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
