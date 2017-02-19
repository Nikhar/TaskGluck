package com.connectfour.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.connectfour.manager.GameManager;
import com.connectfour.object.responses.GameResponse;
import com.connectfour.objects.requests.GameRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {
	
	GameManager manager = new GameManager();
	
	@POST
	@Path("/join")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public GameResponse joinGame(GameRequest request) throws Exception
	{
		//System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request));
		return manager.joinGame(request);
	}

}
