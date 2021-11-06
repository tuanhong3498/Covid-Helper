package com.example.covidhelper.ui.dashboard;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InDeptStatFragment extends Fragment
{

    private BarChart barChartNewCases;

    private InDeptStatViewModel mViewModel;

    public static InDeptStatFragment newInstance()
    {
        return new InDeptStatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_in_dept_stat, container, false);

        barChartNewCases = root.findViewById(R.id.in_depth_stat_chart_new_cases);

        initializeChartNewCases();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InDeptStatViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initializeChartNewCases()
    {
        BarDataSet barDataSet = getNewCases();
        configureChartNewCasesAppearance();
        customBarAppearance(barDataSet);

        BarData data = new BarData(barDataSet);
        barChartNewCases.setData(data);
        barChartNewCases.invalidate();
    }

    private BarDataSet getNewCases()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < 7; ++i)
        {
            float x = i;
            float y = 100 + i * 10;
            values.add(new BarEntry(x, y));
        }

        return new BarDataSet(values, "New cases");
    }

    private void customBarAppearance(BarDataSet barDataSet)
    {
        // customize the color of the bar
        barDataSet.setColor(ContextCompat.getColor(this.requireContext(), R.color.blue_light));

        // hide the value on the bar
        barDataSet.setDrawValues(false);

        // set the text size
        // note: it is redundant as the value on the bar was hidden
        barDataSet.setValueTextSize(12f);
    }

    private void configureChartNewCasesAppearance()
    {
        barChartNewCases.getDescription().setEnabled(false);
        barChartNewCases.setDrawValueAboveBar(false);

        // setting animation for y-axis, the bar will pop up from 0
        barChartNewCases.animateY(1000);
        // animation for x-axis, so the bar will pop up separately
        barChartNewCases.animateX(1000);

        String[] DAYS = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

        XAxis xAxis = barChartNewCases.getXAxis();
        // move the xAxis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        // hide vertical grid line
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value)
            {
                return DAYS[(int) value];
            }
        });


        YAxis yAxisLeft = barChartNewCases.getAxisLeft();
        // hide horizontal grid line
        yAxisLeft.setGridColor(ContextCompat.getColor(this.requireContext(), R.color.grey_light));

        YAxis yAxisRight = barChartNewCases.getAxisRight();
        yAxisRight.setDrawAxisLine(false);

        Legend legend = barChartNewCases.getLegend();
        legend.setTextSize(11f);
        //set the alignment of the legend
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

    }

}