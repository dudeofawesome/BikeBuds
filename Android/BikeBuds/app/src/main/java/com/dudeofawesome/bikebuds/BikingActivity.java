package com.dudeofawesome.bikebuds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


public class BikingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biking);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyView extends View {
        private Paint paint = new Paint();
        private int width = 0;
        private int height = 0;
        private Rect freeAllocate = new Rect();

        long timeOfLastFrame = 0;

        public MyView(Context context) {
            super(context);

            paint.setStyle(Paint.Style.FILL);
            width = getWidth();
            height = getHeight();
            timeOfLastFrame = System.currentTimeMillis();

            gameLoop();
        }

        private int selectedApp = -1;
        private long timeSelected = -1;
        private final long selectionTime = 200;

        private void gameLoop () {
//            move();
//            updateTweens();
            if (width == 0) {
                width = getWidth();
                height = getHeight();
            }

            // cause redraw
            invalidate();
        }

        Display dis = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Bitmap lEyeBit = Bitmap.createBitmap(dis.getWidth() / 2, dis.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap rEyeBit = Bitmap.createBitmap(dis.getWidth() / 2, dis.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas lEye = new Canvas(lEyeBit);
        Canvas rEye = new Canvas(rEyeBit);

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            paint.setColor(Color.BLACK);
            canvas.drawPaint(paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            rEye.drawCircle(width / 2, height / 2, 50, paint);
            paint.setStyle(Paint.Style.FILL);

            gameLoop();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return false;
        }
    }

}
