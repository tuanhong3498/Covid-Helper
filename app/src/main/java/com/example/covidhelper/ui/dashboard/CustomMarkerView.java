package com.example.covidhelper.ui.dashboard;


import android.content.Context;
import android.widget.TextView;

import com.example.covidhelper.R;

import com.github.mikephil.charting.components.MarkerView;
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
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        textViewPopUp.setText(String.valueOf((int)e.getY()));

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
}
