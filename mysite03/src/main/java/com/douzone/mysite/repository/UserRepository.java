package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserRepositoryException;
import com.douzone.mysite.vo.UserVo;
import com.douzone.util.ConnectionProvider;

@Repository
public class UserRepository {
	private final ConnectionProvider connectionProvider;
	private final SqlSession sqlSession;

	@Autowired
	public UserRepository(ConnectionProvider connectionProvider, SqlSession sqlSession) {
		super();
		this.connectionProvider = connectionProvider;
		this.sqlSession = sqlSession;
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
	

	public UserVo findByUserNo(Long no) throws UserRepositoryException{
		UserVo result = null;
		String sql = "select no, name, email, gender from user where no = ?";
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
			throw new UserRepositoryException(e);
		}
		
		return result;
	}

	public UserVo findByEmailAndPassword(UserVo user) throws UserRepositoryException{
		UserVo result = null;
		String sql = "select no, name, email, password, gender, date_format(join_date, '%Y/%m/%d %H:%i:%s') as regDate, role from user where email = ? and password = ?";
	
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
					result.setJoinDate(rs.getString("regDate"));
					result.setRole(rs.getString("role"));
				}
			}
			
		} catch (SQLException e) {
			throw new UserRepositoryException(e);
		}
		return result;
	}

	public boolean update(UserVo user) {
		boolean result = false;
		String sql = null;
		boolean updatePassword = false;
		if(user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
			sql = "update `user` set name = ?, gender = ?, password = ? where no = ?";
			updatePassword = true;
		}else {
			sql = "update `user` set name = ?, gender = ? where no = ?";
		}
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			int idx = 1;
			pstmt.setString(idx++, user.getName());
			pstmt.setString(idx++, user.getGender());
			if(updatePassword) {
				pstmt.setString(idx++, user.getPassword());
			}
			pstmt.setLong(idx++, user.getNo());
			
			
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public UserVo isEmailDuplicated(String email) {
		return sqlSession.selectOne("user.findByEmail", email);
	}

		

}