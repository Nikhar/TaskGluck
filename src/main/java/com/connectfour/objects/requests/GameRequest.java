package com.connectfour.objects.requests;

import com.connectfour.miscellaneous.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameRequest {

	boolean newGame;
	
	/* New Game fields*/
	String username;
	String name;
	boolean twoPlayer = false;
	
	/*
	 * Only relevant when twoPlayer = false. Thus the opponent is an AI. For the purposes of this
	 * task, all difficulty levels play the same.
	 */
	String difficulty = "rookie";
	
	/*board configuration*/
	Integer rows = Constants.BOARD_HEIGHT;
	Integer columns = Constants.BOARD_WIDTH;
	
	/* Join Game*/
	String gameId;
	
	/*No authorization feature as of yet.*/
	
	
}
