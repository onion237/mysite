package com.douzone.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.mvc.main.MainActionFactory;
import com.douzone.web.mvc.ActionFactory;
import com.douzone.web.util.MvcUtil;

/**
 * Servlet implementation class MainController
 */
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ActionFactory actionFactory = new MainActionFactory();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionFactory.getAction(request.getParameter("a")).execute(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
