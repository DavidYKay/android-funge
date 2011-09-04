package com.davidykay.funge.interpreter;

import java.util.Stack;

import android.graphics.Point;

import com.davidykay.funge.interpreter.tiles.Tile;

/**
 * Can move around the funge space as well as carrying a stack.
 */
public class InstructionPointer {

  private Point mPosition;
  private Point mHeading;
  private Stack<Tile> mStack;

  public InstructionPointer() {
    this(new Point());
  }
  
  public InstructionPointer(Point position) {
    this(position, new Point());
  }

  public InstructionPointer(Point position, Point heading) {
    mPosition = position;
    mHeading = heading;
    mStack = new Stack<Tile>();
  }

  /**
   * Move in the direction of the current facing.
   * @param distance
   */
  public void move() {
    getPosition().offset(getHeading().x, getHeading().y);
  }

  public void setPosition(Point mPosition) {
    this.mPosition = mPosition;
  }

  public Point getPosition() {
    return mPosition;
  }

  public void setHeading(Point mHeading) {
    this.mHeading = mHeading;
  }

  public Point getHeading() {
    return mHeading;
  }

  public void pushTile(Tile tile) {
    mStack.push(tile);
  }
  
  public Tile popTile() {
    return mStack.pop();
  }
}
