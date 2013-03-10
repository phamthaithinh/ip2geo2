/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "dixi_geocity_block")
public class GeoCityBlock {
	private Long id;

	private Long startIP;

	private Long endIP;
	private Long localId;

	public Long getEndIP() {
		return endIP;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public Long getLocalId() {
		return localId;
	}

	public Long getStartIP() {
		return startIP;
	}

	public void setEndIP(Long endIP) {
		this.endIP = endIP;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLocalId(Long localId) {
		this.localId = localId;
	}

	public void setStartIP(Long startIP) {
		this.startIP = startIP;
	}
}
