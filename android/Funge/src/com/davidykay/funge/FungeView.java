package com.davidykay.funge;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.davidykay.funge.interpreter.FungeModel;
import com.davidykay.funge.interpreter.InstructionPointer;
import com.davidykay.funge.interpreter.tiles.ArithmeticTile;
import com.davidykay.funge.interpreter.tiles.IntegerTile;
import com.davidykay.funge.interpreter.tiles.Tile;

/**
 * View representing the Befunge game board.
 */
public class FungeView extends View {

  private static final int MARGIN = 4;
  private static final String TAG = "FungeView";
  
  private static final int CHECKER_A    = 0xAABBCCDD;
  private static final int CHECKER_B    = 0xDDCCBBAA;

  // UI
  private Bitmap mCellBackground;
  private Bitmap mExampleCell;
  //private Drawable mDrawableBg;
  private Paint mBmpPaint;
  private Paint mGridPaint;
  private Paint mLinePaint; 
  private Paint mTilePaint; 
  private Paint mTextPaint; 
  private Paint mPointerPaint; 
  
  // Model
  private FungeModel mModel; 

  public FungeView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mCellBackground = getResBitmap(R.drawable.lib_cross);
    //mDrawableBg = getResources().getDrawable(R.drawable.lib_bg);

    if (mCellBackground != null) {
      mSrcRect.set(0, 0, mCellBackground.getWidth() - 1, mCellBackground.getHeight() - 1);
    }

    mTilePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTilePaint.setColor(Color.BLACK);

    mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPointerPaint.setColor(Color.RED);
    mPointerPaint.setTextSize(30.0f);
    
    mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTextPaint.setColor(Color.BLUE);
    mTextPaint.setTextSize(40.0f);

    mBmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mGridPaint.setColor(Color.DKGRAY);

