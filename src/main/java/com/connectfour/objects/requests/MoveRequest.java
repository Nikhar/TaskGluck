package com.connectfour.objects.requests;

import lombok.Data;

@Data
public class MoveRequest {
	String gameId;
	String username;
	int column;
}
