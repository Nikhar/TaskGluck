package com.connectfour.objects.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BijliBillRequest {
	
	boolean commercial;
	String level;
	int kilowatt;
	int previous;
	int current;
	boolean urban;
	String phase;
}
