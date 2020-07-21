package com.vardhan.tinyUrlWebAuth.dao;

import java.util.List;

import com.vardhan.tinyUrlWebAuth.dto.Url;
import com.vardhan.tinyUrlWebAuth.dto.Urllog;

public interface TinyUrlDAO {
	public String shortenUrl(String url,String user);

	public String getUrl(String shortUrl);

	public List<Url> getCreatedUrl(String username);

	public String getLongUrl(String shortUrl, String username);

	public String generateTinyUrl(String url, String username);

	public List<Urllog> getUrlLogs(String shortUrl);

}
