package com.example.covidhelper.ui.dashboard;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covidhelper.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class InDeptStatFragment extends Fragment
{
    private CombinedChart chartNewCases;
    private CombinedChart chartNewDeaths;
    private CombinedChart chartVaccination;
    private CombinedChart chartTest;
    private TextView textViewDateNewCases;
    private TextView textViewDateNewDeaths;
    private TextView textViewDateVaccination;
    private TextView textViewDateTest;

    final private int UNIX_DAY = 86400;

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

        chartNewCases = root.findViewById(R.id.in_depth_stat_chart_new_cases);
        chartNewDeaths = root.findViewById(R.id.in_depth_stat_chart_new_deaths);
        chartVaccination = root.findViewById(R.id.in_depth_stat_chart_vaccine);
        chartTest = root.findViewById(R.id.in_depth_stat_chart_test);

        textViewDateNewCases = root.findViewById(R.id.in_depth_stat_update_date_new_cases);
        textViewDateNewDeaths = root.findViewById(R.id.in_depth_stat_update_date_new_death);
        textViewDateVaccination = root.findViewById(R.id.in_depth_stat_update_date_vaccine);
        textViewDateTest = root.findViewById(R.id.in_depth_stat_update_date_test);

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
        // TODO: get data from DB
        float[] newCases = {10915, 9066, 8075, 8817, 9380, 9890, 9751, 8743, 7373, 6709, 7276, 7950, 8084, 7420};
        float[] newCasesAvg = {9915, 9666, 9075, 8917, 9080, 9690, 9651, 9443, 8773, 8209, 8076, 8050, 8084, 7820};
        int startingDate = 1633046400;

        initializeCombinedChartAppearance(chartNewCases, startingDate);

        CombinedData data = new CombinedData();

        data.setData(generateBarData(new float[][]{newCases,},
                                        new String[]{"New Cases"},
                                        new int[]{R.color.blue_light}));
        data.setData(generateLineData(newCasesAvg, "7 days Avg. Cases", YAxis.AxisDependency.LEFT));

        configureCombinedChart(chartNewCases, data);
    }

    private void configureCombinedChart(CombinedChart chart, CombinedData data)
    {
        XAxis xAxis = chart.getXAxis();
        // adding space on X-axis so that the left and right most bars can be shown completely
        xAxis.setAxisMinimum(data.getXMin() - 0.75f);
        xAxis.setAxisMaximum(data.getXMax() + 0.75f);

        chart.setData(data);
        chart.invalidate();
    }

    private void initializeCombinedChartAppearance(CombinedChart chart, int startingDate)
    {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        chart.setDrawGridBackground(false);
        // disable zoom
        chart.setScaleEnabled(false);
        // animation of the graph
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
        rightAxis.enableGridDashedLine(15f, 15f, 0f);
        rightAxis.setGridColor(ContextCompat.getColor(this.requireContext(), R.color.grey_light));
        rightAxis.setGridLineWidth(1f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setDrawAxisLine(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.enableGridDashedLine(15f, 15f, 0f);
        leftAxis.setGridColor(ContextCompat.getColor(this.requireContext(), R.color.grey_light));
        leftAxis.setGridLineWidth(1f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(3f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value)
            {
                Date date = new Date((long)(value * UNIX_DAY + startingDate)*1000);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.ENGLISH);
                return sdf.format(date);
            }
        });

        //Todo: remove the following


        // set the marker when the bar is highlighted
        IMarker marker = new CustomMarkerView(this.requireContext(), R.layout.label_pop_up);
        chart.setMarker(marker);


    }

    private BarData generateStackedBarData(XAxis xAxis)
    {
        int referenceTimestamp = 1635724800; // the starting date
        float[] dose1 = {2399, 2342, 2463, 2464, 3467, 2345, 4456, 3456, 3455, 2385, 3335, 2346, 2353, 2467, 2563, 2463, 2356};
        float[] dose2 = {2834, 3538, 4837, 2838, 2948, 6323, 5636, 6443, 5355, 3456, 6783, 3634, 7343, 6443, 5644, 3466, 3645};
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < 14; ++i)
        {
//            entries.add(new BarEntry(i, i*i + 1));
            entries.add(new BarEntry(i, new float[]{dose1[i], dose2[i]}));
        }

        BarDataSet set = new BarDataSet(entries, "");
        set.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        // customize the color of the bar
        set.setColors(ContextCompat.getColor(this.requireContext(), R.color.blue_medium),
                        ContextCompat.getColor(this.requireContext(), R.color.blue_light));
        // disable the highlight on the bar -> highlight will be depends on the line
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value)
            {
                Date date = new Date((long)(value * 86400 + referenceTimestamp)*1000);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.ENGLISH);
                return sdf.format(date);
            }
        });

        return new BarData(set);
    }

    private BarData generateBarData(float[][] datasets, String[] labels, @ColorRes int[] barColors)
    {
        ArrayList<BarEntry> entries = new ArrayList<>();

        // get the data from datasets
        for (int entry = 0; entry < datasets[0].length; ++entry)
        {
            // get the data from all dataset on the same entry
            float[] data = new float[datasets.length];
            for (int dataset = 0; dataset < datasets.length; ++dataset)
            {
                data[dataset] = datasets[dataset][entry];
            }
            entries.add(new BarEntry(entry, data));
        }

        BarDataSet set = new BarDataSet(entries, "");
        if (labels.length == 1)
        {
            set.setLabel(labels[0]);
        }
        else if (labels.length > 1)
        {
            set.setStackLabels(labels);
        }
        int[] colors = new int[barColors.length];
        // get the color from color id
        for (int i = 0; i < barColors.length; ++i)
        {
            colors[i] = ContextCompat.getColor(this.requireContext(), barColors[i]);
        }
        // customize the color of the bar
        set.setColors(colors);
        // disable the highlight on the bar -> highlight will be depends on the line
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return new BarData(set);
    }

    private LineData generateLineData(float[] data, String label, YAxis.AxisDependency dependency)
    {
        LineData lineData = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < 14; ++i)
        {
            entries.add(new Entry(i, data[i]));
        }

        LineDataSet set = new LineDataSet(entries, label);
        set.setColors(ContextCompat.getColor(this.requireContext(), R.color.grey_medium));
        set.setLineWidth(2.5f);
        // set mode of the line LINEAR for straight line between data points
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setDrawHorizontalHighlightIndicator(false);
        set.setHighLightColor(ContextCompat.getColor(this.requireContext(), R.color.grey_medium));
        set.setHighlightLineWidth(1.5f);
        set.enableDashedHighlightLine(8f, 8f, 0);

        set.setAxisDependency(dependency);
        lineData.addDataSet(set);

        return lineData;
    }
}