package com.douzone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.util.ConnectionProvider;
import com.douzone.vo.BoardVo;
import com.douzone.vo.PageInfo;

public class BoardDao {
	private final ConnectionProvider connectionProvider;

	public BoardDao(ConnectionProvider connectionProvider) {
		super();
		this.connectionProvider = connectionProvider;
	}

	public boolean insert(BoardVo vo) {
		boolean result = false;
		String sql = "insert into board(title, contents, user_no, group_no, order_no, depth) values(?,?,?, (select * from (select ifnull(max(group_no), 0) + 1 from board for update) as b), 0,0)";

		try (Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());

			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public boolean insertReply(BoardVo board) {
		boolean result = false;
		String getGroupInfoSql = "select group_no, order_no, depth from board where no = ?";
		String updateOrderSql = "update board set order_no = order_no + 1 where group_no = ? and order_no > ?";
		String insertReplySql = "insert into board(title, contents, user_no, group_no, order_no, depth) values(?,?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long groupNo, orderNo, depth;
		groupNo = orderNo = depth = 0;
		try {
			conn = connectionProvider.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(getGroupInfoSql);
			pstmt.setLong(1, board.getNo());

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				groupNo = rs.getLong("group_no");
				orderNo = rs.getLong("order_no");
				depth = rs.getLong("depth");
			}else {
				throw new SQLException("해당 글의 group info를 불러오던 중 예외 발생");
			}
			
			pstmt = conn.prepareStatement(updateOrderSql);
			pstmt.setLong(1, groupNo);
			pstmt.setLong(2, orderNo);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(insertReplySql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContents());
			pstmt.setLong(3, board.getUserNo());
			pstmt.setLong(4, groupNo);
			pstmt.setLong(5, orderNo + 1);
			pstmt.setLong(6, depth + 1);
			result = pstmt.executeUpdate() > 0;
			
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		return result;
	}

	public List<BoardVo> findAll(BoardVo board, PageInfo page) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		String sql = "select board.no, title, hit, date_format(reg_date, '%Y-%m-%d %H:%i:%s') d, group_no, order_no, depth, user_no, name, is_deleted "
				+ "from board join user on user.no = board.user_no "
				+ "where title like ? "
				+ "order by group_no desc, order_no asc "
				+ "limit ?, ?";
		
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			//파라미터 바인딩
			pstmt.setString(1, "%" + board.getTitle() + "%");
			pstmt.setInt(2, (page.getCurPage() - 1) * page.getCntPerPage());
			pstmt.setInt(3, page.getCntPerPage());
			try(ResultSet rs = pstmt.executeQuery()){
				BoardVo vo = null;
				
				//객체 매핑
				while(rs.next()) {
					vo = new BoardVo();
					vo.setNo(rs.getLong("board.no"));
					vo.setTitle(rs.getString("title"));
					vo.setHit(rs.getLong("hit"));
					vo.setRegDate(rs.getString("d"));
					vo.setGroupNo(rs.getLong("group_no"));
					vo.setOrderNo(rs.getLong("order_no"));
					vo.setDepth(rs.getLong("depth"));
					vo.setUserNo(rs.getLong("user_no"));
					vo.setUserName(rs.getString("name"));
					vo.setIsDeleted(rs.getBoolean("is_deleted"));
					result.add(vo);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
	public int getBoardCnt(BoardVo board) {
		int result = 0;
		String sql = "select count(*) as cnt "
				+ "from board join user on user.no = board.user_no "
				+ "where title like ? ";
		
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, "%" + board.getTitle() + "%");
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					result = rs.getInt("cnt");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}

	public BoardVo findByNo(Long no) {
		BoardVo vo = null;
		
		String sql = "select board.no, title, contents, hit, date_format(reg_date, '%Y-%m-%d %H:%i:%s') as d, user.no, user.name "
				+ "from board join user on user.no = board.user_no "
				+ "where board.no = ? and is_deleted = false";
		String hitSql = "update board set hit = hit + 1 where no = ?";
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setLong(1, no);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					vo = new BoardVo();
					vo.setNo(rs.getLong("board.no"));
					vo.setTitle(rs.getString("title"));
					vo.setContents(rs.getString("contents"));
					vo.setHit(rs.getLong("hit"));
					vo.setRegDate(rs.getString("d"));
					vo.setUserNo(rs.getLong("user.no"));
					vo.setUserName(rs.getString("user.name"));
				}
			}
			
			try(PreparedStatement pstmt2 = conn.prepareStatement(hitSql)){
				pstmt2.setLong(1, no);
				pstmt2.executeUpdate();				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	public Long getUserNo(long boardNo) {
		Long result = null;
		String sql = "select user_no from board where no = ?";
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setLong(1, boardNo);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					result = rs.getLong("user_no");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean update(BoardVo board) {
		boolean result = false;
		String sql = "update board set title = ?, contents = ? where no = ?";
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContents());
			pstmt.setLong(3, board.getNo());

			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public boolean delete(Long no) {
		boolean result = false;
		String sql = "update board set is_deleted = true where no = ?";
		try(Connection conn = connectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setLong(1, no);
			
			result = pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
