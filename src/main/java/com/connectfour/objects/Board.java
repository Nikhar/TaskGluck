package com.connectfour.objects;

import com.connectfour.constants.Constants;
import com.connectfour.exceptions.InvalidMoveException;

public class Board {
	
	int rows;
	int columns;
	String[][] board;
	
	public Board()
	{
		rows = Constants.BOARD_WIDTH;
		columns = Constants.BOARD_HEIGHT;
		board = new String[rows][columns];
	}
	
	public Board(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		board = new String[rows][columns];
	}
	
	/*
	 * .: empty cell
	 * 1: player 1
	 * 2: player 2
	 * */
	public String[][] move(String player, int column) throws InvalidMoveException
	{
		int i = rows - 1;
		while(i >= 0)
		{
			if(!board[i][column].equals("."))
			{
				i--;
			}
			else
			{
				board[i][column] = player;
				return board;
			}
		}
		
		throw new InvalidMoveException("The specified column is full.");
	}
	

}
