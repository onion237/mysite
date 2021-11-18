package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	private final SqlSession sqlSession;	
	
	public GuestBookRepository(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}
	
	public boolean delete(GuestBookVo vo) {
		return sqlSession.delete("guestbook.delete",vo) > 0;		
	}
	public List<GuestBookVo> findAll(){
		List<GuestBookVo> result = sqlSession.selectList("guestbook.findAll");
		return result;
	}
	public List<GuestBookVo> findAll(GuestBookVo vo, Integer count){
		Map<String, Object> map = new HashMap<>();
		map.put("guestbook", vo);
		map.put("count", count);
		List<GuestBookVo> result = sqlSession.selectList("guestbook.findAll", map);
		return result;
	}
	public boolean insert(GuestBookVo vo) {
		return sqlSession.insert("guestbook.insert",vo) > 0;
	}
}