    mLinePaint = new Paint();
    mLinePaint.setColor(Color.WHITE);
    mLinePaint.setStrokeWidth(5);
    mLinePaint.setStyle(Style.STROKE);
  }
  
  private final Rect mSrcRect = new Rect();
  private final Rect mDstRect = new Rect();
  private final Rect mGridRect = new Rect();

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    // Render the board.

    int columns = mModel.getWidth();
    int rows = mModel.getHeight();

    int cellWidth = getWidth() / columns;
    int cellHeight = getHeight() / rows;
    mGridRect.set(0, 0, cellWidth, cellHeight);
    Log.v(TAG, String.format("Cell Dimensions: (%d, %d)", 
                             cellWidth,
                             cellHeight
                             ));

    int xPixels = 0;
    int yPixels = 0;
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        mGridRect.offsetTo(xPixels, yPixels);
        if (mModel.tileAtLocation(x, y) != null) {
          Tile tile = mModel.tileAtLocation(x, y);
          if (tile instanceof IntegerTile) {
            // If integer, draw number.
            String intString = Integer.toString(((IntegerTile) tile).getInteger());
            drawString(intString, mGridRect, mTextPaint, canvas);
          } else if (tile instanceof ArithmeticTile) {
            // If integer, draw number.
            String symbolString = Character.toString(((ArithmeticTile) tile).getSymbol());
            drawString(symbolString, mGridRect, mTextPaint, canvas);
          } else {
            // If other type, draw black.
            canvas.drawRect(mGridRect, mTilePaint);
          }
          Log.v(TAG, String.format("Drawing tile at: (%d, %d)", 
                                   x, y
                                  ));
        } else {
          // If empty, draw a checker.
          if (y % 2 == 0) {
            if (x % 2 == 0) {
              mGridPaint.setColor(CHECKER_A);
            } else {
              mGridPaint.setColor(CHECKER_B);
            }
          } else {
            if (x % 2 == 0) {
              mGridPaint.setColor(CHECKER_B);
            } else {
              mGridPaint.setColor(CHECKER_A);
            }
          }
          // Draw the box.
          canvas.drawRect(mGridRect, mGridPaint);
          Log.v(TAG, String.format("Drawing checkers at: (%d, %d, %d, %d)", 
                                   mGridRect.left,
                                   mGridRect.top,
                                   mGridRect.right,
                                   mGridRect.bottom
                                  ));
        }
        
        // Draw the outline.
        canvas.drawLine( mGridRect.left, mGridRect.top, 
                        mGridRect.right, mGridRect.top, mLinePaint);
        canvas.drawLine( mGridRect.right, mGridRect.top, 
                        mGridRect.right, mGridRect.bottom, mLinePaint);
        canvas.drawLine( mGridRect.right, mGridRect.bottom, 
                         mGridRect.left, mGridRect.bottom, mLinePaint); 
        canvas.drawLine( mGridRect.left, mGridRect.bottom, 
                        mGridRect.left, mGridRect.top, mLinePaint);

        //mDstRect.offsetTo(MARGIN + x, MARGIN + y);
        //canvas.drawBitmap(mCellBackground, mSrcRect, mDstRect, mBmpPaint);

        //canvas.drawLine(x7    , y7 + k, x7 + s3 - 1, y7 + k     , mLinePaint);
        //canvas.drawLine(x7 + k, y7    , x7 + k     , y7 + s3 - 1, mLinePaint);
        xPixels += cellWidth;
      }
      xPixels = 0;
      yPixels += cellHeight;
    }

    // Render the instruction pointer.
    InstructionPointer pointer = mModel.getPointer();
    Point pos = pointer.getPosition();
    drawString("P", 
               new Rect(
                   //pos.x - cellWidth / 2, pos.y - cellHeight / 2, 
                   //pos.x + cellWidth / 2, pos.y + cellHeight / 2
                   pos.x, pos.y, 
                   pos.x + cellWidth, pos.y + cellHeight 
                 ),
               mPointerPaint,
               canvas
               );
  }

  private void drawString(String string, Rect rect, Paint paint, Canvas canvas) {
    Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
    float textWidth = mTextPaint.measureText(string);
    Rect bounds = new Rect();
    paint.getTextBounds(string, 0, string.length(), bounds);
    //float textHeight = fontMe
    float[] coordinates = new float[] { 
      (rect.left + rect.right) / 2 - (textWidth / 2),
      //(rect.top + rect.bottom) / 2
      (rect.top + rect.bottom) / 2 - (bounds.top - bounds.bottom) / 2
    };
    canvas.drawPosText(
        string,
        coordinates,
        paint);
    Log.v(TAG, String.format("Drawing %s at: (%f, %f)", 
                             string,
                             coordinates[0],
                             coordinates[1]
                            ));

  }
  
  //@Override
  //protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
  //  // Keep the view squared
  //  int w = MeasureSpec.getSize(widthMeasureSpec);
  //  int h = MeasureSpec.getSize(heightMeasureSpec);
  //  int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
  //  setMeasuredDimension(d, d);
  //}

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    //int sx = (w - 2 * MARGIN) / 3;
    //int sy = (h - 2 * MARGIN) / 3;

    //int size = sx < sy ? sx : sy;

    //mSxy = size;
    //mOffetX = (w - 3 * size) / 2;
    //mOffetY = (h - 3 * size) / 2;

    // Set the destination rect to the "origin" location.
    //mDstRect.set(MARGIN, MARGIN, size - MARGIN, size - MARGIN);
    //mDstRect.set(MARGIN, MARGIN, w - MARGIN, h - MARGIN);
  }

  ////////////////////////////////////////
  // Utility Functions
  ////////////////////////////////////////
  
  private Bitmap getResBitmap(int bmpResId) {
    Options opts = new Options();
    opts.inDither = false;

    Resources res = getResources();
    Bitmap bmp = BitmapFactory.decodeResource(res, bmpResId, opts);

    if (bmp == null && isInEditMode()) {
      // BitmapFactory.decodeResource doesn't work from the rendering
      // library in Eclipse's Graphical Layout Editor. Use this workaround instead.

      Drawable d = res.getDrawable(bmpResId);
      int w = d.getIntrinsicWidth();
      int h = d.getIntrinsicHeight();
      bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
      Canvas c = new Canvas(bmp);
      d.setBounds(0, 0, w - 1, h - 1);
      d.draw(c);
    }

    return bmp;
  }

  public void setModel(FungeModel model) {
    // TODO Auto-generated method stub
    mModel = model;
  }
}
