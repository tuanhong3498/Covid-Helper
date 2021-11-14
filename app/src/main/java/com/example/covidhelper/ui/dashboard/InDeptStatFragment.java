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
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InDeptStatFragment extends Fragment
{

    private CombinedChart barChartNewCases;
    private BarChart barChartCasePerState;

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
        barChartCasePerState = root.findViewById(R.id.in_depth_stat_chart_case_per_state);

        initializeChartNewCases(barChartCasePerState);
        configureCombinedChartAppearance(barChartNewCases);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InDeptStatViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initializeChartNewCases(BarChart barChart)
    {
        BarDataSet barDataSet = getVaccinationData();
        configureChartNewCasesAppearance(barChart);
        customBarAppearance(barDataSet);

        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.invalidate();
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

    private BarDataSet getVaccinationData()
    {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < 7; ++i)
        {
            float x = i;
            float y1 = 50 + i * 10;
            float y2 = 20 - 2*i;
            values.add(new BarEntry(x, new float[]{y2, y1}));
        }

        return new BarDataSet(values, "New cases");
    }

    private void customBarAppearance(BarDataSet barDataSet)
    {
        // customize the color of the bar
        barDataSet.setColors(ContextCompat.getColor(this.requireContext(), R.color.green_medium),
                            ContextCompat.getColor(this.requireContext(), R.color.green_light));
        barDataSet.setStackLabels(new String[]{"Fully vaccinated", "Partially vaccinated"});

        // hide the value on the bar
        barDataSet.setDrawValues(false);

        // set the text size
        // note: it is redundant as the value on the bar was hidden
        barDataSet.setValueTextSize(12f);

        // set the bar color when clicked
        barDataSet.setHighLightColor(ContextCompat.getColor(this.requireContext(), R.color.green_medium));
        barDataSet.setHighLightAlpha(100);
    }

    private void configureChartNewCasesAppearance(BarChart barChart)
    {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);

        // The stacked bar will be highlighted as one
        // instead of being highlighted separately
        barChart.setHighlightFullBarEnabled(true);

        // disable zooming
        barChart.setScaleEnabled(false);

        // set the label when the bar is highlighted
        IMarker customMarkerView = new CustomMarkerView(this.requireContext(), R.layout.label_pop_up);
        barChart.setMarker(customMarkerView);

        // setting animation for y-axis, the bar will pop up from 0
        barChart.animateY(1000);
        // animation for x-axis, so the bar will pop up separately
        barChart.animateX(1000);

        String[] DAYS = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

        XAxis xAxis = barChart.getXAxis();
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


        YAxis yAxisLeft = barChart.getAxisLeft();
        // hide horizontal grid line
        yAxisLeft.setGridColor(ContextCompat.getColor(this.requireContext(), R.color.grey_light));
        yAxisLeft.enableGridDashedLine(10f, 10f, 0f);

        // disable right axis
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setTextSize(11f);
        //set the alignment of the legend
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        // setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

    }

    private void configureCombinedChartAppearance(CombinedChart chart)
    {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        chart.setDrawGridBackground(false);
        chart.setHighlightFullBarEnabled(false);

        chart.setScaleEnabled(false);

        IMarker marker = new CustomMarkerView(this.requireContext(), R.layout.label_pop_up);
        chart.setMarker(marker);

        chart.animateY(1000);
        chart.animateX(1000);

        // draw bars behind lines
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        Legend legend = chart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.enableGridDashedLine(10f, 10f, 0f);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setAxisMinimum(0f);

        String[] DAYS = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value)
            {
                return DAYS[(int) value % DAYS.length];
            }
        });

        CombinedData data = new CombinedData();

        data.setData(generateBarData());
        data.setData(generateLineData());

//        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        chart.setData(data);
        chart.invalidate();
    }

    private BarData generateBarData()
    {
        ArrayList<BarEntry> entries1 = new ArrayList<>();

        for (int i = 0; i < 7; ++i)
        {
            entries1.add(new BarEntry(i, i*3 + 10));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(ContextCompat.getColor(this.requireContext(), R.color.blue_medium));
        set1.setHighLightColor(ContextCompat.getColor(this.requireContext(), R.color.blue_medium));
        set1.setHighLightAlpha(100);
        set1.setDrawValues(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f;
        float barWidth = 0.45f;

        BarData barData = new BarData(set1);

//        barData.setBarWidth(barWidth);
//        barData.groupBars(0, groupSpace, barSpace);

        return barData;
    }

    private LineData generateLineData()
    {
        LineData lineData = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < 7; ++i)
        {
            entries.add(new Entry(i, 0.1f + i * i * 0.2f));
        }

        LineDataSet set = new LineDataSet(entries, "Line Dataset");
        set.setColors(ContextCompat.getColor(this.requireContext(), R.color.grey_medium));
        set.setLineWidth(2.5f);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(false);

        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineData.addDataSet(set);

        return lineData;
    }

}