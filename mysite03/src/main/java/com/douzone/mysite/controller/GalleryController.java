package com.douzone.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.annotation.Auth;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	private final GalleryService galleryService;
	public GalleryController(GalleryService galleryService) {
		super();
		this.galleryService = galleryService;
	}
	
	@GetMapping("")
	public String index(Model model) {
		model.addAttribute("list", galleryService.getList());
		return "gallery/index";
	}
	@Auth(role = "ADMIN")
	@PostMapping("/upload")
	public String upload(GalleryVo vo, @RequestParam("file") MultipartFile file) {
		System.out.println(file);
		galleryService.upload(vo, file);
		return "redirect:/gallery";
	}
	
	@Auth(role = "ADMIN")
	@GetMapping("/delete/{no}")
	public String delete(@PathVariable Long no) {
		galleryService.delete(no);
		return "redirect:/gallery";
	}
	
}
