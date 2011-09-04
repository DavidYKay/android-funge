package com.davidykay.funge.interpreter.tiles;

public class InvalidTileException extends Exception {

	public InvalidTileException(String format) {
		super(format);
	}

}
