package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.dao.BoardDao;
import com.douzone.vo.BoardVo;
import com.douzone.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ModyfiFormAction implements Action {
	private final BoardDao dao;

	public ModyfiFormAction(BoardDao dao) {
		this.dao = dao;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVo loginUser = (UserVo)request.getSession().getAttribute("user");
		String bNo = request.getParameter("no");
		Long boardNo = null;
		
		try {
			boardNo = Long.parseLong(bNo);
		}catch (NumberFormatException e) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		if(dao.getUserNo(boardNo) != loginUser.getNo()) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			System.out.println("권한없음-----------!!!!!!!!!!!!!!!!!!");
			return;
		}
		
		BoardVo board = dao.findByNo(boardNo);
		request.setAttribute("board", board);
		MvcUtil.forward("board/modify", request, response);
	}

}
