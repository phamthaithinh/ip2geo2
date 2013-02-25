package com.jpayment.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

public class RestSupport {
	protected ResponseBuilder createResponse(ResponseObject obj) {
		ResponseBuilder builder = Response.status(Status.OK);
		builder = builder.type(MediaType.APPLICATION_JSON_TYPE);
		builder = builder.entity(obj.toJson());
		return builder;
	}
	protected ResponseBuilder createFailurResponse(ResponseObject obj) {
		ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
		builder = builder.type(MediaType.APPLICATION_JSON_TYPE);
		builder = builder.entity(obj.toJson());
		return builder;
	}
}
