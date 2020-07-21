package com.vardhan.tinyUrlWebAuth.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.vardhan.tinyUrlWebAuth.dao.LoginDAO;
import com.vardhan.tinyUrlWebAuth.dto.User;

public class LoginDAOImpl implements LoginDAO {

	@Autowired
	CassandraOperations cassandraTemplate;

	@Override
	public User findUserByUsername(String username) {
		String query = "SELECT * FROM tiny.user WHERE username = '" + username + "'";
		List<User> output = (List<User>) cassandraTemplate.select(query, User.class);
		User current = null;
		if (output != null && !output.isEmpty()) {
			current = output.get(0);
			return current;
		} else {
			return null;
		}
	}

}
