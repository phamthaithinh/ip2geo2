/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest;

public class AbstractResponse {
	private String success;
	private String msg;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
