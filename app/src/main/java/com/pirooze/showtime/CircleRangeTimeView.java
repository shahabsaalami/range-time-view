package com.pirooze.showtime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by shahab saalami on 07/17/2016.
 * Email : Shahab.saalami@gmail.com
 * web :  pirooze.com
 */
public class CircleRangeTimeView extends AppCompatImageView {

    private Paint paintAm;
    private Paint backgroundCircle;
    private Canvas myCanvas;

    private float fromHour = 0;
    private float toHour = 0;
    private float timeDifference = 0;
    private float toMinute = 0;
    private float fromMinute = 0;

    public CircleRangeTimeView(Context context) {
        super(context);
        initialize();
    }

    public CircleRangeTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();

    }

    public CircleRangeTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();

    }

    /**
     * set range time  , with hoours
     *
     * @param fromHour its start Time  , for example : 15
     * @param toHour   its End time , for example : 22
     */
    public void setRangeTime(int fromHour, int toHour) {
        this.fromHour = fromHour;
        this.toHour = toHour;
        timeDifference = toHour - fromHour;
        postInvalidate();

    }

    /**
     * set range time with hour and minute
     *
     * @param fromHour from hour
     * @param fromMinute from minute
     * @param toHour to hour
     * @param toMinute to minute
     */
    public void setRangeTime(float fromHour, float fromMinute, float toHour, float toMinute) {
        this.fromHour = fromHour;
        this.toHour = toHour;
        timeDifference = toHour - fromHour;
        this.fromMinute = fromMinute;
        this.toMinute = toMinute;
        postInvalidate();

    }

    /**
     * set range time with String formant
     * format should be 12:00-13:58
     * @param rangeTime time to will be show
     */
    public void setRangeTime(String rangeTime)  {
        //sample   12:00-13:58
        if (rangeTime != null) {
            if (rangeTime.length() > 6) {
                String[] time = rangeTime.replaceAll(" ", "").split("-");

                String[] F = time[0].split(":");
                fromHour = Integer.parseInt(F[0]);
                fromMinute = Integer.parseInt(F[1]);

                String[] T = time[1].split(":");
                toHour = Integer.parseInt(T[0]);
                toMinute = Integer.parseInt(T[1]);

                timeDifference = toHour - fromHour;
                postInvalidate();
            }
        }
    }

    //Circle color background
    public int bacgroundColor = Color.parseColor("#dfdfdf");

    public int circleColor = Color.parseColor("#1976D2");

    /**
     *  Circle color background
     * @param color A color value in the form 0xAARRGGBB.
     *              Do not pass a resource ID. To get a color value from a resource ID, call
     *              {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.     */
    public void setBackgroundCircleColor(int color) {
        bacgroundColor = color;
    }

    /**
     * range Color
     * @param color A color value in the form 0xAARRGGBB.
     *              Do not pass a resource ID. To get a color value from a resource ID, call
     *              {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.     */
    public void setCircleColor(int color) {
        circleColor = color;
    }

    private void initialize() {
        paintAm = new Paint();
        paintAm.setColor(circleColor);
        paintAm.setAntiAlias(true);
        paintAm.setStyle(Paint.Style.FILL);

        backgroundCircle = new Paint();
        backgroundCircle.setColor(bacgroundColor);
        backgroundCircle.setAntiAlias(true);
        backgroundCircle.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.myCanvas = canvas;
        RectF rectF = new RectF();
        rectF.left = 0;
        rectF.right = getWidth();
        rectF.top = 0;
        rectF.bottom = getHeight();

        saveCanvas();
        myCanvas.drawArc(rectF, 0, 360, true, backgroundCircle);
        myCanvas.restore();

//        Log.d("LOG", "onDraw: Time is =" + fromHour + ":" + fromMinute + " - " + toHour + ":" + toMinute);
        if (fromHour < 12 && toHour > 12) {
//            Log.d("LOG", "onDraw: I");
            saveCanvas();
            myCanvas.drawArc(rectF, (fromHour) * 30 + (fromMinute / 2), (12 - fromHour) * 30 - (fromMinute / 2), true, paintAm);
            myCanvas.restore();

            float mabaghi = toHour - 12;
            saveCanvas();
            myCanvas.drawArc(rectF, 0, (mabaghi) * 30 + (toMinute / 2), true, paintAm);
            myCanvas.restore();

        } else if (fromHour < 12) {
//            Log.d("LOG", "onDraw: II ");
            saveCanvas();
            float from = (fromHour) * 30 + (fromMinute / 2);
            float to = (timeDifference) * 30 + (toMinute / 2) - (fromMinute / 2);
            myCanvas.drawArc(rectF, from, to, true, paintAm);
            myCanvas.restore();
        } else if (fromHour > 12 && toHour < 12) {
//            Log.d("LOG", "onDraw: III");
            saveCanvas();
            fromHour = fromHour - 12;
            myCanvas.drawArc(rectF, (fromHour) * 30 + (fromMinute / 2), (12 - fromHour) * 30 - (fromMinute / 2), true, paintAm);
            myCanvas.restore();

            saveCanvas();
            myCanvas.drawArc(rectF, 0, (toHour) * 30 + (toMinute / 2), true, paintAm);
            myCanvas.restore();
        } else {
//            Log.d("LOG", "onDraw: IV");
            saveCanvas();
            fromHour = fromHour - 12;
            toHour = toHour - 12;
            float from = (fromHour) * 30 + (fromMinute / 2);
            float to = (timeDifference) * 30 + (toMinute / 2) - (fromMinute / 2);
            myCanvas.drawArc(rectF, from, to, true, paintAm);
            myCanvas.restore();

        }
//        myCanvas.save();
//        myCanvas.rotate(-90, getWidth() / 2, getHeight() / 2);
//        myCanvas.drawArc(rectF, (13-12) * 30, (18-13) * 30, true, paintPm);
//        myCanvas.restore();

    }

    private void saveCanvas() {
        myCanvas.save();
        myCanvas.rotate(-90, getWidth() / 2, getHeight() / 2);
    }

}