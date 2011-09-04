package com.davidykay.funge.interpreter;

import android.graphics.Point;

public class InstructionPointer {

  private Point mPosition;
  private Point mHeading;

  public InstructionPointer() {
    this(new Point());
  }
  
  public InstructionPointer(Point position) {
    this(position, new Point());
  }

  public InstructionPointer(Point position, Point heading) {
    mPosition = position;
    mHeading = heading;
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
}
