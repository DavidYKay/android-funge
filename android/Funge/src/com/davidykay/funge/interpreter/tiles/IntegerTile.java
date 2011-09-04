package com.davidykay.funge.interpreter.tiles;

/**
 * A Tile that contains an integer.
 */
public class IntegerTile extends Tile {
  private int mInt;

  public IntegerTile(int value) {
    mInt = value;
  }

  public int getInteger() {
    return mInt;
  }
}
