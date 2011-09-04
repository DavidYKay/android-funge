package com.davidykay.funge.interpreter;

import android.graphics.Point;

import com.davidykay.funge.interpreter.tiles.Tile;

public class FungePlane implements FungeModel {

  public static final int NUM_ROWS    = 4;
  public static final int NUM_COLUMNS = 4;

  private Tile[][] mTiles;

  public FungePlane() {
    this(NUM_ROWS, NUM_COLUMNS);
  }

  public FungePlane(int width, int height) {
    mTiles = new Tile[width][height];
  }

  public int getWidth() {
    return mTiles.length;
  }

  public int getHeight() {
    return mTiles[0].length;
  }
  
  public Tile tileAtLocation(Point point) {
    return tileAtLocation(point.x, point.y);
  }
  
  public Tile tileAtLocation(int x, int y) {
    return mTiles[x][y];
  }
  
  public void setTileAtLocation(Tile tile, Point point) {
    setTileAtLocation(tile, point.x, point.y);
  }
  
  public void setTileAtLocation(Tile tile, int x, int y) {
    mTiles[x][y] = tile;
  }
}
