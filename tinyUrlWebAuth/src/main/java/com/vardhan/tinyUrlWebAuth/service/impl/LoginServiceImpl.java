package com.vardhan.tinyUrlWebAuth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vardhan.tinyUrlWebAuth.dto.User;

import com.vardhan.tinyUrlWebAuth.dao.LoginDAO;

@Service
public class LoginServiceImpl implements UserDetailsService {

	@Autowired
	private LoginDAO loginDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= loginDao.findUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("not found");
		}
		return new UserDetailsImpl(user);
	}

}
