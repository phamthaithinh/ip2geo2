/*
 * Copyright (c) 2013 jPayment.org. All Rights Reserved
 */
package com.jpayment.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.jpayment.rest.RestPath;
import com.jpayment.rest.RestSupport;
import com.jpayment.rest.api.object.IPGeoCityRequest;
import com.jpayment.rest.api.object.IPGeoCityResponse;
import com.jpayment.rest.api.object.IPGeoCountryResponse;
import com.jpayment.rest.api.service.IPGeoService;
import com.jpayment.rest.api.service.ipml.IPGeoServiceImpl;
import com.jpayment.rest.entity.GeoCityLocation;
import com.jpayment.rest.util.CountryMap;

@Path("/" + RestPath.ADMIN)
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class IP2GEOAdminAPI extends RestSupport {
	private static Logger log = Logger.getLogger(IP2GEOAdminAPI.class);

	@POST
	@Path("/" + RestPath.COUNTRY)
	public Response getCountry(@Context final UriInfo uriInfo,
			@Context final HttpHeaders httpHeaders, final String request) {
		log.info(request);
		if (request == null | request.equals("")) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		try {
			IPGeoCityRequest req = IPGeoCityRequest.parser(request);
			IPGeoService service = new IPGeoServiceImpl();
			if (req.getIp() == null | req.getIp().equals("")) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			GeoCityLocation out = service.getCity(req.getIp());
			IPGeoCityResponse response = new IPGeoCityResponse();
			if (out != null) {
				response.setCountryCode2(out.getCountryCode2());
				response.setAreaCode(out.getAreaCode());
				response.setCity(out.getCity());
				response.setLatitude(out.getLatitude());
				response.setLongitude(out.getLongitude());
				response.setMetroCode(out.getMetroCode());
				response.setPostalCode(out.getPostalCode());
				response.setRegion(out.getRegion());
				response.setCountryName(CountryMap.getCountry(out.getCountryCode2()));
				response.setMsg("success");
				response.setSuccess("true");
			} else {
				response.setSuccess("false");
				response.setMsg("Cannot find couttry");
				return createFailurResponse(response).build();
			}
			return createResponse(response).build();
		} catch (Exception ex) {
			log.error("getcountry", ex);
			IPGeoCountryResponse response = new IPGeoCountryResponse();
			response.setSuccess("false");
			response.setMsg(ex.getMessage());
			return createFailurResponse(response).build();
		}
	}
}
