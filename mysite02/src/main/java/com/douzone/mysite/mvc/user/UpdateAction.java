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

public class UpdateAction implements Action {
	private final UserDao dao;
	public UpdateAction(UserDao dao) {
		this.dao = dao;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo vo = (UserVo) session.getAttribute("user");
		if(vo == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		String name, password, gender;
		name = password = gender = null;
		
		
		name = request.getParameter("name");
		password = request.getParameter("password");
		gender = request.getParameter("gender");
		UserVo user = new UserVo();
		user.setNo(vo.getNo());
		user.setName(name);
		user.setPassword(password);
		user.setGender(gender);
		
		boolean result = dao.update(user);
		if(result) {
			session.setAttribute("msg", "수정성공");
			vo.setName(name);
		}
		MvcUtil.redirect(request.getContextPath() + "/user?a=updateform", request, response);
	}

}
