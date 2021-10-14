package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.dao.UserDao;
import com.douzone.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class UpdateFormAction implements Action {
	private final UserDao dao;
	public UpdateFormAction(UserDao dao) {
		this.dao = dao;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo user = (UserVo)session.getAttribute("user");
		
		if(user == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		user = dao.findByUserNo(user.getNo()); 
		request.setAttribute("user", user);
		
		MvcUtil.forward("user/updateform", request, response);
	}
}