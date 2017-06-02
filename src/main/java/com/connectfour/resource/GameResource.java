package com.connectfour.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.connectfour.manager.BijliManager;
import com.connectfour.manager.GameManager;
import com.connectfour.object.responses.BijliBillResponse;
import com.connectfour.object.responses.GameResponse;
import com.connectfour.objects.requests.BijliBillRequest;
import com.connectfour.objects.requests.GameRequest;
import com.connectfour.objects.requests.MoveRequest;


@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {
	
	GameManager manager = new GameManager();
	BijliManager bijliManager = new BijliManager();
	
	@POST
	@Path("/join")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public GameResponse joinGame(GameRequest request) throws Exception
	{
		return manager.joinGame(request);
	}
	
	@POST
	@Path("/bijli")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BijliBillResponse getBillAmount(BijliBillRequest request) throws Exception
	{
		return bijliManager.getBillAmount(request);
	}
	
	@POST
	@Path("/move")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public GameResponse makeMove(MoveRequest request) throws Exception
	{
		return manager.makeMove(request);
	}

}
