package com.vardhan.tinyUrlWebAuth.dao.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.vardhan.tinyUrlWebAuth.dao.TinyUrlDAO;
import com.vardhan.tinyUrlWebAuth.dto.Url;

public class TinyUrlDAOImpl implements TinyUrlDAO {

	@Autowired
	CassandraOperations cassandraTemplate;

	@Value("${app.url}")
	String prefixUrl;

	@Autowired
	HttpServletResponse httpServletResponse;

	@Override
	public String shortenUrl(String url, String user) {
		Url saveUrl = new Url();
		Long time = Calendar.getInstance().getTimeInMillis();
		saveUrl.setCreatedat(time);
		saveUrl.setExpireat(time + 259200000);
		String mdString = getMd5(url);
		String shortUrl = (String) mdString.subSequence(0, 6);
		saveUrl.setShorturl(shortUrl);
		cassandraTemplate.insert(url);
		return prefixUrl + shortUrl;
	}

	@Override
	public String getUrl(String shortUrl) {
		System.out.println(shortUrl);
		String query = "SELECT * FROM tiny.url WHERE shorturl = '" + shortUrl + "'";
		List<Url> output = (List<Url>) cassandraTemplate.select(query, Url.class);
		Url current = null;
		if (output != null && !output.isEmpty()) {
			current = output.get(0);
			if (Calendar.getInstance().getTimeInMillis() < current.getExpireat()) {
				return current.getUrl();
			} else {
				return "EXPIRED";
			}
		} else {
			return "NOT_FOUND";
		}
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

	@Override
	public List<Url> getCreatedUrl(String username) {
		String query = "SELECT * FROM tiny.url WHERE userid = '" + username + "'";
		List<Url> output = (List<Url>) cassandraTemplate.select(query, Url.class);
		return output;
	}

	@Override
	public String getLongUrl(String shortUrl) {
		String query = "SELECT * FROM tiny.url WHERE shorturl = '" + shortUrl + "'";
		List<Url> output = (List<Url>) cassandraTemplate.select(query, Url.class);
		Url current = null;
		String ret = null;
		if (output != null && !output.isEmpty()) {
			current = output.get(0);
			ret = current.getUrl();
			if (Calendar.getInstance().getTimeInMillis() > current.getExpireat()) {
				ret = "urlExpired";

			}
		}
		return ret;
	}

	@Override
	public String generateTinyUrl(String longUrl, String username) {
		Long time = Calendar.getInstance().getTimeInMillis();
		Url url = new Url();
		url.setCreatedat(time);
		url.setExpireat(time + 259200000);
		url.setUrl(longUrl);
		url.setUserid(username);
		String mdString = getMd5(url.getUrl());
		String shortUrl = (String) mdString.subSequence(0, 6);
		url.setShorturl(shortUrl);
		cassandraTemplate.insert(url);

		return shortUrl;
	}

}
