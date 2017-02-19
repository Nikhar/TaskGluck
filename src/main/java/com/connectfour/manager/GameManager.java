package com.connectfour.manager;

import com.connectfour.exceptions.InvalidMoveException;
import com.connectfour.miscellaneous.Constants;
import com.connectfour.miscellaneous.JedisWrapper;
import com.connectfour.object.responses.GameResponse;
import com.connectfour.objects.Board;
import com.connectfour.objects.Game;
import com.connectfour.objects.Player;
import com.connectfour.objects.Player.PlayerType;
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
			System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(game));
			validateMove(game, request);
			
			game.move(request.getColumn());
			
			if(!game.getResult().equals("YOU WON!") 
					&& game.getNextTurn().getType() == PlayerType.AI)
				game.makeAIMove();
			
			JedisWrapper.setJedis(Constants.SAVE_PREFIX + request.getGameId(), game);
			
			response.setStatus(game.getResult());
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
	
	
	private void validateMove(Game game, MoveRequest request) throws InvalidMoveException{
		
		if(!game.isPlayerTwoSet() || game.getNextTurn() == null)
			throw new InvalidMoveException("Game needs to have 2 players before commencing!");
		
		Player currentTurn = game.getNextTurn();
		if(!currentTurn.getId().equals(request.getUsername()))
			throw new InvalidMoveException("It is not " + request.getUsername() + "'s turn to play!");
		
	}

	/*Private methods*/
	private GameResponse joinExistingGame(GameRequest request) {
		GameResponse response = new GameResponse();
		try
		{
			String gameString = JedisWrapper.getJedis(Constants.SAVE_PREFIX + request.getGameId());
			Game game = new ObjectMapper().readValue(gameString, Game.class);
			if(game.isPlayerTwoSet())
				response.setStatus("Can't join game. Both players already set.");
			else
			{
				game.setPlayerTwo(new Player(request.getUsername(), "HUMAN", request.getName()));
				game.setPlayerTwoSet(true);
				System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(game));
				JedisWrapper.setJedis(Constants.SAVE_PREFIX + request.getGameId(), game);
				response.setId(game.getGameId());
				response.setStatus("Succesfully joined as player two");
				response.setBoard(game.getBoard());
			}
		}
		catch(Exception e)
		{
			response.setStatus("Failed to load game");
		}
		return response;
	}


	private GameResponse createGame(GameRequest request)
	{
		Game game = new Game(request.isTwoPlayer(), request.getDifficulty());
		game.setBoard(new Board(request.getRows(), request.getColumns()));
		game.setPlayerOne(new Player(request.getUsername(), "HUMAN", request.getName()));
		game.setNextTurn(game.getPlayerOne());
		
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
