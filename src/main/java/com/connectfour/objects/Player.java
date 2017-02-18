package com.connectfour.objects;

import java.util.Random;

import lombok.Data;

@Data
public class Player {
	
	public static enum PlayerType
	{
		HUMAN, AI, NONE;
	}
	
	String id;
	String name;
	PlayerType type;
	
	public Player(String id, String type)
	{
		fillFields(id, type);
		name = generateAIName();
		
	}
	
	public Player(String id, String type, String name)
	{
		fillFields(id, type);
		this.name = name;
	}

	
	private void fillFields(String id, String type)
	{
		this.id = id;
		if(type.equals("HUMAN"))
			this.type = PlayerType.HUMAN;
		else
			this.type = PlayerType.AI;
	}
	
	private String generateAIName()
	{
		String[] names = new String[] {"DeepThought", "HAL", "R2D2", "Marvin"};
		Random random = new Random();
		return names[random.nextInt(names.length)];
	}
}
