package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRespository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageInfo;

@Service
public class BoardService {
	private final BoardRespository boardRespository;
	@Autowired
	public BoardService(BoardRespository boardRespository) {
		super();
		this.boardRespository = boardRespository;
	}
	
	public Map<String, Object> getListAndPageInfo(String keyword, int curPage) {
		BoardVo board  = new BoardVo();
		board.setTitle(keyword);
		
		PageInfo page = new PageInfo();
		page.setCurPage(curPage);
		page.setOffset((curPage - 1) * page.getCntPerPage());
		List<BoardVo> result = boardRespository.findAll(board, page);
		
		int totalPage = (int)Math.ceil((double)boardRespository.getBoardCnt(board) / page.getCntPerPage());
		int begin = page.getCurPage() - ((page.getPageRange() - 1) / 2);
		begin = begin < 1 ? 1 : begin;
//		int end = begin + page.getPageRange() - 1;
		int end = Math.min(begin + page.getPageRange() - 1, totalPage);
		
		int nextPage = curPage >= totalPage ? -1 : curPage + 1; 
		int prevPage = curPage <= 1? -1 : curPage - 1;
//		int nextPage = end >= totalPage ? -1 : end + 1; 
//		int prevPage = begin <= 1? -1 : begin - 1;
		
		page.setTotalPage(totalPage);
		page.setNextPage(nextPage);
		page.setPrevPage(prevPage);
		page.setBegin(begin);
		page.setEnd(end);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("list", boardRespository.findAll(board, page));
		map.put("pager", page);
		return map;
	}

	public BoardVo getBoard(Long no) {
		return boardRespository.findByNo(no);
	}


	public Long getWriterNo(Long boardNo) {
		return boardRespository.getUserNo(boardNo);
	}
	
	public boolean writeBoard(BoardVo board) {
		if(!board.isWritable()) return false;
		
		return boardRespository.insert(board);
	}
	
	public boolean writeReply(BoardVo board) {
		if(!board.isWritable()) return false;
		
		return boardRespository.insertReply(board);
	}

	public boolean modify(BoardVo board) {
		if(!board.isWritable()) return false;
		
		return boardRespository.update(board);
	}

	public boolean delete(Long no) {
		return boardRespository.delete(no);
	}
	
}
