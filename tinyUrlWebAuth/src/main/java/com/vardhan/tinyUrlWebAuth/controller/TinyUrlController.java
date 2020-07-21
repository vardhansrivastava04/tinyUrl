package com.vardhan.tinyUrlWebAuth.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.vardhan.tinyUrlWebAuth.dto.Url;
import com.vardhan.tinyUrlWebAuth.service.TinyUrlService;

@RestController
public class TinyUrlController {

	@Autowired
	CassandraOperations cassandraTemplate;

	@Value("${app.url}")
	String prefixUrl;

	@Autowired
	HttpServletResponse httpServletResponse;
	
	
	@Autowired
	TinyUrlService tinyUrlService;

	@PostMapping(path = "/generate")
	public String save(@RequestBody Url url) {
		Long time = Calendar.getInstance().getTimeInMillis();
		url.setCreatedat(time);
		url.setExpireat(time + 259200000);
		String mdString = getMd5(url.getUrl());
		String shortUrl = (String) mdString.subSequence(0, 6);
		url.setShorturl(shortUrl);
		cassandraTemplate.insert(url);
		return prefixUrl + shortUrl;
	}

	@GetMapping(path = "/{shortUrl}")
	public RedirectView getUrl(@PathVariable("shortUrl") String shortUrl) {
		System.out.println(shortUrl);
		String longUrl = tinyUrlService.getLongUrl(shortUrl);
		RedirectView redirectView = new RedirectView();
		if (longUrl != null && !longUrl.isEmpty() && longUrl.equalsIgnoreCase("urlExpired")) {
			redirectView.setUrl("http://localhost:8080/urlExpired");
		} else if (longUrl != null && !longUrl.isEmpty()) {
			redirectView.setUrl(longUrl);
		} else {
			redirectView.setUrl("http://localhost:8080/urlExpired");
		}
		return redirectView;
	}

	@GetMapping(path = "/test/{shortUrl}")
	public String getHi(@PathVariable("shortUrl") String shortUrl) {
		return shortUrl;
	}

	public static String getMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping(path = "/urlExpired")
	public String getExpired() {
		return "Url Expired";
	}

	@GetMapping(path = "/urlNotFound")
	public String urlNotFound() {
		return "Url Not Found";
	}
	
	
	

}
