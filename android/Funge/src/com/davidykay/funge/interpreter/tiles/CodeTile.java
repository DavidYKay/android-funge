package com.davidykay.funge.interpreter.tiles;

import com.davidykay.funge.interpreter.FungeModel;
import com.davidykay.funge.interpreter.InstructionPointer;

/**
 * A tile containing executable code.
 */
public abstract class CodeTile extends Tile {
  
  public abstract void execute(InstructionPointer pointer, FungeModel model) throws InvalidTileException;
}
