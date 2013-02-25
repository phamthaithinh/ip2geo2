package com.jpayment.rest.api;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.jpayment.rest.RestPath;
import com.jpayment.rest.RestSupport;
import com.jpayment.rest.api.object.IPGeoCountryRequest;
import com.jpayment.rest.api.object.IPGeoCountryResponse;
import com.jpayment.rest.api.service.IPGeoService;
import com.jpayment.rest.api.service.ipml.IPGeoServiceImpl;
import com.jpayment.rest.entity.IPGeoCountry;

@Path("/" + RestPath.IP_INFO)
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class IP2GeoAPI extends RestSupport {
	private static Logger log = Logger.getLogger(IP2GeoAPI.class);

	@POST
	@Path("/" + RestPath.COUNTRY)
	public Response getCountry(@Context final UriInfo uriInfo,
			@Context final HttpHeaders httpHeaders, final String request) {
		log.info(request);
		if (request == null | request.equals("")) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		try {
			IPGeoCountryRequest req = IPGeoCountryRequest.parser(request);
			IPGeoService service = new IPGeoServiceImpl();
			if (req.getIp() == null | req.getIp().equals("")) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			IPGeoCountry out = service.getCountry(req.getIp());
			IPGeoCountryResponse response = new IPGeoCountryResponse();
			if (out != null) {
				response.setCode2(out.getCode2());
				response.setName(out.getName());
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

	@POST
	@Path("/" + RestPath.CLEAR)
	public Response delete() {
		IPGeoService service = new IPGeoServiceImpl();
		try {
			log.info("clear db");
			service.deletedb();
			IPGeoCountryResponse response = new IPGeoCountryResponse();
			response.setMsg("success");
			response.setSuccess("true");
			return createResponse(response).build();
		} catch (Exception ex) {
			log.error("delete", ex);
			IPGeoCountryResponse response = new IPGeoCountryResponse();
			response.setSuccess("false");
			response.setMsg(ex.getMessage());
			return createFailurResponse(response).build();
		}

	}

	@POST
	@Path("/" + RestPath.RELOAD)
	public Response reload() {
		log.info("Reload");
		IPGeoService service = new IPGeoServiceImpl();
		try {
			service.reload();
			IPGeoCountryResponse response = new IPGeoCountryResponse();
			response.setMsg("success");
			response.setSuccess("true");
			return createResponse(response).build();
		} catch (Exception ex) {
			log.error("reload", ex);
			IPGeoCountryResponse response = new IPGeoCountryResponse();
			response.setSuccess("false");
			response.setMsg(ex.getMessage());
			return createFailurResponse(response).build();
		}

	}
}
