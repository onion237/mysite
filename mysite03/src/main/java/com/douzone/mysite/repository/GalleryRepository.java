package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	private final SqlSession sqlSession;
	

	public GalleryRepository(SqlSession sqlSession) {
		super();
		this.sqlSession = sqlSession;
	}


	public List<GalleryVo> findAll() {
		return sqlSession.selectList("gallery.findAll");
	}


	public boolean insert(GalleryVo vo) {
		return sqlSession.insert("gallery.insert", vo) > 0;
	}


	public boolean delete(Long no) {
		return sqlSession.delete("gallery.delete", no) > 0;
	}

}
