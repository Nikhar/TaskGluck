package com.connectfour.objects;

import java.util.Random;

import com.connectfour.ai.AI;
import com.connectfour.objects.Player.PlayerType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Game {
	
	String gameId;
	
	Player playerOne;
	Player playerTwo;
	boolean isPlayerTwoSet;
	boolean isPlayerTwoAi;

	Board board;
	Player nextTurn;
	
	String result;
	String difficulty;
	
	public Game(boolean isTwoPlayer, String difficulty)
	{
		gameId = String.valueOf(generateGameId());
		this.difficulty = difficulty;
		result = "IN PROGRESS";
		
		/*If its an AI game*/
		if(!isTwoPlayer)
		{
			isPlayerTwoSet = true;
			isPlayerTwoAi = true;
			
			/*Generate an AI Player with the same ID as GameId*/
			playerTwo = new Player(gameId, "AI");
		}
	}
	
	public void move(int column) throws Exception
	{
		String victor = ".";
		if(nextTurn.getId().equals(playerOne.getId()))
		{
			nextTurn = playerTwo;
			victor = board.move("1", column);
		}
		else
		{
			nextTurn = playerOne;
			victor = board.move("2", column);
		}
		if(victor.equals("1") || victor.equals("2"))
		{
			this.setResult("Player" + victor + " WON!");
			return;
		}
	}
	
	public void makeAIMove()
	{
		int column = new AI(difficulty, board).nextMove();
		try
		{
			move(column);
			
			if(result != null && result.equals("YOU WON!"))
				result = "AI wins";
		}
		catch(Exception e)
		{
			try {
				System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(e.getStackTrace()));

			} catch (Exception e2) {
				// TODO: handle exception
			}
			result = "Sorry but the AI failed to make a move";
		}
	}
	
	private Integer generateGameId()
	{
		Random rnd = new Random();
		return 100000 + rnd.nextInt(900000);
		
	}
	
	String error;
}
