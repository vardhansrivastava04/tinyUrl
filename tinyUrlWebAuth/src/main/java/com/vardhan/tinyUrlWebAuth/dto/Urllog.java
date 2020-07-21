package com.vardhan.tinyUrlWebAuth.dto;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("urllog")
public class Urllog implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Column("shorturl")
	private String shorturl;

	@PrimaryKeyColumn(name = "accessedat", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
	@Column("accessedat")
	private Long accessedat;

	@PrimaryKeyColumn(name = "userid", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
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