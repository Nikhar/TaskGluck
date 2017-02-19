package com.connectfour.miscellaneous;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.connectfour.objects.Board;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BoardSerializer extends StdSerializer<Board>{

	public BoardSerializer()
	{
		this(null);
	}
	
    public BoardSerializer(Class<Board> t) {
        super(t);
    }

	@Override
	public void serialize(Board board, JsonGenerator arg1, SerializerProvider arg2) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		String[][] arg = board.getBoard();
		int rows = arg.length;
		int columns = arg[0].length;
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
				sb.append(arg[i][j]);
			
			list.add(sb.toString());
			sb = new StringBuilder();
		}
		arg1.writeObject(list);
	}

}
