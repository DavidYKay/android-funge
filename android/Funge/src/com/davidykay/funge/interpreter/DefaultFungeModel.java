package com.davidykay.funge.interpreter;

import android.graphics.Point;

import com.davidykay.funge.interpreter.tiles.Tile;

public class DefaultFungeModel implements FungeModel {

  private FungePlane mPlane;
  private InstructionPointer mPointer;
  
  public DefaultFungeModel(int width, int height) {
    mPlane = new FungePlane(width, height);
    mPointer = new InstructionPointer();
  }

  public int getWidth() {
    return mPlane.getWidth();
  }

  public int getHeight() {
    return mPlane.getHeight();
  }

  public Tile tileAtLocation(int x, int y) {
    return mPlane.tileAtLocation(x, y);
  }

  public InstructionPointer getPointer() {
    return mPointer;
  }

  public Tile tileAtLocation(Point point) {
    return mPlane.tileAtLocation(point);
  }

  public void setTileAtLocation(Tile tile, Point point) {
    mPlane.setTileAtLocation(tile, point);
  }

  public void setTileAtLocation(Tile tile, int x, int y) {
    mPlane.setTileAtLocation(tile, x, y);
  }
}
