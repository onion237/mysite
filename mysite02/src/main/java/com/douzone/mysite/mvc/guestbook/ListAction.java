package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.dao.GuestBookDao;
import com.douzone.util.SimpleConnectionProvider;
import com.douzone.vo.GuestBookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {
	private GuestBookDao dao = new GuestBookDao(new SimpleConnectionProvider()); 

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<GuestBookVo> list = dao.findAll();
		
		request.setAttribute("list", list);
		MvcUtil.forward("guestbook/list", request, response);		
	}

}
