package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestBookService {
	private final GuestBookRepository guestBookRepository;

	@Autowired
	public GuestBookService(GuestBookRepository guestBookRepository) {
		super();
		this.guestBookRepository = guestBookRepository;
	}
	
	public List<GuestBookVo> getList(){
		return guestBookRepository.findAll();
	}

	public boolean write(GuestBookVo guestBook) {
		return guestBookRepository.insert(guestBook);
	}

	public boolean delete(GuestBookVo vo) {
		return guestBookRepository.delete(vo);
	}

	public List<GuestBookVo> getList(GuestBookVo vo, Integer count) {
		return guestBookRepository.findAll(vo, count);
	}
	
}
