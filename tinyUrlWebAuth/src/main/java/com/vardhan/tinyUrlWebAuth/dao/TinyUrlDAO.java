package com.vardhan.tinyUrlWebAuth.dao;

import java.util.List;

import com.vardhan.tinyUrlWebAuth.dto.Url;

public interface TinyUrlDAO {
	public String shortenUrl(String url,String user);

	public String getUrl(String shortUrl);

	public List<Url> getCreatedUrl(String username);

	public String getLongUrl(String shortUrl);

	public String generateTinyUrl(String url, String username);

}
