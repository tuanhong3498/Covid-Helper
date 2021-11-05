package com.example.covidhelper.ui.dashboard;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidhelper.R;
import com.github.mikephil.charting.charts.BarChart;
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
        BarData data = getNewCases();
        configureChartNewCasesAppearance();

        data.setValueTextSize(12f);
        barChartNewCases.setData(data);
        barChartNewCases.invalidate();
    }

    private BarData getNewCases()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < 7; ++i)
        {
            float x = i;
            float y = 100 + i * 10;
            values.add(new BarEntry(x, y));
        }

        BarDataSet barDataSet = new BarDataSet(values, "New cases");

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        return new BarData(dataSets);
    }

    private void configureChartNewCasesAppearance()
    {
        barChartNewCases.getDescription().setEnabled(false);
        barChartNewCases.setDrawValueAboveBar(false);

        String[] DAYS = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

        XAxis xAxis = barChartNewCases.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value)
            {
                return DAYS[(int) value];
            }
        });

        YAxis yAxisLeft = barChartNewCases.getAxisLeft();
        yAxisLeft.setGranularity(10f);
        yAxisLeft.setAxisMinimum(0);
    }

}