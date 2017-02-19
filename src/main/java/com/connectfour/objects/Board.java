package com.connectfour.objects;

import java.util.ArrayList;
import java.util.List;

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
	public String move(String player, int column) throws InvalidMoveException
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
				return getVictor(i, column);
			}
		}
		
		throw new InvalidMoveException("The specified column is full.");
	}
	
	private String getVictor(int row, int column) {

		String player = board[row][column];
		if(isColumnVictory(row,column)) return player;
		if(isRowVictory(row,column)) return player;
		if(isLeadingDiagonalVictory(row, column)) return player;
		if(isCounterDiagonalVictory(row, column)) return player;
		
		return ".";
	}

	private boolean isColumnVictory(int row, int column)
	{
		String player = board[row][column];
		int count = 0;
		for(int i = row; i < rows; i++)
		{
			if(!board[i][column].equals(player))
				break;
			count++;
		}
		if(count >= 4) return true;
		return false;
	}
	
	private boolean isRowVictory(int row, int column)
	{
		String player = board[row][column];
		int j = column;
		while(j >= 0 && board[row][j].equals(player)) j--;
		j++;
		int count = 0;
		while(j < columns && board[row][j].equals(player))
		{
			j++;
			count++;
		}
		if(count>=4) return true;
		return false;
	}

	private boolean isLeadingDiagonalVictory(int row, int column)
	{
		String player = board[row][column];
		int i = row, j = column;
		for(; i >= 0 && j >= 0 && board[i][j].equals(player); i--, j--);
		i++;j++;
		int count = 0;
		for(; i < rows && j < columns && board[i][j].equals(player); i++, j++)
			count++;
		if(count >= 4) return true;
		return false;
	}
	
	private boolean isCounterDiagonalVictory(int row, int column)
	{
		String player = board[row][column];
		int i = row, j = column;
		for(; i < rows && j >= 0 && board[i][j].equals(player); i++, j--);
		i--; j++;
		int count = 0;
		for(; i >= 0 && j < columns && board[i][j].equals(player); i--, j++)
			count++;
		if(count >= 4) return true;
		return false;
	}
	
	private void fillEmptyBoard()
	{
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				board[i][j]=".";
	}
	
	public static void main(String[] args)
	{
		List<String> pattern = new ArrayList<String>();
		pattern.add("..2222");
		Board board = new Board(1, 6);
		board.createBoard(pattern);
		System.out.println(board.isRowVictory(0, 4));
	}
	
	private void createBoard(List<String> pattern)
	{
		for(int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
				board[i][j] = ""+pattern.get(i).charAt(j);
		}
	}

}
