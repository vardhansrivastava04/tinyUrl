package com.vardhan.tinyUrlWebAuth.service;

import java.util.List;

import com.vardhan.tinyUrlWebAuth.dto.Url;

public interface TinyUrlService {
	public List<Url> getCreatedUrl(String username);

	public String getLongUrl(String shortUrl, String usrname);

	public String generateTinyUrl(String url, String username);
}
