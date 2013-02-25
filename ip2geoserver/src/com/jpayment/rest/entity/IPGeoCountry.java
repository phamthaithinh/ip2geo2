package com.jpayment.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="dixi_ip2country")
public class IPGeoCountry {
	private Long id;
	private String fromSIp;
	private String toSIp;

	@Column(name = "from_s_ip")
	public String getFromSIp() {
		return fromSIp;
	}

	public void setFromSIp(String fromSIp) {
		this.fromSIp = fromSIp;
	}

	@Column(name = "to_s_ip")
	public String getToSIp() {
		return toSIp;
	}

	public void setToSIp(String toSIp) {
		this.toSIp = toSIp;
	}

	private Long fromIp;
	private Long toIp;
	private String code2;
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "from_ip")
	public Long getFromIp() {
		return fromIp;
	}

	public void setFromIp(Long fromIp) {
		this.fromIp = fromIp;
	}

	@Column(name = "to_ip")
	public Long getToIp() {
		return toIp;
	}

	public void setToIp(Long toIp) {
		this.toIp = toIp;
	}

	public String getCode2() {
		return code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
