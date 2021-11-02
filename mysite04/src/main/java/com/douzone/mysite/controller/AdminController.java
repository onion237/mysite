package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.annotation.Auth;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	private final SiteService siteService;

	public AdminController(SiteService siteService) {
		super();
		this.siteService = siteService;
	}

	@GetMapping("")
	public String main(Model model) {
		return "admin/main";
	}

	@PostMapping("/main/update")
	public String main(SiteVo vo, @RequestParam("file") MultipartFile multipartFile) {
		System.out.println(vo);
		System.out.println(multipartFile);
		System.out.println(multipartFile.isEmpty());
		siteService.updateSite(vo, multipartFile);

		return "redirect:/admin";
	}

	@GetMapping("/guestbook")
	public String guestbook() {

		return "admin/guestbook";
	}

	@GetMapping("/user")
	public String user() {

		return "admin/user";
	}

	@GetMapping("/board")
	public String board() {

		return "admin/board";
	}
}
