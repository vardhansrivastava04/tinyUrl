package com.vardhan.tinyUrlWebAuth.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.vardhan.tinyUrlWebAuth.Utils.TinyUrlUtil;
import com.vardhan.tinyUrlWebAuth.dto.Urllog;
import com.vardhan.tinyUrlWebAuth.service.TinyUrlService;

@RestController
public class TinyUrlController {

	@Value("${app.url}")
	String prefixUrl;

	@Autowired
	HttpServletResponse httpServletResponse;
	
	
	@Autowired
	TinyUrlService tinyUrlService;

	@GetMapping(path = "/{shortUrl}")
	public RedirectView getUrl(@PathVariable("shortUrl") String shortUrl) {
		System.out.println(shortUrl);
		String longUrl = tinyUrlService.getLongUrl(shortUrl,TinyUrlUtil.getLoggedInUserName());
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

	@GetMapping(path = "/urlExpired")
	public String getExpired() {
		return "Url Expired";
	}

	@GetMapping(path = "/urlNotFound")
	public String urlNotFound() {
		return "Url Not Found";
	}

}
