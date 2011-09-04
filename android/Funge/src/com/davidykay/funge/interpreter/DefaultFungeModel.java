package com.davidykay.funge.interpreter;

import android.graphics.Point;

import com.davidykay.funge.interpreter.tiles.CodeTile;
import com.davidykay.funge.interpreter.tiles.InvalidTileException;
import com.davidykay.funge.interpreter.tiles.Tile;
import com.davidykay.funge.interpreter.tiles.ValueTile;

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

  public void advance() {
    // Move the IP.
    mPointer.move();
    // Consume the tile at the destination.
    Tile newTile = tileAtLocation(mPointer.getPosition());
    consume(newTile);
  }

  private void consume(Tile tile) {
    if (tile instanceof ValueTile) {
      mPointer.pushTile(tile);
    } else if (tile instanceof CodeTile) {
      // Execute tile
      try {
        ((CodeTile) tile).execute(mPointer, this);
      } catch (InvalidTileException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();		
      }
    } else {
      throw new IllegalArgumentException("Invalid tile type given!");
    }
  }

}
