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

public class WriteAction implements Action {
	private final BoardDao dao;
	
	public WriteAction(BoardDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String reply = request.getParameter("reply");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(((UserVo)request.getSession().getAttribute("user")).getNo());
		
		if(reply == null || reply.isEmpty()) {
			dao.insert(vo);			
		}else {
			try {
				vo.setNo(Long.parseLong(reply));				
			}catch (NumberFormatException e) {
				System.out.println(e);
				MvcUtil.redirect(request.getContextPath(), request, response);
				return;
			}
			
			dao.insertReply(vo);
		}
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}
