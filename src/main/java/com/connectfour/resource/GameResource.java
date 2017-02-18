package com.connectfour.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {
	
	@GET
	public String hello() throws Exception {
		return "hello";
	}

}
