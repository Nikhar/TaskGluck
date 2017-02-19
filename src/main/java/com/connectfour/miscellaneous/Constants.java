package com.connectfour.miscellaneous;

public final class Constants {
	
	
	public static final int BOARD_WIDTH = 6;
	public static final int BOARD_HEIGHT = 7;

	private Constants() {
		// this prevents even the native class from
		// calling this ctor as well :
		throw new AssertionError();
	}

}
