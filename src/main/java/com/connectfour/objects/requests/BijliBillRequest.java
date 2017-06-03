package com.connectfour.objects.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BijliBillRequest {
	
	int previous;
	int current;
	boolean urban;
	String phase;
}
