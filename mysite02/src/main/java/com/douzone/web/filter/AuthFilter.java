package com.douzone.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.web.util.MvcUtil;

public class AuthFilter implements Filter {
	private List<String> actions = new ArrayList<>();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String action = request.getParameter("a");
		System.out
				.println("----------------------------------auth filter---------------------------------------------");
		System.out.println("action : " + action);
		if (actions.contains(action)) {
			HttpSession session = ((HttpServletRequest) request).getSession();
			HttpServletRequest r = (HttpServletRequest) request;
			System.out.println(session);
			System.out.println(r.getCookies());
			
			Arrays.stream(r.getCookies()).forEach((c) -> {
				System.out.println(c.getValue());
			});
			if (session.getAttribute("user") == null) {
				System.out.println("permission denied------------------------");
				MvcUtil.redirect(((HttpServletRequest) request).getContextPath() + "/board",
						(HttpServletRequest) request, (HttpServletResponse) response);
				return;
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String actionParam = filterConfig.getInitParameter("actions");
		if (actionParam != null) {
			actions.addAll(Arrays.asList(actionParam.split(",")));
			System.out.println("access control actions : " + actions);
		}
	}

	@Override
	public void destroy() {

	}

}
