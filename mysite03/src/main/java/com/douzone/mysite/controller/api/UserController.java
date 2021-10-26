package com.douzone.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.ApiResult;
import com.douzone.mysite.service.UserService;

@RestController("userApiController")
@RequestMapping("/user/api")
public class UserController {
	private final UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/checkemail")
	public ApiResult checkEmail(String email) {
		if(userService.isAvailableEmail(email)) {
			return ApiResult.success(true);
		}else {
			return ApiResult.fail("duplicated email");
		}
	}
}
