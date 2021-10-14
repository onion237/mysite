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

public class ModifyAction implements Action {
	private final BoardDao dao;
	
	public ModifyAction(BoardDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVo vo = (UserVo)request.getSession().getAttribute("user");
		String bNo = request.getParameter("no");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		Long boardNo = null;
		
		try {
			boardNo = Long.parseLong(bNo);
		}catch (NumberFormatException e) {
			System.out.println(e);
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		if(vo.getNo() != dao.getUserNo(boardNo)) {
			System.out.println("권한 없음!!!!!");
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		BoardVo board = new BoardVo();
		board.setNo(boardNo);
		board.setTitle(title);
		board.setContents(contents);
		dao.update(board);
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}
