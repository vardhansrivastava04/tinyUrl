package com.vardhan.tinyUrlWebAuth.dto;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("url")
public class Url implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "shorturl", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	@Column("shorturl")
	private String shorturl;

	@Column("url")
	private String url;

	@Column("createdat")
	private Long createdat;

	@Column("expireat")
	private Long expireat;

	@Column("userid")
	private String userid;

	public String getShorturl() {
		return shorturl;
	}

	public void setShorturl(String shorturl) {
		this.shorturl = shorturl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Long createdat) {
		this.createdat = createdat;
	}

	public Long getExpireat() {
		return expireat;
	}

	public void setExpireat(Long expireat) {
		this.expireat = expireat;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}



}