package com.connectfour.object.responses;

import com.connectfour.miscellaneous.BoardSerializer;
import com.connectfour.objects.Board;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class GameResponse {

	String id;
	String status;

	@JsonSerialize(using = BoardSerializer.class)
	Board board;
}
