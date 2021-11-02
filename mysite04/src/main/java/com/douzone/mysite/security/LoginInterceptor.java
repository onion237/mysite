package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor{
	private final UserService userService;

	public LoginInterceptor(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getMethod().equals("GET")) {
			System.out.println(getClass().getSimpleName() + ".login(GET)");
			return true;
		}
		
		System.out.println(getClass().getSimpleName() + ".login(POST)");
		
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo user = new UserVo();
		user.setEmail(email);
		user.setPassword(password);
		
		UserVo authUser = userService.getUser(user);
		if(authUser == null) {
			request.setAttribute("result", "fail");
			request.setAttribute("userVo", user);
			
			request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp").forward(request, response);
		}else {
			request.getSession().setAttribute("user", authUser);
			response.sendRedirect(request.getContextPath());
		}
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
