package com.pirooze.showtime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;


import java.util.ArrayList;

/**
 * Created by shahab saalami on 07/17/2016.
 * Email : Shahab.saalami@gmail.com
 * web :  pirooze.com
 */
public class LineRangeTimeView extends AppCompatImageView {

    private Paint linePaint;
    private Paint backgroundLinePaint;
    private float timeSplitterSize;
    private ArrayList<TimesModel> clocks = new ArrayList<>();
    public int backgroundLineColor = Color.parseColor("#dfdfdf");
    public int clockTextColor = Color.parseColor("#3f3f3f");
    public int lineColor = Color.parseColor("#1976D2");

    public LineRangeTimeView(Context context) {
        super(context);
        initialize(context);
    }

    public LineRangeTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);

    }

    public LineRangeTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);

    }


    /**
     * set background line color
     *
     * @param color A color value in the form 0xAARRGGBB
     */
    public void setBackgroundLineColor(int color) {
        backgroundLineColor = color;
    }

    /**
     * set text Color of helper times
     *
     * @param color A color value in the form 0xAARRGGBB.
     *              Do not pass a resource ID. To get a color value from a resource ID, call
     *              {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     */
    public void setTextClockColor(int color) {
        clockTextColor = color;
    }

    /**
     * set time line color
     *
     * @param color A color value in the form 0xAARRGGBB.
     *              Do not pass a resource ID. To get a color value from a resource ID, call
     *              {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     */
    public void setLineColor(int color) {
        lineColor = color;
    }


    private float textSize = 0;
    private float lineSize = 0;


    private void initialize(Context context) {
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, context.getResources().getDisplayMetrics());
        lineSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        paddingSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
        timeSplitterSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(lineSize);

        splitterPaint = new Paint();
        splitterPaint.setColor(Color.parseColor("#afafaf"));
        splitterPaint.setAntiAlias(true);
        splitterPaint.setStyle(Paint.Style.FILL);
        splitterPaint.setStrokeWidth(lineSize);


        backgroundLinePaint = new Paint();
        backgroundLinePaint.setColor(backgroundLineColor);
        backgroundLinePaint.setAntiAlias(true);
        backgroundLinePaint.setStyle(Paint.Style.FILL);
        backgroundLinePaint.setStrokeWidth(lineSize);

        textPaint = new Paint();
        textPaint.setColor(clockTextColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);

    }

    private Paint splitterPaint;
    private Paint textPaint;
    private Canvas myCanvas;

    private float paddingSize = 0;


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        myCanvas = canvas;

        float start = paddingSize;
        float end = getWidth() - start;
        float width = start - end;
        float spilet = Math.abs(width / 4);
        float height = getHeight();


        float paddingUpText = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, getResources().getDisplayMetrics());

        String[] clocks = {"0", "6", "12", "18", "24"};
        float[] distans = {start, textSize, textSize, textSize, textSize};
        float x = 0;
        for (int i = 0; i <= 4; i++) {

//            neveshtan saat ha
            myCanvas.save();
            myCanvas.drawText(clocks[i], (x + distans[i]), (height / 2) + paddingUpText + lineSize, textPaint);
            myCanvas.restore();


// moshakhas kardan har 6 saat
            myCanvas.save();
            myCanvas.drawLine(x + distans[i],                //start x
                    (height / 2) + lineSize,                                //start y
                    (x + distans[i]) + timeSplitterSize,                // end  x
                    (height / 2) + lineSize,                                //end y
                    splitterPaint);
            myCanvas.restore();
            x = x + (spilet);
        }

        // keshian khate pass zamine
        myCanvas.save();
        myCanvas.drawLine(start, height / 2, end, height / 2, backgroundLinePaint);
        myCanvas.restore();

        if (this.clocks != null) {
            if (this.clocks.size() != 0) {
                for (TimesModel model : this.clocks) {
                    int f_h = model.getFromTime() / 60;
                    int f_m = model.getFromTime() % 60;
                    int to_h = model.getToTime() / 60;
                    int to_m = model.getToTime() % 60;

                    float hourDistans = (spilet / 6);
                    float MinuteDistance = (hourDistans / 60);
                    start = paddingSize + (f_h * hourDistans) + (f_m * MinuteDistance);
                    end = paddingSize + (to_h * hourDistans) + (to_m * MinuteDistance);
                    myCanvas.save();
                    //keshidan saat ha roye nemodar
                    myCanvas.drawLine(Math.abs(start), height / 2, Math.abs(end), height / 2, linePaint);
                    myCanvas.restore();
                }
            }
//             else {
//                //draw 24 hour  -- poor kardan koole line
//                float hourDistans = (spilet / 6);
//                float MinuteDistance = (hourDistans / 60);
//                start = paddingSize + (0 * hourDistans) + (0 * MinuteDistance);
//                end = paddingSize + (24 * hourDistans) + (0 * MinuteDistance);
//                myCanvas.save();
//                myCanvas.drawLine(Math.abs(start), height / 2, Math.abs(end), height / 2, linePaint);
//                myCanvas.restore();
//            }
        }
