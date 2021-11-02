package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.annotation.Auth;
import com.douzone.mysite.annotation.AuthUser;
import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private final BoardService boardService;
	@Autowired
	private GuestBookRepository guestBookRepository;
	@Autowired
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@ResponseBody
	@GetMapping("/test")
	public String test() {
		guestBookRepository.findAll();
		return "test";
	}
	@GetMapping("")
	public String list(Model model, 
			@RequestParam(defaultValue = "") String keyword, 
			@RequestParam(defaultValue = "1") int cur) {
		
		Map<String, Object> listAndPageInfo = boardService.getListAndPageInfo(keyword,cur);		
		model.addAllAttributes(listAndPageInfo);
		
		return "board/list";
	}
	
	@GetMapping("/{no}")
	public String viewBoard(Model model,@PathVariable Long no) {
		BoardVo board = boardService.getBoard(no);
		
		model.addAttribute("board",board);
		return "board/view";
	}
	
	@Auth
	@GetMapping("/write")
	public String write(@AuthUser UserVo authUser) {			
		return "board/write";
	}
	
	@Auth
	@PostMapping("")
	public String write(BoardVo board, @AuthUser UserVo authUser) {
		System.out.println("==============================");
		System.out.println(authUser);
		board.setUserNo(authUser.getNo());
		
		if(board.getReplyTo() == null)
			boardService.writeBoard(board);
		else
			boardService.writeReply(board);
		
		return "redirect:/board";
	}
	
	@Auth
	@GetMapping("/update/{no}")
	public String update(Model model, @PathVariable Long no, @AuthUser UserVo authUser) {
		
		if(authUser.getNo() != boardService.getWriterNo(no)) {
			System.out.println("권한 없음!!!");
			return "redirect:/";
		}
		
		BoardVo board = boardService.getBoard(no);
		model.addAttribute("board", board);
		
		return "board/modify";
	}
	
	@Auth
	@PostMapping("/{no}")
	public String update(@PathVariable Long no, BoardVo board, @AuthUser UserVo authUser) {
		
		if(authUser.getNo() != boardService.getWriterNo(no)) {
			System.out.println("권한 없음!!!");
			return "redirect:/";
		}
		board.setNo(no);
		boardService.modify(board);
		
		return "redirect:/board";
	}
	
	@Auth
	@GetMapping("/delete/{no}")
	public String delete(@PathVariable Long no, @RequestParam(defaultValue = "1") int cur, @AuthUser UserVo authUser) {
		
		if(authUser.getNo() != boardService.getWriterNo(no)) {
			System.out.println("권한 없음!!!");
			return "redirect:/";
		}
		
		boardService.delete(no);
		
		return "redirect:/board?cur=" + cur;
	}
}
