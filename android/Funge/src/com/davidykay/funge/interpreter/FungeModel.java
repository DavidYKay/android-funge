package com.davidykay.funge.interpreter;

import com.davidykay.funge.interpreter.tiles.Tile;

public interface FungeModel {

  public int getWidth();
  public int getHeight();

  public Tile tileAtLocation(int x, int y);

}
