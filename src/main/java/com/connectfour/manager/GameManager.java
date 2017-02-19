package com.connectfour.manager;

import com.connectfour.exceptions.InvalidMoveException;
import com.connectfour.miscellaneous.Constants;
import com.connectfour.miscellaneous.JedisWrapper;
import com.connectfour.object.responses.GameResponse;
import com.connectfour.objects.Board;
import com.connectfour.objects.Game;
import com.connectfour.objects.Player;
import com.connectfour.objects.requests.GameRequest;
import com.connectfour.objects.requests.MoveRequest;
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
	
	public GameResponse makeMove(MoveRequest request)
	{
		String gameString = JedisWrapper.getJedis(Constants.SAVE_PREFIX + request.getGameId());
		Game game = null;
		GameResponse response = new GameResponse();
		try
		{
			game = new ObjectMapper().readValue(gameString, Game.class);
			String victor = game.getBoard().move("1", request.getColumn());
			if(victor.equals("1"))
				response.setStatus("YOU WON!");
			else response.setStatus("IN PROGRESS");
			game.setResult(response.getStatus());
			JedisWrapper.setJedis(Constants.SAVE_PREFIX + request.getGameId(), game);
			response.setId(game.getGameId());
			response.setBoard(game.getBoard());
		}
		catch(InvalidMoveException e)
		{
			response.setStatus(e.getMessage());
		}
		catch (Exception e)
		{
			
			response.setStatus("ERROR: Unable to load the string with error " + e.getMessage());
		}
		return response;
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
			JedisWrapper.setJedis(Constants.SAVE_PREFIX+game.getGameId(), game);
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
