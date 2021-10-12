package com.douzone.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.dao.GuestBookDao;
import com.douzone.util.SimpleConnectionProvider;
import com.douzone.vo.GuestBookVo;

public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String action = request.getParameter("a");
		if(action == null || action.isEmpty()) action = "list";
		
		GuestBookDao dao = new GuestBookDao(new SimpleConnectionProvider());
		
		String viewsPath = "/WEB-INF/views/";
		String contextPath = request.getContextPath();
		String forwardingPath = "";
		String password = "";
		GuestBookVo vo = null;
		switch(action) {
		case "list":
			List<GuestBookVo> list = dao.findAll();
			request.setAttribute("list",list);
			forwardingPath = "index.jsp";
			break;
		case "form":
			forwardingPath = "deleteform.jsp";
			break;
		case "delete":
			vo = new GuestBookVo();
			Long no = Long.parseLong(request.getParameter("no"));
			password = request.getParameter("password");
			vo.setNo(no);
			vo.setPassword(password);
			dao.delete(vo);
			response.sendRedirect(contextPath + "/guestbook");
			return;
		case "add":
			String name = request.getParameter("name");
			password = request.getParameter("password");
			String message = request.getParameter("message");
			vo = new GuestBookVo();
			
			vo.setName(name);
			vo.setPassword(password);
			vo.setMessage(message);
			
			dao.insert(vo);
			response.sendRedirect(contextPath + "/guestbook");
			return;
		}
		request.getRequestDispatcher(viewsPath + forwardingPath).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
