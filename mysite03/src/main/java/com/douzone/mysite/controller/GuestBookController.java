package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	private final GuestBookService guestBookService;
	
	@Autowired
	public GuestBookController(GuestBookService guestBookService) {
		super();
		this.guestBookService = guestBookService;
	}


	@GetMapping({"", "/list"})
	public String list(Model model) {
		model.addAttribute("list",guestBookService.getList());
		return "guestbook/list";
	}
	
	@PostMapping("")
	public String write(GuestBookVo guestBook) {
		guestBookService.write(guestBook);
		return "redirect:/guestbook";
	}
	
	@GetMapping("/delete/{no}")
	public String delete(Model model, @PathVariable Long no) {
		model.addAttribute("no", no);
		System.out.println("asdasdasdasdasd");
		return "guestbook/deleteform";
	}
	@PostMapping("/delete/{no}")
	public String delete(GuestBookVo vo, @PathVariable Long no) {
		vo.setNo(no);
		guestBookService.delete(vo);
		return "redirect:/guestbook";
	}
	
}
