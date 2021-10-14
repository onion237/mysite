package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;

import com.douzone.dao.BoardDao;
import com.douzone.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class DeleteAction implements Action {
	private final BoardDao dao;
	public DeleteAction(BoardDao dao) {
		this.dao = dao;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVo loginUser = (UserVo) request.getSession().getAttribute("user");
		Long boardNo = Long.parseLong(request.getParameter("no"));
		Long userNo = dao.getUserNo(boardNo);
		
		if(userNo != loginUser.getNo()) {
			System.out.println("권한 없음!!!!!!!");
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		dao.delete(boardNo);

		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}
