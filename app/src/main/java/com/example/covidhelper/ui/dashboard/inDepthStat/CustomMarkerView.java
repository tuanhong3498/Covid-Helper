package com.example.covidhelper.ui.dashboard.inDepthStat;


import android.content.Context;
import android.content.res.Configuration;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomMarkerView extends MarkerView
{
    private TextView textViewPopUp;
    private TextView textViewDate;

    private int startingDate;
    private float[][] datasets;
    private boolean[] useFloat; // to specify whether to display the data in decimal point
    private String[] labels;


    public CustomMarkerView(Context context, int layoutResource, int startingDate, String[] labels, float[][] datasets, boolean[] useFloat)
    {
        super(context, layoutResource);

        textViewPopUp = findViewById(R.id.label_text);
        textViewDate = findViewById(R.id.label_date);

        this.startingDate = startingDate;
        this.labels = labels;
        this.datasets = datasets;
        this.useFloat = useFloat;

        if (labels.length != datasets.length)
        {
            System.out.println("The lengths of labels and datasets do not match!");
        }
        if (labels.length != useFloat.length)
        {
            System.out.println("The 'useFloat' parameter may not be specified for some data");
        }
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight)
    {
        int entryIndex = (int)e.getX();

        textViewDate.setText(getDate(entryIndex));

        StringBuffer popUpContent = new StringBuffer();
        for(int i = 0; i < labels.length; ++i)
        {
            if (i > 0)
                popUpContent.append("\n");
            popUpContent.append(labels[i]);
            popUpContent.append(": ");
            if (!useFloat[i])
                popUpContent.append(Math.round(datasets[i][entryIndex]));
            else
            {
                popUpContent.append(datasets[i][entryIndex]);
                popUpContent.append("%");
            }
        }

        textViewPopUp.setText(popUpContent);

        super.refreshContent(e, highlight);
    }

    private String getDate(int addDay)
    {
        long UNIX_DAY = 86400;

        Date date = new Date((addDay * UNIX_DAY + startingDate)*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        return sdf.format(date);
    }


    @Override
    public void draw(Canvas canvas, float posX, float posY)
    {
        // Check marker position and update offsets.
        int w = getWidth();
        // offset to add horizontal space between the marker and the vertical highlight line
        float xOffset = 30;

        if(posX > (canvas.getWidth()/2f))
        {
            // if at the right half of the chart
            // flip the marker to the right of the vertical highlight line
            posX -= (w + xOffset);
        }
        else
            posX += xOffset;

        super.draw(canvas, posX, posY - getHeight()/2f);
    }
}
