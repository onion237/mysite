package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageInfo;

@Repository
public class BoardRespository {
	private final SqlSession sqlSession;

	public BoardRespository(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}

	public boolean insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo) > 0;
	}

	public boolean insertReply(BoardVo board) {
		BoardVo groupInfo = sqlSession.selectOne("board.getGroupInfo", board);
		sqlSession.update("board.updateOrder", groupInfo);
		board.setGroupNo(groupInfo.getGroupNo());
		board.setOrderNo(groupInfo.getOrderNo() + 1);
		board.setDepth(groupInfo.getDepth() + 1);

		return sqlSession.insert("board.insertReply", board) > 0;
	}

	public List<BoardVo> findAll(BoardVo board, PageInfo page) {
		Map<String, Object> map = new HashMap<>();
		map.put("board", board);
		map.put("page", page);
		
		return sqlSession.selectList("board.findAll", map);
	}

	public long getBoardCnt(BoardVo board) {
		long result = sqlSession.selectOne("board.getCount",board);
		return result;
	}

	public BoardVo findByNo(Long no) {
		sqlSession.update("board.updateHit", no);
		return sqlSession.selectOne("board.findByNo",no);
	}

	public Long getUserNo(long boardNo) {
		return sqlSession.selectOne("board.getUserNo", boardNo);
	}

	public boolean update(BoardVo board) {
		return sqlSession.update("board.update", board) > 0;
	}

	public boolean delete(Long no) {
		return sqlSession.delete("board.delete", no) > 0;
	}
}
