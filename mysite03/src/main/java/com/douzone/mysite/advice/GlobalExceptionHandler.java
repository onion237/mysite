package com.douzone.mysite.advice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(Exception.class)
	public void handlerException(
			HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
		
		// 1. 로깅
		StringWriter errors = new StringWriter();
		
		e.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		
		// 2. 요청 구분
		
		// 3. Sorry
		request.setAttribute("exception", errors.toString());
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
	}
}
