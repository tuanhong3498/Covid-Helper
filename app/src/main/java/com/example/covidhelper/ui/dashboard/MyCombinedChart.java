package com.example.covidhelper.ui.dashboard;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.highlight.Highlight;

public class MyCombinedChart extends CombinedChart
{
    public MyCombinedChart(Context context)
    {
        super(context);
    }

    public MyCombinedChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyCombinedChart(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
//
//    @Override
//    public Highlight getHighlightByTouchPoint(float x, float y)
//    {
//        Highlight h = getHighlighter().getHighlight(x, y);
//        if (h == null || !isHighlightFullBarEnabled()) return h;
//
//        // if HighlightFullBar is enabled
//        Highlight highlight = new Highlight(h.getX(), h.getY(), h.getXPx(), h.getYPx(), h.getDataSetIndex(), -1, h.getAxis());
//        System.out.println("In etHighlightByTouchPoint(): getDataIndex=" + h.getDataIndex());
//        highlight.setDataIndex(h.getDataIndex());
//        return highlight;
//    }
}
