package com.dudeofawesome.bikebuds;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by dudeofawesome on 2/21/15.
 */
public class RidePainter extends View {
    private Paint paint = new Paint();
    private int width = 0;
    private int height = 0;
    private RectF freeAllocate = new RectF();

    public static float deltaSpeed = 0;
    public static boolean leftBlinkerOn = true;
    public static boolean rightBlinkerOn = true;
    public static ArrayList<PartyMember> partyMembers = new ArrayList<PartyMember>();

    private Path roadPath = new Path();
    private Path leftBlinker = new Path();
    private Path rightBlinker = new Path();

    public RidePainter (Context context) {
        super(context);
        init();
    }

    public RidePainter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RidePainter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init () {
        width = getWidth();
        height = getHeight();

        loop();
    }

    private void loop () {
        width = getWidth();
        height = getHeight();

        roadPath.reset();
        roadPath.moveTo(width / 2 - 50, 0);
        roadPath.lineTo(width / 2 + 50, 0);
        roadPath.lineTo(width / 2 + 200, height);
        roadPath.lineTo(width / 2 - 200, height);
        roadPath.close();

        leftBlinker.reset();
        leftBlinker.moveTo(width / 2 - 75, height / 2 - 30);
        leftBlinker.lineTo(width / 2 - 75, height / 2 + 30);
        leftBlinker.lineTo(width / 2 - 125, height / 2);
        leftBlinker.close();

        rightBlinker.reset();
        rightBlinker.moveTo(width / 2 + 75, height / 2 - 30);
        rightBlinker.lineTo(width / 2 + 75, height / 2 + 30);
        rightBlinker.lineTo(width / 2 + 125, height / 2);
        rightBlinker.close();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);

        paint.setAntiAlias(true);

        paint.setColor(Color.rgb(36, 96, 104));
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(null);
        canvas.drawPath(roadPath, paint);

        // Draw other party members
        for (int i = 0; i < partyMembers.size(); i++) {
            paint.setColor(Color.rgb(200, 200, 200));
            paint.setColor(partyMembers.get(i).color);
            float heightSkew = 0.000723f * partyMembers.get(i).position.y + 0.342767f;
            float widthScale = 0.000943f * partyMembers.get(i).position.y + 0.690566f;
//            float widthScale = partyMembers.get(i).y / (height + 1) + 0.8f;
            freeAllocate.set(partyMembers.get(i).position.x - 30 * widthScale, partyMembers.get(i).position.y - 30 * heightSkew, partyMembers.get(i).position.x + 30 * widthScale, partyMembers.get(i).position.y + 30 * heightSkew);
            canvas.drawOval(freeAllocate, paint);
        }

        // Draw you
        paint.setColor(Color.rgb(255, 255, 255));
        float heightSkew = 0.000723f * width / 2 + 0.342767f;
        float widthScale = 0.000943f * height / 2 + 0.690566f;
        freeAllocate.set(width / 2 - 30 * widthScale, height / 2 - 30 * heightSkew, width / 2 + 30 * widthScale, height / 2 + 30 * heightSkew);
        canvas.drawOval(freeAllocate, paint);

        // Draw stats
        if (deltaSpeed > 0)
            paint.setColor(Color.GREEN);
        else if (deltaSpeed < 0)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(150);
        canvas.drawText("20 mph", width / 2, height / 2 + 200, paint);

        paint.setColor(Color.rgb(255, 176, 0));
        if (leftBlinkerOn)
            canvas.drawPath(leftBlinker, paint);
        if (rightBlinkerOn)
            canvas.drawPath(rightBlinker, paint);

        loop();
    }
}
