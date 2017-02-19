package com.connectfour.objects;

import java.util.Random;

import lombok.Data;

@Data
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
		
		/*If its an AI game*/
		if(!isTwoPlayer)
		{
			isPlayerTwoSet = true;
			isPlayerTwoAi = true;
			
			/*Generate an AI Player with the same ID as GameId*/
			playerTwo = new Player(gameId, "AI");
		}
	}
	
	private Integer generateGameId()
	{
		Random rnd = new Random();
		return 100000 + rnd.nextInt(900000);
		
	}
}
