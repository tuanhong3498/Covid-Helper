package com.example.covidhelper.ui.dashboard;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
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
import com.github.mikephil.charting.components.LegendEntry;
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
import com.google.android.material.button.MaterialButton;

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
    private MaterialButton buttonVisitCovidNow;

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

        buttonVisitCovidNow = root.findViewById(R.id.in_depth_stat_button_to_covidNow);

        initializeChartNewCases(chartNewCases);
        initializeChartNewDeath(chartNewDeaths);
        initializeChartVaccination(chartVaccination);
        initializeChartTest(chartTest);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InDeptStatViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        buttonVisitCovidNow.setOnClickListener(v ->
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://covidnow.moh.gov.my/"));
            startActivity(intent);
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeChartTest(CombinedChart chart)
    {
        // TODO: get data from DB
        float[] rtk = {99474, 96673, 100825, 67827, 70297, 141200, 113545, 110446, 100153, 90455, 67260, 67441, 128940, 108487};
        float[] pcr = {46182, 43082, 37382, 34369, 27174, 35180, 44228, 39793, 35360, 32691, 28749, 24980, 32291, 39859};
        float[] positiveRate = {9.9f, 9.4f, 9.3f, 9.0f, 8.7f, 8.5f, 8.3f, 7.8f, 7.5f, 7.3f, 6.9f, 6.8f, 6.5f, 5.9f};
        int startingDate = 1633046400;

        initializeCombinedChartAppearance(chart, startingDate);

        CombinedData data = new CombinedData();

        data.setData(generateBarData(new float[][]{pcr, rtk},
                new String[]{"PCR", "Rtk-Ag"},
                new int[]{R.color.blue_medium, R.color.blue_light}));
        data.setData(generateLineData(positiveRate, "PositiveRate", YAxis.AxisDependency.RIGHT));

        IMarker marker = new CustomMarkerView(this.requireContext(),
                R.layout.label_pop_up,
                startingDate,
                new String[]{"Rtk-Ag", "PCR", "Positive Rate"},
                new float[][]{rtk, pcr, positiveRate},
                new boolean[]{false, false, true});

        configureCombinedChart(chart, data, marker);
    }

    private void initializeChartVaccination(CombinedChart chart)
    {
        // TODO: get data from DB
        float[] dose1 = {104345, 92431, 98803, 128890, 124836, 114476, 109107, 75148, 40414, 32574, 53817, 56926, 52587, 41614};
        float[] dose2 = {141128, 130349, 116808, 104156, 106760, 101857, 93232, 105938, 108574, 101929, 139902, 157390, 159964, 168136};
        float[] dosagesAvg = {250000, 230000, 220000, 225000, 230000, 225000, 210000, 190000, 160000, 150000, 160000, 180000, 190000, 200000};
        int startingDate = 1633046400;

        initializeCombinedChartAppearance(chart, startingDate);

        CombinedData data = new CombinedData();

        data.setData(generateBarData(new float[][]{dose2, dose1},
                new String[]{"Dose 2", "Dose 1"},
                new int[]{R.color.green_medium, R.color.green_light}));
        data.setData(generateLineData(dosagesAvg, "Avg. dosages in last 7 days", YAxis.AxisDependency.LEFT));

        IMarker marker = new CustomMarkerView(this.requireContext(),
                R.layout.label_pop_up,
                startingDate,
                new String[]{"Dose 1", "Dose 2", "7 days avg."},
                new float[][]{dose1, dose2, dosagesAvg},
                new boolean[]{false, false, false});

        configureCombinedChart(chart, data, marker);
    }

    private void initializeChartNewDeath(CombinedChart chart)
    {
        // TODO: get data from DB
        float[] newDeath = {128, 98, 87, 95, 87, 86, 79, 64, 76, 67, 59, 30, 6, 0};
        float[] newDeathAvg = {130, 110, 100, 92, 93, 91, 87, 80, 79, 75, 70, 60, 40, 30};
        int startingDate = 1633046400;

        initializeCombinedChartAppearance(chart, startingDate);

        CombinedData data = new CombinedData();

        data.setData(generateBarData(new float[][]{newDeath,},
                new String[]{"New Deaths"},
                new int[]{R.color.grey_light}));
        data.setData(generateLineData(newDeathAvg, "Avg. death in last 7 days", YAxis.AxisDependency.LEFT));

        IMarker marker = new CustomMarkerView(this.requireContext(),
                R.layout.label_pop_up,
                startingDate,
                new String[]{"New Deaths", "7 days Avg."},
                new float[][]{newDeath, newDeathAvg},
                new boolean[]{false, false});

        configureCombinedChart(chart, data, marker);
    }

    private void initializeChartNewCases(CombinedChart chart)
    {
        // TODO: get data from DB
        float[] newCases = {10915, 9066, 8075, 8817, 9380, 9890, 9751, 8743, 7373, 6709, 7276, 7950, 8084, 7420};
        float[] newCasesAvg = {9915, 9666, 9075, 8917, 9080, 9690, 9651, 9443, 8773, 8209, 8076, 8050, 8084, 7820};
        int startingDate = 1633046400;

        initializeCombinedChartAppearance(chart, startingDate);

        CombinedData data = new CombinedData();

        data.setData(generateBarData(new float[][]{newCases,},
                                        new String[]{"New Cases"},
                                        new int[]{R.color.blue_light}));
        data.setData(generateLineData(newCasesAvg, "7 days Avg. Cases", YAxis.AxisDependency.LEFT));

        IMarker marker = new CustomMarkerView(this.requireContext(),
                                                R.layout.label_pop_up,
                                                startingDate,
                                                new String[]{"New Cases", "7 days Avg."},
                                                new float[][]{newCases, newCasesAvg},
                                                new boolean[]{false, false});

        configureCombinedChart(chart, data, marker);
    }

    /// configuration of the chart after the chart has been populated with data
    private void configureCombinedChart(CombinedChart chart, CombinedData data, IMarker marker)
    {
        XAxis xAxis = chart.getXAxis();
        // adding space on X-axis so that the left and right most bars can be shown completely
        xAxis.setAxisMinimum(data.getXMin() - 0.75f);
        xAxis.setAxisMaximum(data.getXMax() + 0.75f);

        // set the marker when the bar is highlighted
        chart.setMarker(marker);

        chart.setData(data);

        // customize the first legend -> the legend for the line plot
        // use a LINE shape for its legend
        Legend legend = chart.getLegend();
        LegendEntry[] legendEntries = legend.getEntries();
        legendEntries[0].form = Legend.LegendForm.LINE;
        legend.setCustom(legendEntries);

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

        // add spacing between axis and the legend
        chart.setExtraBottomOffset(5f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
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