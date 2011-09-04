package com.davidykay.funge.interpreter;

import android.graphics.Point;

import com.davidykay.funge.interpreter.tiles.Tile;

public interface FungeModel {

  public int getWidth();
  public int getHeight();
  
  public InstructionPointer getPointer();
  
  public Tile tileAtLocation(Point point);
  public Tile tileAtLocation(int x, int y);
  
  public void setTileAtLocation(Tile tile, Point point);
  public void setTileAtLocation(Tile tile, int x, int y);

  /** Advance to the next step of the simulation. */
  public void advance();

}
