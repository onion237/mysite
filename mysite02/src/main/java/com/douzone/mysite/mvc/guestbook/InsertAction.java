package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.dao.GuestBookDao;
import com.douzone.util.SimpleConnectionProvider;
import com.douzone.vo.GuestBookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class InsertAction implements Action {
	GuestBookDao dao = new GuestBookDao(new SimpleConnectionProvider());
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String message = request.getParameter("message");
		
		GuestBookVo vo = new GuestBookVo();
		
		vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(message);
		
		dao.insert(vo);
		MvcUtil.redirect(request.getContextPath() + "/guestbook", request, response);
	}

}
