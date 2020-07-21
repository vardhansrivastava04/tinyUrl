package com.vardhan.tinyUrlWebAuth.service;

import java.util.List;

import com.vardhan.tinyUrlWebAuth.dto.Url;
import com.vardhan.tinyUrlWebAuth.dto.Urllog;

public interface TinyUrlService {
	public List<Url> getCreatedUrl(String username);

	public String getLongUrl(String shortUrl, String usrname);

	public String generateTinyUrl(String url, String username);

	public List<Urllog> getUrlLogs(String shortUrl);
}