//               else {
//            //draw 24 hour  -- poor kardan koole line
//            float hourDistans = (spilet / 6);
//            float MinuteDistance = (hourDistans / 60);
//            start = paddingSize + (0 * hourDistans) + (0 * MinuteDistance);
//            end = paddingSize + (24 * hourDistans) + (0 * MinuteDistance);
//            myCanvas.save();
//            myCanvas.drawLine(Math.abs(start), height / 2, Math.abs(end), height / 2, linePaint);
//            myCanvas.restore();
//        }
    }


//    float start = paddingSize;
//    float end;
//    float splitter;
//    public void setClocks() {
//
//        if (this.clocks != null) {
//            if (this.clocks.size() != 0) {
//                for (TimesModel model : this.clocks) {
//                    int f_h = model.getFromTime() / 60;
//                    int f_m = model.getFromTime() % 60;
//                    int to_h = model.getToTime() / 60;
//                    int to_m = model.getToTime() % 60;
//
//                    float hourDistans = (splitter / 6);
//                    float MinuteDistance = (hourDistans / 60);
//                    start = paddingSize + (f_h * hourDistans) + (f_m * MinuteDistance);
//                    end = paddingSize + (to_h * hourDistans) + (to_m * MinuteDistance);
//                    myCanvas.save();
//                    myCanvas.drawLine(Math.abs(start), getHeight() / 2, Math.abs(end), getHeight() / 2, linePaint);
//                    myCanvas.restore();
//                }
//            }
//        } else {
//            float hourDistans = (splitter / 6);
//            float MinuteDistance = (hourDistans / 60);
//            start = paddingSize + (0 * hourDistans) + (0 * MinuteDistance);
//            end = paddingSize + (24 * hourDistans) + (0 * MinuteDistance);
//            myCanvas.save();
//            myCanvas.drawLine(Math.abs(start), getHeight() / 2, Math.abs(end), getHeight() / 2, linePaint);
//            myCanvas.restore();
//        }
//    }

    /**
     * set range time with String formant
     * format is 12:00-13:58
     *
     * @param rangeTime time rang in text
     */
    public void addRangeTime(String rangeTime) {
        TimesModel timesModel = new TimesModel();
        if (rangeTime != null) {
            if (rangeTime.length() > 6) {
                String[] time = rangeTime.replaceAll(" ", "").split("-");

                String[] FromTimeSplitter = time[0].split(":");
                String[] ToTimeSplitter = time[1].split(":");

                timesModel.setFromTime((Integer.parseInt(FromTimeSplitter[0]) * 60) + Integer.parseInt(FromTimeSplitter[1]));

                timesModel.setToTime((Integer.parseInt(ToTimeSplitter[0]) * 60) + Integer.parseInt(ToTimeSplitter[1]));

                postInvalidate();
            }
        }
        this.clocks.add(timesModel);
    }

    /**
     * for set font for time helpe
     * 0  - 6  - 12 - 18 - 24
     *
     * @param typeface
     */
    public void setTypeFace(Typeface typeface) {
        textPaint.setTypeface(typeface);
        postInvalidate();
    }

    private int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }


    /**
     * its inner class for handle times
     */
    private class TimesModel {
        private int fromTime;
        private int toTime;
        private int id;

        TimesModel(int fromHour, int toHour) {
            this.fromTime = fromHour;
            this.toTime = toHour;
        }

        TimesModel() {
        }

        void setId(int id) {
            this.id = id;
        }

        int getId() {
            return id;
        }

        int getFromTime() {
            return fromTime;
        }

        int getToTime() {
            return toTime;
        }

        void setFromTime(int fromTime) {
            this.fromTime = fromTime;
        }

        void setToTime(int toTime) {
            this.toTime = toTime;
        }
    }
}
