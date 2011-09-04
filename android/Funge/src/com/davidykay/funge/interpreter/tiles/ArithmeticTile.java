package com.davidykay.funge.interpreter.tiles;

import com.davidykay.funge.interpreter.FungeModel;
import com.davidykay.funge.interpreter.InstructionPointer;


public abstract class ArithmeticTile extends Tile {
  public static class ArithmeticTileFactory {
    public static ArithmeticTile tileFromSymbol(char symbol) {
      ArithmeticTile tile = null;
      switch (symbol) {
        case '+':
          tile = new ArithmeticTile() {
            public char getSymbol() { return '+'; }
            public int performOperation(int a, int b) throws InvalidTileException {
              return a + b;
            }
          };
          break;			
        case '-':
          tile = new ArithmeticTile() {
            public char getSymbol() { return '-'; }
            public int performOperation(int a, int b) throws InvalidTileException {
              return a - b;
            }
          };
          break;			
        case 'x':
          tile = new ArithmeticTile() {
            public char getSymbol() { return 'x'; }
            public int performOperation(int a, int b) throws InvalidTileException {
              return a * b;
            }
          };
          break;			
        case '/':
          tile = new ArithmeticTile() {
            public char getSymbol() { return '/'; }
            public int performOperation(int a, int b) throws InvalidTileException {
              return a / b;
            }
          };
          break;			
      }
      return tile;
    }
  }
  
  public abstract char getSymbol();
  public abstract int performOperation(int a, int b) throws InvalidTileException;

  public void execute(InstructionPointer pointer, FungeModel model) throws InvalidTileException {
    // Consume two items off the stack.
    IntegerTile[] tiles = popIntegerTiles(pointer);
    // Subtract them.
    int result = performOperation(tiles[1].getInteger(), tiles[0].getInteger());
    // Push the result back.
    pointer.pushTile(new IntegerTile(result));
  }

  public IntegerTile[] popIntegerTiles(InstructionPointer pointer) throws InvalidTileException {
    Tile tile1 = pointer.popTile();
    Tile tile2 = pointer.popTile();
    if (tile1 instanceof IntegerTile && tile2 instanceof IntegerTile) {
      IntegerTile integerA = (IntegerTile) tile1;
      IntegerTile integerB = (IntegerTile) tile2;
      return new IntegerTile[] { integerA, integerB };
    } else {
      throw new InvalidTileException(
          String.format("Needed two IntegerTiles in order to execute %s", this));
    }
  }
}
