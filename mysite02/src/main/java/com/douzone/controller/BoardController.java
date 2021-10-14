package com.douzone.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.mvc.board.BoardActionFactory;
import com.douzone.web.mvc.ActionFactory;
import com.douzone.web.util.MvcUtil;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ActionFactory actionFactory = new BoardActionFactory();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			actionFactory.getAction(request.getParameter("a")).execute(request, response);			
		}catch (NumberFormatException e) {
			System.out.println(e);
			MvcUtil.redirect(request.getContextPath(), request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
