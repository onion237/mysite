package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.dao.BoardDao;
import com.douzone.vo.BoardVo;
import com.douzone.vo.PageInfo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {
	private final BoardDao dao;
	public ListAction(BoardDao dao) {
		this.dao = dao;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("keyword");
		String cur = request.getParameter("cur");
		
		int curPage = 1;
		try {
			if(cur != null && !cur.isEmpty())
				curPage = Integer.parseInt(cur);
		}catch (NumberFormatException e) {
			System.out.println(e);
		}
		
		
		BoardVo board  = new BoardVo();
		if(title == null) title = "";
		board.setTitle(title);
		
		PageInfo page = new PageInfo();
		page.setCurPage(curPage);
		
		List<BoardVo> result = dao.findAll(board, page);
		int totalPage = (int)Math.ceil((double)dao.getBoardCnt(board) / page.getCntPerPage());
		int nextPage = curPage >= totalPage ? -1 : curPage + 1; 
		int prevPage = curPage <= 1? -1 : curPage - 1;
		int begin = page.getCurPage() - ((page.getPageRange() - 1) / 2);
		begin = begin < 1 ? 1 : begin;
		int end = begin + page.getPageRange() - 1;
		
		page.setTotalPage(totalPage);
		page.setNextPage(nextPage);
		page.setPrevPage(prevPage);
		page.setBegin(begin);
		page.setEnd(end);
		
		System.out.println(page);
		
		request.setAttribute("list", result);
		request.setAttribute("pager", page);
		
		MvcUtil.forward("board/list", request, response);
	}

}
