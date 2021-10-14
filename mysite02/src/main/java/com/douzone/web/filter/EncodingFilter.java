package com.douzone.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
	private String encoding;
	
    public EncodingFilter() {
    }

    public void init(FilterConfig fConfig) throws ServletException {
    	encoding = fConfig.getInitParameter("encoding");
    	if(encoding == null) encoding = "utf-8";
    	
    	System.out.println(getClass() + " init called");
    }
    
    public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*before service */
		System.out.println("do Filter...");
		request.setCharacterEncoding(encoding);
		
		chain.doFilter(request, response);
		/*after service */
	}


}
