package com.connectfour.manager;

import com.connectfour.object.responses.GameResponse;
import com.connectfour.objects.Board;
import com.connectfour.objects.Game;
import com.connectfour.objects.Player;
import com.connectfour.objects.requests.GameRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameManager {
	
	public GameResponse joinGame(GameRequest request)
	{
		if(request.isNewGame())
			return createGame(request);
		return joinExistingGame(request);
	}
	
	
	/*Private methods*/
	private GameResponse joinExistingGame(GameRequest request) {
		return null;
	}


	private GameResponse createGame(GameRequest request)
	{
		Game game = new Game(request.isTwoPlayer(), request.getDifficulty());
		game.setBoard(new Board(request.getRows(), request.getColumns()));
		game.setPlayerOne(new Player(request.getUsername(), "HUMAN", request.getName()));
		
		return assembleGameResponse(game);
	}


	private GameResponse assembleGameResponse(Game game) {
		GameResponse response = new GameResponse();
		response.setId(game.getGameId());
		response.setStatus("In Progress");
		response.setBoard(game.getBoard());
		try
		{
		System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return response;
	}
	

}
