package com.connectfour.ai;

import java.util.Random;

import com.connectfour.objects.Board;

public class AI {

	String difficulty;
	Board board;
	
	public AI(String difficulty, Board board)
	{
		this.difficulty = difficulty;
		this.board = board;
	}
	
	/*A very dumb AI for purposes of test which returns random column.*/
	public int nextMove()
	{
		Random random = new Random();
		int column = random.nextInt(board.getColumns());
		/*
		 * The column must not be full
		 * */
		while(!board.getBoard()[0][column].equals("."))
			column = random.nextInt(board.getColumns());
		return column;
	}
}
