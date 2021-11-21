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

    public CustomMarkerView(Context context, int layoutResource)
    {
        super(context, layoutResource);

        textViewPopUp = findViewById(R.id.label_text);
        uiScreenWidth = getResources().getDisplayMetrics().widthPixels;
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
            mOffset = new MPPointF(-(getWidth()/2f), -getHeight()-10);
        }

        return mOffset;
    }

//    @Override
//    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY)
//    {
//        System.out.println("getOffsetForDrawingAtPoint() called");
//        System.out.println("posX: " + posX);
//        System.out.println("posY: " + posY);
//        System.out.println("getWidth(): " + getWidth());
//        System.out.println("getHeight(): " + getHeight());
//        if (mOffset == null)
//            mOffset = new MPPointF(-getWidth(), -Math.abs(posY-posX));
//        return mOffset;
//    }
    private int uiScreenWidth;

//    @Override
//    public void draw(Canvas canvas, float posX, float posY)
//    {
//        // Check marker position and update offsets.
//        int w = getWidth();
//        if((uiScreenWidth-posX-w) < w) {
//            posX -= w;
//        }
//
//        // translate to the correct position and draw
//        canvas.translate(posX, posY);
//        draw(canvas);
//        canvas.translate(-posX, -posY);
//    }
}
