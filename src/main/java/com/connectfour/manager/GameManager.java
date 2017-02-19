package com.connectfour.manager;

import com.connectfour.miscellaneous.JedisWrapper;
import com.connectfour.object.responses.GameResponse;
import com.connectfour.objects.Board;
import com.connectfour.objects.Game;
import com.connectfour.objects.Player;
import com.connectfour.objects.requests.GameRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;

public class GameManager {
	ObjectMapper mapper = new ObjectMapper();
	
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
		
		saveGameStateToJedis(game);
		return assembleGameResponse(game);
	}


	private void saveGameStateToJedis(Game game) {
		try
		{
			JedisWrapper.setJedis("C4"+game.getGameId(), game);
		}
		catch (Exception e)
		{
			game.setError(e.getMessage());
		}
	}


	private GameResponse assembleGameResponse(Game game) {
		GameResponse response = new GameResponse();
		response.setId(game.getGameId());
		response.setStatus("In Progress");
		response.setBoard(game.getBoard());
		if(game.getError() != null) response.setStatus(game.getError());;
		return response;
	}
	

}
