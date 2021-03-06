package com.davidykay.funge.interpreter;

import java.util.Observable;

import thanks.javax.swing.event.EventListenerList;

import android.graphics.Point;

import com.davidykay.funge.interpreter.tiles.CodeTile;
import com.davidykay.funge.interpreter.tiles.InvalidTileException;
import com.davidykay.funge.interpreter.tiles.Tile;
import com.davidykay.funge.interpreter.tiles.ValueTile;

public class DefaultFungeModel implements FungeModel {

  private FungePlane mPlane;
  private InstructionPointer mPointer;
  //private EventListenerList mListenerList = new EventListenerList();
  private ModelObservable mObservable = new ModelObservable();
  
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
    if (newTile != null) {
      consume(newTile);
    }

    // Notify listeners
    mObservable.setChangedFlag();
    mObservable.notifyObservers(this);
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
      throw new IllegalArgumentException(
          String.format("Invalid tile type given: %s", tile.getClass()));
    }
  }

  public void addListener(FungeModelListener l) {
    //mListenerList.add(FungeModelListener.class, l);
    mObservable.addObserver(l);
  }
      
  public void removeListener(FungeModelListener l) {
    mObservable.deleteObserver(l);
    //mListenerList.remove(FungeModelListener.class, l);
  }

  private class ModelObservable extends Observable {
    public void setChangedFlag() {
      setChanged();
    }
  }
}
