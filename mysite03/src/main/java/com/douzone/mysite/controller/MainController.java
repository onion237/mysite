package com.douzone.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.SiteService;

@Controller
public class MainController {
	private final SiteService siteService;
	
	
	public MainController(SiteService siteService) {
		super();
		this.siteService = siteService;
	}


	@RequestMapping({"","/main"})
	public String index(Model model) {
		model.addAttribute("siteVo", siteService.getCurrentSite());
		return "main/index";
	}

}