package com.douzone.mysite.repository;

import java.util.List;

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
	public boolean insert(GuestBookVo vo) {
		return sqlSession.insert("guestbook.insert",vo) > 0;
	}
}
