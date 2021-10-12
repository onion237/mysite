package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.dao.UserDao;
import com.douzone.util.SimpleConnectionProvider;
import com.douzone.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class LoginAction implements Action {
	private final UserDao dao = new UserDao(new SimpleConnectionProvider());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserVo user = new UserVo();
		user.setEmail(email);
		user.setPassword(password);

		UserVo result = dao.findByEmailAndPassword(user);

		if (result == null) {
			request.setAttribute("result", "fail");
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		
		System.out.println("로그인 성공");
		HttpSession session = request.getSession(true);
		session.setAttribute("user", result);
		
		MvcUtil.redirect(request.getContextPath() + "/user", request, response);
	}
}