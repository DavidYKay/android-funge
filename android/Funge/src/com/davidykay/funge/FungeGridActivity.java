package com.davidykay.funge;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.davidykay.funge.interpreter.DefaultFungeModel;
import com.davidykay.funge.interpreter.FungeModel;
import com.davidykay.funge.interpreter.tiles.ArithmeticTile;
import com.davidykay.funge.interpreter.tiles.IntegerTile;
import com.davidykay.funge.interpreter.tiles.Tile;

/**
 * Shows the funge grid. This is the signature activity in our application.
 * @author dk
 *
 */
public class FungeGridActivity extends Activity {

  private FungeModel mModel;
  private FungeView mView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
    setContentView(R.layout.funge_grid);

    mView = (FungeView) findViewById(R.id.game_view);
    mModel = new DefaultFungeModel(5, 7);
    Tile sampleTile = new IntegerTile(5);
    Tile addTile = ArithmeticTile.ArithmeticTileFactory.tileFromSymbol('+');
    mModel.setTileAtLocation(sampleTile, 0, 1);
    mModel.setTileAtLocation(sampleTile, 0, 2);
    mModel.setTileAtLocation(addTile, 0, 3);
    mView.setModel(mModel);
    mModel.getPointer().setHeading(new Point(0, 1));
    
    final Button advanceButton = (Button) findViewById(R.id.step);
    advanceButton.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View v) {
            // Step to the next part of the simulation.
            mModel.advance();
          }
        });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // TODO Auto-generated method stub
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // TODO Auto-generated method stub
    return super.onOptionsItemSelected(item);
  }

}
