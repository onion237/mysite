package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	private final SqlSession sqlSession;

	public SiteRepository(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}
	
	public boolean update(SiteVo vo) {
		return sqlSession.update("site.updateLatest", vo) > 0;
	}
	public SiteVo findLatest() {
		return sqlSession.selectOne("site.findLatest");
	}
}
