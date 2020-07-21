package com.vardhan.tinyUrlWebAuth.dao;

import com.vardhan.tinyUrlWebAuth.dto.User;

public interface LoginDAO {
	public User findUserByUsername(String username);
}
