package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public boolean join(UserVo user) {
		boolean result = false;
		
		//validation
		if(!user.validate()) {
			System.out.println("user validate fail");
			return false;
		}
		result = userRepository.insert(user);			
		
		return result;
	}

	public UserVo getUser(UserVo user) {
		return userRepository.findByEmailAndPassword(user);
	}
	public UserVo getUser(Long no) {
		return userRepository.findByUserNo(no);
	}

	public boolean update(UserVo user) {
		return userRepository.update(user);
	}

	public boolean isAvailableEmail(String email) {
		if(email == null || email.trim().isEmpty()) return false;
		
		return userRepository.isEmailDuplicated(email) == null;
	}
}