package com.example.covidhelper.ui.dashboard;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.TextView;

import com.example.covidhelper.R;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomMarkerView extends MarkerView
{
    private TextView textViewPopUp;
    private TextView textViewDate;

    private int[] date;
    private float[][] datasets;
    private String[] labels;


    private int uiScreenWidth;

    public CustomMarkerView(Context context, int layoutResource)
    {
        super(context, layoutResource);

        textViewPopUp = findViewById(R.id.label_text);
        textViewDate = findViewById(R.id.label_date);

        uiScreenWidth = getResources().getDisplayMetrics().widthPixels;
    }

    public CustomMarkerView(Context context, int layoutResource, int[] date, String[] labels, float[][] datasets)
    {
        this(context, layoutResource);

        this.date = date;
        this.labels = labels;
        this.datasets = datasets;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        textViewPopUp.setText(String.valueOf((int)e.getX()));
        // TODO:
        //  pass an array to the CustomMarkerView
        //  Then read the y values from the array by using the index of x
        //  e.g. dataToDisplay[e.getX()]
//        textViewPopUp.setText(String.valueOf((int)e.getY()));

        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset()
    {
        if(mOffset == null)
        {
            mOffset = new MPPointF(-(getWidth()/2f), -(getHeight()/2f));
        }

        return mOffset;
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY)
    {
        // Check marker position and update offsets.
        int w = getWidth();
        float xOffset = 30;
        if((uiScreenWidth-posX-w) < w)
        {
            posX -= (w + xOffset);
        }
        else
        {
            posX += xOffset;
        }

        // translate to the correct position and draw
        canvas.translate(posX, getHeight()/2f);
        draw(canvas);
        canvas.translate(-posX, -getHeight()/2f);
    }
}
