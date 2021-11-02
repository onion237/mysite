package com.douzone.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.annotation.Auth;
import com.douzone.mysite.annotation.AuthUser;
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
	public String login(UserVo userVo, Model model) {
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public String join(UserVo vo) {
		return "user/joinform";
	}

	@PostMapping("/join")
	public String join(@Valid UserVo user, BindingResult result, Model model) {
//		if(result.hasErrors()) {
//			result.getAllErrors().forEach((error) -> {
//				
//				System.out.println(error.getCode());
//				System.out.println(error.getArguments()[0]);
//				System.out.println(error.getDefaultMessage());
//			});
//			model.addAttribute("errors", result.getAllErrors());
//			return "user/joinform";
//		}
		if(result.hasErrors()) {
			
			model.addAllAttributes(result.getModel());
			result.getModel().forEach((key, value) -> {
				System.out.println(key + "---------------" + value);
			});
			return "user/joinform";
		}
//		if (!userService.join(user)) {
//			return "redirect:/";
//		}

		return "redirect:/user/joinsuccess";
	}

	@GetMapping("/joinsuccess")
	public String joinSuccess(UserVo user) {
		return "user/joinsuccess";
	}

	@Auth
	@GetMapping("/update")
	public String update(Model model, @AuthUser UserVo authUser) {
		if(authUser == null) return "redirect:/";
		
		UserVo user = userService.getUser(authUser.getNo());
		if(user == null) return "redirect:/";
		
		model.addAttribute("user", user);
		return "user/updateform";
	}

	@Auth
	@PostMapping("/update")
	public String update(@AuthUser UserVo authUser, UserVo user) {
		if(authUser == null) return "redirect:/";
		
		user.setNo(authUser.getNo());

		if(userService.update(user))
			authUser.setName(user.getName());
			
		
		return "redirect:/";
	}
	
	
	
}
