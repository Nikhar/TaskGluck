package com.connectfour.objects;

import com.connectfour.exceptions.InvalidMoveException;
import com.connectfour.miscellaneous.Constants;

import lombok.Data;

@Data
public class Board {
	
	int rows;
	int columns;
	String[][] board;
	
	public Board()
	{
		rows = Constants.BOARD_WIDTH;
		columns = Constants.BOARD_HEIGHT;
		board = new String[rows][columns];
		fillEmptyBoard();
	}
	
	public Board(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		board = new String[rows][columns];
		fillEmptyBoard();
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
	
	private void fillEmptyBoard()
	{
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				board[i][j]=".";
	}
	

}
