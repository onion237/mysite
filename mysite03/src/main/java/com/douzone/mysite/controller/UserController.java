package com.douzone.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login() {
		return "user/loginform";
	}

	@PostMapping("/login")
	public String login(UserVo user, Model model, HttpSession session) {
		System.out.println(user);
		UserVo loginUser = userService.getUser(user);
		if(loginUser == null) {
			System.out.println("login fail");
			model.addAttribute("result", "fail");
			
			return "user/loginform";
		}
		session.setAttribute("user", loginUser);			
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	@GetMapping("/join")
	public String join() {
		return "user/joinform";
	}

	@PostMapping("/join")
	public String join(UserVo user) {
		if (!userService.join(user)) {
			return "redirect:/";
		}

		return "redirect:/user/joinsuccess";
	}

	@GetMapping("/joinsuccess")
	public String joinSuccess(UserVo user) {
		return "user/joinsuccess";
	}

	@GetMapping("/update")
	public String update(Model model, HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("user");
		if(authUser == null) return "redirect:/";
		
		UserVo user = userService.getUser(authUser.getNo());
		if(user == null) return "redirect:/";
		
		model.addAttribute("user", user);
		return "user/updateform";
	}
	
	@PostMapping("/update")
	public String update(HttpSession session, UserVo user) {
		UserVo authUser = (UserVo) session.getAttribute("user");
		if(authUser == null) return "redirect:/";
		
		user.setNo(authUser.getNo());

		if(userService.update(user))
			authUser.setName(user.getName());
			
		
		return "redirect:/";
	}
	
}
