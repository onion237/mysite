package com.douzone.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.douzone.mysite.service.SiteService;

public class SiteInfoLoadInterceptor implements HandlerInterceptor{
	@Autowired
	private SiteService siteService;
	@Autowired
	private ApplicationContext ctx;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(getClass() + ":::::::::::::::::::::::::::::::");
		
		ctx.getBeansOfType(HandlerMapping.class)
				.forEach((key, value) -> {
					System.out.println(key + " : " + value);
					});
		if(request.getServletContext().getAttribute("siteVo") == null) {
			System.out.println(siteService);
			request.getServletContext().setAttribute("siteVo", siteService.getCurrentSite());
		}
		
		return true;
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
