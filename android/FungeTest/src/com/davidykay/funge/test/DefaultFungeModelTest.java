package com.davidykay.funge.test;

import junit.framework.TestCase;
import android.graphics.Point;

import com.davidykay.funge.interpreter.DefaultFungeModel;
import com.davidykay.funge.interpreter.FungeModel;
import com.davidykay.funge.interpreter.InstructionPointer;
import com.davidykay.funge.interpreter.tiles.ArithmeticTile;
import com.davidykay.funge.interpreter.tiles.CodeTile;
import com.davidykay.funge.interpreter.tiles.IntegerTile;
import com.davidykay.funge.interpreter.tiles.Tile;
import com.davidykay.funge.interpreter.tiles.ValueTile;

public class DefaultFungeModelTest extends TestCase {
  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testArithmetic() {
    // Prep the model.
    FungeModel model = new DefaultFungeModel(5, 7);
    IntegerTile sampleTile = new IntegerTile(5);
    ArithmeticTile addTile = ArithmeticTile.ArithmeticTileFactory.tileFromSymbol('+');
    model.setTileAtLocation(sampleTile, 0, 1);
    model.setTileAtLocation(sampleTile, 0, 2);
    model.setTileAtLocation(addTile, 0, 3);

    assertTrue(addTile instanceof CodeTile);
    assertTrue(sampleTile instanceof ValueTile);
    
    InstructionPointer pointer = model.getPointer();
    pointer.setHeading(new Point(0, 1));

    // Check our assumptions.
    assertTrue(pointer.getPosition().equals (new Point(0, 0)));

    // Advance to next step.
    model.advance();
    // The pointer should now be one slot down from before.
    assertTrue(pointer.getPosition().equals (new Point(0, 1)));
    // We should have consumed the tile that was there?
    //assertTrue(model.tileAtPosition(pointer.getPosition()) == null);
    
    model.advance();
    assertTrue(pointer.getPosition().equals (new Point(0, 2)));
    // Now that we've run over everything, we should have executed 5 5 +.
    
    model.advance();
    assertTrue(pointer.getPosition().equals (new Point(0, 3)));

    Tile finalTile = pointer.popTile();
    assertTrue(finalTile instanceof IntegerTile);

    int observed = ((IntegerTile) finalTile).getInteger();
    int sampleInt = (sampleTile.getInteger());
    int expected = 2 * sampleInt;
    assertTrue(
        String.format("Expected: %d, observed: %d", expected, observed),
        expected == observed
        );
  }
}
