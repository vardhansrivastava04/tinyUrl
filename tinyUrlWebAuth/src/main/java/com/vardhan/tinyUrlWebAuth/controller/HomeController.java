package com.vardhan.tinyUrlWebAuth.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vardhan.tinyUrlWebAuth.constants.TinyUrlConstants;
import com.vardhan.tinyUrlWebAuth.dto.Url;
import com.vardhan.tinyUrlWebAuth.dto.UrlRequest;
import com.vardhan.tinyUrlWebAuth.service.TinyUrlService;

@Controller
public class HomeController {

	@Autowired
	TinyUrlService tinyUrlService;

	@Autowired
	CassandraOperations cassandraTemplate;

	@Value("${app.url}")
	String prefixUrl;

	@Autowired
	HttpServletResponse httpServletResponse;

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}

	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout";
	}

	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/generate_tiny")
	public String generateTiny() {
		return "generate_tiny";
	}

	@GetMapping("/generate_form")
	public String generateForm(Model model) {
		UrlRequest urlRequest = null;
		urlRequest = (UrlRequest) model.getAttribute(TinyUrlConstants.ATTRIBUTE_URL_REQUEST);
		if (urlRequest == null) {
			urlRequest = new UrlRequest();
			urlRequest.setUrl("Enter url");
			urlRequest.setShort_url("");
		}
		List<Url> createdUrl = tinyUrlService.getCreatedUrl(getLoggedInUserName());
		setGeneratePageModelAttributes(urlRequest, model, createdUrl);
		return "generate_tiny";
	}

	private String getLoggedInUserName() {
		String username = "123";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}


	@PostMapping(path = "/generate_url")
	public String saveShortUrl(@ModelAttribute("urlRequest") UrlRequest urlRequest, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors())
			return "error";
		String username = getLoggedInUserName();
		String shortUrl = tinyUrlService.generateTinyUrl(urlRequest.getUrl(), username);
		List<Url> createdUrl = tinyUrlService.getCreatedUrl(getLoggedInUserName());
		urlRequest.setShort_url(shortUrl);
		urlRequest.setAnswer(true);
		setGeneratePageModelAttributes(urlRequest, model, createdUrl);
		return "generate_tiny";
	}

	private void setGeneratePageModelAttributes(UrlRequest urlRequest, Model model, List<Url> createdUrl) {
		model.addAttribute(TinyUrlConstants.ATTRIBUTE_URL_REQUEST, urlRequest);
		model.addAttribute(TinyUrlConstants.ATTRIBUTE_CREATED_URL_LIST, createdUrl);
		model.addAttribute(TinyUrlConstants.ATTRIBUTE_PREFIX_URL, prefixUrl);
		model.addAttribute(TinyUrlConstants.ATTRIBUTE_CURRENT_TIME, Calendar.getInstance().getTimeInMillis());
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
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
