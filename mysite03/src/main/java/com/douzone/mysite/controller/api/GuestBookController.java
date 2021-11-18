package com.douzone.mysite.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.ApiResult;
import com.douzone.mysite.service.GuestBookService;
import com.douzone.mysite.vo.GuestBookVo;

@RestController("guestBookApiController")
@RequestMapping("/api/guestbook")
public class GuestBookController {
	private final GuestBookService guestBookService;
	
	public GuestBookController(GuestBookService guestBookService) {
		super();
		this.guestBookService = guestBookService;
	}


	@PostMapping
	public ApiResult wirte(@RequestBody GuestBookVo vo) {
		guestBookService.write(vo);
		vo.setPassword(null);
		return ApiResult.success(vo);
	}
	@DeleteMapping("/{no}")
	public ApiResult delete(@PathVariable Long no, GuestBookVo vo) {
		vo.setNo(no);
		boolean deleted = guestBookService.delete(vo);
		return deleted? ApiResult.success(vo) : ApiResult.fail("비밀번호가 틀립니다.");
	}
	@GetMapping
	public ApiResult getList(GuestBookVo vo, Integer count) {
		List<GuestBookVo> list = guestBookService.getList(vo, count);
		
		return ApiResult.success(list);
	}
}
