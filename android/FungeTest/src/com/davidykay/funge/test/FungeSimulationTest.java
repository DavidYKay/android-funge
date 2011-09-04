package com.davidykay.funge.test;

import junit.framework.TestCase;

import com.davidykay.funge.interpreter.FungePlane;
import com.davidykay.funge.interpreter.tiles.IntegerTile;
import com.davidykay.funge.interpreter.tiles.Tile;

public class FungeSimulationTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testInit() {
    int width = 4;
    int height = 10;
    FungePlane sim = new FungePlane(width, height);
    assertTrue(String.format("Expected: %d, Observed: %d", 
                             sim.getWidth(), width),
               sim.getWidth() == width
               );
    assertTrue(sim.getHeight() == height);
    
    Tile sampleTile = new IntegerTile(5);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        // Make sure it starts empty.
        Tile emptyTile = sim.tileAtLocation(x, y);
        assertTrue(emptyTile == null);
        // Set the tile.
        sim.setTileAtLocation(sampleTile, x, y);
        // Retrieve it.
        Tile retrievedTile = sim.tileAtLocation(x, y);
        // Make sure it's the same.
        assertTrue(String.format("Expected: %s, Observed: %s", 
                                 sampleTile,
                                 retrievedTile),
                   sampleTile.equals(retrievedTile)
                  );
        
      }
    }
  }
}
