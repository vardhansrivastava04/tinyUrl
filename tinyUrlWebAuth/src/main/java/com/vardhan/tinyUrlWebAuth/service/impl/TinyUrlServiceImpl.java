package com.vardhan.tinyUrlWebAuth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vardhan.tinyUrlWebAuth.dao.TinyUrlDAO;
import com.vardhan.tinyUrlWebAuth.dto.Url;
import com.vardhan.tinyUrlWebAuth.service.TinyUrlService;

public class TinyUrlServiceImpl implements TinyUrlService{

	@Autowired
    TinyUrlDAO tinyUrlDAO;
	@Override
	public List<Url> getCreatedUrl(String username) {
		return tinyUrlDAO.getCreatedUrl(username);
	}
	@Override
	public String getLongUrl(String shortUrl) {
		return tinyUrlDAO.getLongUrl(shortUrl);
	}
	@Override
	public String generateTinyUrl(String url, String username) {
		return tinyUrlDAO.generateTinyUrl(url,username);
	}

}
