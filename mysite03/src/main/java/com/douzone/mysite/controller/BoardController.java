package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.repository.BoardRespository;
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
	public String list(Model model, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int cur) {
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
	
	@GetMapping("/write")
	public String write(HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("user");
		if(authUser == null) return "redirect:/";
			
		return "board/write";
	}
	
	@PostMapping("")
	public String write(HttpSession session, BoardVo board) {
		UserVo authUser = (UserVo) session.getAttribute("user");
		if(authUser == null) return "redirect:/";

		board.setUserNo(authUser.getNo());
		
		if(board.getReplyTo() == null)
			boardService.writeBoard(board);
		else
			boardService.writeReply(board);
		
		return "redirect:/board";
	}
	
	@GetMapping("/update/{no}")
	public String update(Model model, HttpSession session, @PathVariable Long no) {
		UserVo authUser = (UserVo) session.getAttribute("user");
		if(authUser == null) return "redirect:/";
		
		if(authUser.getNo() != boardService.getWriterNo(no)) {
			System.out.println("권한 없음!!!");
			return "redirect:/";
		}
		
		model.addAttribute("board", boardService.getBoard(no));
		
		return "board/modify";
	}
	
	@PostMapping("/{no}")
	public String update(HttpSession session, @PathVariable Long no, BoardVo board) {
		UserVo authUser = (UserVo) session.getAttribute("user");
		if(authUser == null) return "redirect:/";
		
		if(authUser.getNo() != boardService.getWriterNo(no)) {
			System.out.println("권한 없음!!!");
			return "redirect:/";
		}
		board.setNo(no);
		boardService.modify(board);
		
		return "redirect:/board";
	}
	
	@GetMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable Long no, @RequestParam(defaultValue = "1") int cur) {
		UserVo authUser = (UserVo) session.getAttribute("user");
		if(authUser == null) return "redirect:/";
		
		if(authUser.getNo() != boardService.getWriterNo(no)) {
			System.out.println("권한 없음!!!");
			return "redirect:/";
		}
		
		boardService.delete(no);
		
		return "redirect:/board?cur=" + cur;
	}
}
