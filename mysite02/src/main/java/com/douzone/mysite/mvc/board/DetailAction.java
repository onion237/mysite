package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.dao.BoardDao;
import com.douzone.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class DetailAction implements Action {
	private final BoardDao dao;
	
	public DetailAction(BoardDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paramNo = request.getParameter("no");
		
		Long no = null;
		if(paramNo != null) {
			try {
				no = Long.parseLong(paramNo);				
			}catch (NumberFormatException e) {
				System.out.println(e);
				MvcUtil.redirect(request.getContextPath() + "/board", request, response);
				return;
			}
		}
		
		BoardVo result = dao.findByNo(no);
		request.setAttribute("board", result);
		MvcUtil.forward("board/view", request, response);
	}

}
