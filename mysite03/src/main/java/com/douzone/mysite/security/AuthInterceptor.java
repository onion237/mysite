package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.annotation.Auth;
import com.douzone.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(getClass().getSimpleName() + ".prehandle============================================W");
		System.out.println(handler.getClass());

		// handler 종류 확인
		if (!(handler instanceof HandlerMethod))
			return true;

		// casting
//		System.out.println(handler.getClass().getMe;
		
		HandlerMethod method = (HandlerMethod) handler;
		// @Auth anotation가져오기
		Auth authAnnotation = method.getMethodAnnotation(Auth.class);
		System.out.println(authAnnotation);
//		for(Annotation anno : method.getMethod().getAnnotations()) {
//			System.out.println(anno);
//			System.out.println("=============================");
//		}
		
		
		// 메소드에 @Auth가 적용이 안돼있으면 클래스에 적용돼있는지 확인
		if (authAnnotation == null) {
			authAnnotation = method.getBeanType().getAnnotation(Auth.class);
		}

		if (authAnnotation == null)
			return true;
		

		UserVo authUser = (UserVo) request.getSession().getAttribute("user");

		// 인가여부 확인
		if (authUser != null
				&& ("ADMIN".equals(authUser.getRole()) || authAnnotation.role().equals(authUser.getRole())))
			return true;

		// @Auth 적용이 되어있으면 인증여부 확인
		response.sendRedirect(request.getContextPath());
		return false;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}