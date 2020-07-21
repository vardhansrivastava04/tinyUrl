package com.vardhan.tinyUrlWebAuth.dto;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("url")
public class Urllog implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "shorturl", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	@Column("shorturl")
	private String shorturl;

	@Column("accessedat")
	private Long accessedat;

	@PrimaryKeyColumn(name = "userid", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	@Column("userid")
	private String userid;

	public String getShorturl() {
		return shorturl;
	}

	public void setShorturl(String shorturl) {
		this.shorturl = shorturl;
	}

	public Long getAccessedat() {
		return accessedat;
	}

	public void setAccessedat(Long accessedat) {
		this.accessedat = accessedat;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}



}