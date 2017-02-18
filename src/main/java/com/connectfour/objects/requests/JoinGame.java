package com.connectfour.objects.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JoinGame {

	boolean newGame;
	
	/* New Game fields*/
	String username;
	String name;
	boolean twoPlayer;
	
	/*
	 * Only relevant when twoPlayer = false. Thus the opponent is an AI. For the purposes of this
	 * task, all difficulty levels play the same.
	 */
	String difficulty;
	
	/*board configuration*/
	int rows;
	int columns;
	
	/* Join Game*/
	String gameId;
	
	/*No authorization feature as of yet.*/
	
	
}
