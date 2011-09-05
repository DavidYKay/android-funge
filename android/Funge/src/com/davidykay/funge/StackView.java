package com.davidykay.funge;

import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.davidykay.funge.interpreter.FungeModelListener;
import com.davidykay.funge.interpreter.InstructionPointer;
import com.davidykay.funge.interpreter.tiles.Tile;

public class StackView extends View implements FungeModelListener {
  
  private static final int TILE_MARGIN = 4;
  private static final int MAX_DISPLAY_TILES = 8;

  private InstructionPointer mPointer;
  private Paint mRectPaint; 
  private Paint mTilePaint; 
  private Paint mMarginPaint; 

  public StackView(Context context, AttributeSet attrs) {
    super(context, attrs);

    mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mRectPaint.setColor(Color.MAGENTA);

    mTilePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTilePaint.setColor(Color.GREEN);
    
    mMarginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mMarginPaint.setColor(Color.BLACK);
  }
  
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    int width  = getWidth();
    int height = getHeight(); 

    // Draw a black background
    Rect boundsRect = new Rect(
            0,
            0,
            width,
            height);
    canvas.drawRect(boundsRect, mMarginPaint);

    // Draw magenta walls.
    canvas.drawLine( boundsRect.left, boundsRect.top, 
                     boundsRect.right, boundsRect.top, mRectPaint);
    canvas.drawLine( boundsRect.right, boundsRect.top, 
                     boundsRect.right, boundsRect.bottom, mRectPaint);
    canvas.drawLine( boundsRect.right, boundsRect.bottom, 
                     boundsRect.left, boundsRect.bottom, mRectPaint); 
    canvas.drawLine( boundsRect.left, boundsRect.bottom, 
                     boundsRect.left, boundsRect.top, mRectPaint);

    // Render the contents of the instruction pointer.
    List<Tile> tiles = getPointer().allTiles();
    int size = tiles.size();
    if (size > 0) {
      //float temp = (width + TILE_MARGIN) / size;
      //int tileWidth = (int) temp;

      float temp = (width + TILE_MARGIN) / MAX_DISPLAY_TILES;
      int tileWidth = (int) temp;

      Rect tileRect = new Rect(
          0,
          0,
          tileWidth,
          height);
      for (Tile tile : tiles) {
        // Paint the tile in the given rect.
        canvas.drawRect(tileRect, mTilePaint);
        //canvas.drawRect(marginRect, mMarginPaint);

        // Slide to the next position.
        tileRect.offset(tileWidth + TILE_MARGIN, 0);
      }
    }
  }

  public void setPointer(InstructionPointer mPointer) {
    this.mPointer = mPointer;
  }

  public InstructionPointer getPointer() {
    return mPointer;
  }
  
  /**
   * The model just updated!
   */
  public void update(Observable observable, Object data) {
    // Redraw using new data.
    invalidate();
  }
}
