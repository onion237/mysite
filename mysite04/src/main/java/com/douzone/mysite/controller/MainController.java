package com.douzone.mysite.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.SiteService;

@Controller
public class MainController {
	private final SiteService siteService;
	
	public MainController(SiteService siteService) {
		super();
		this.siteService = siteService;
	}

	@RequestMapping({"","/main"})
	public String index() {
		return "main/index";
	}
	
	@GetMapping("/json")
	@ResponseBody
	public Map getMessge(/*HttpServletResponse response*/) throws IOException {
//		response.setContentType("application/json; charset=UTF-8");
//		PrintWriter writer = response.getWriter();
//		writer.println("{\"test\":\"ajax\"}");
		
		Map<String,Object> map = new HashMap<>();
		map.put("message", "Hello World");
		
		return map;
	}
	
	@GetMapping("/string")
	@ResponseBody
	public String getString(/*HttpServletResponse response*/) throws IOException {
		return "<h1>hello 안녕";
	}
}