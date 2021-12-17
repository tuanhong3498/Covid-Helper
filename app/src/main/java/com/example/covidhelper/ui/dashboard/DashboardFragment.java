package com.example.covidhelper.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidhelper.R;
import com.example.covidhelper.database.table.FAQ;
import com.example.covidhelper.model.Faq;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;


public class DashboardFragment extends Fragment
{
    // UI elements
    private ImageView toolVaccination;
    private ImageView toolHotspot;
    private ImageView toolReportSelfTest;
    private ImageView toolRiskAssessment;
    private ImageView toolCallAmbulance;
    private ImageView toolSop;

    private TextView textViewDataUpdateDate;
    private TextView textViewCases;
    private TextView textViewDeath;
    private TextView textViewPartialVaccinated;
    private TextView textViewFullVaccinated;
    private ImageView imageViewCaseTrend;
    private ImageView imageViewDeathTrend;
    private MaterialButton buttonInDepthStat;

    private RecyclerView recyclerViewFaq;

    private NavController navController;

    private DashboardViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        toolVaccination = root.findViewById(R.id.dashboard_tool_vaccine);
        toolHotspot = root.findViewById(R.id.dashboard_tool_hotspot);
        toolReportSelfTest = root.findViewById(R.id.dashboard_tool_self_test);
        toolRiskAssessment = root.findViewById(R.id.dashboard_tool_risk_assessment);
        toolCallAmbulance = root.findViewById(R.id.dashboard_tool_sos);
        toolSop = root.findViewById(R.id.dashboard_tool_sop);

        textViewDataUpdateDate = root.findViewById(R.id.dashboard_covid_data_date);
        textViewCases = root.findViewById(R.id.dashboard_data_cases);
        textViewDeath = root.findViewById(R.id.dashboard_data_death);
        textViewPartialVaccinated = root.findViewById(R.id.dashboard_data_vaccine_1);
        textViewFullVaccinated = root.findViewById(R.id.dashboard_data_vaccine_2);
        imageViewCaseTrend = root.findViewById(R.id.dashboard_trend_case);
        imageViewDeathTrend = root.findViewById(R.id.dashboard_trend_death);
        buttonInDepthStat = root.findViewById(R.id.dashboard_button_in_depth_statistic);

        recyclerViewFaq = (RecyclerView) root.findViewById(R.id.dashboard_faq_recycler_view);

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        mViewModel = factory.create(DashboardViewModel.class);

        initializeCovidData();
        addFaqList();

        return root;
    }

    private void initializeCovidData()
    {
        AtomicLong latestDate = new AtomicLong(0);

        mViewModel.getLatestNewCase().observe(requireActivity(), dailyNewCases ->
        {
            textViewCases.setText(String.valueOf((int) dailyNewCases.get(0).newCasesAvg));
            setTrendIncreased(imageViewCaseTrend, dailyNewCases.get(0).newCasesAvg >= dailyNewCases.get(1).newCasesAvg);
            latestDate.set(Math.max(dailyNewCases.get(0).date, latestDate.get()));
        });
        mViewModel.getLatestNewDeath().observe(requireActivity(), dailyNewDeaths ->
        {
            textViewDeath.setText(String.valueOf((int) dailyNewDeaths.get(0).newDeathAvg));
            setTrendIncreased(imageViewDeathTrend, dailyNewDeaths.get(0).newDeathAvg >= dailyNewDeaths.get(1).newDeathAvg);
            latestDate.set(Math.max(dailyNewDeaths.get(0).date, latestDate.get()));
            textViewDataUpdateDate.setText(dataAsOf(latestDate.get()));
        });
        mViewModel.getAccumulatedDose1().observe(requireActivity(), accumulatedDose1 ->
        {
            textViewPartialVaccinated.setText(accumulatedVacToPercentage(accumulatedDose1));
        });
        mViewModel.getAccumulatedDose2().observe(requireActivity(), accumulatedDose2 ->
        {
            textViewFullVaccinated.setText(accumulatedVacToPercentage(accumulatedDose2));
        });
    }

    private String accumulatedVacToPercentage(float accumulatedVac)
    {
        int population = 32000000;
        return String.format(Locale.US, "%.2f", accumulatedVac/population*100) + "%";
    }

    private String dataAsOf(long unixTimeStamp)
    {
        Date date = new Date(unixTimeStamp*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm aa", Locale.UK);
        return "Data as of " + sdf.format(date);
    }

    private void setTrendIncreased(ImageView image, boolean increasing)
    {
        if(increasing)
        {
            // set a green triangle pointing upwards
            image.setRotation(180);
            image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green_medium));
        }
        else
        {
            // set a red triangle pointing downwards
            image.setRotation(0);
            image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red_dark));
        }
    }

    // function to fetch API using Volley
//    private void fetchAPIUsingVolley()
//    {
//        RequestQueue queue = Volley.newRequestQueue(requireContext());
//        // the url of the API
//        String url = "https://disease.sh/v3/covid-19/all";
//
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.GET,
//                url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        // process the response from the API
//                        try
//                        {
//                            JSONObject jsonObject = new JSONObject(response.toString());
//
//                            textViewCases.setText(jsonObject.getString("todayCases"));
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        Toast.makeText(
//                                getContext(),
//                                error.getMessage(),
//                                Toast.LENGTH_SHORT
//                        ).show();
//                    }
//                });
//
//        // add the request to the queue
//        queue.add(stringRequest);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        navController = Navigation.findNavController(view);

        // set onclick listener
        toolVaccination.setOnClickListener(v ->
                navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToVaccinationFragment()));

        toolHotspot.setOnClickListener(v ->
                navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToHotspotFragment()));

        toolReportSelfTest.setOnClickListener(v ->
                navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToReportSelfTestFragment()));

        toolRiskAssessment.setOnClickListener(v ->
                navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToRiskAssessmentFragment()));

        toolSop.setOnClickListener(v ->
                navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToSopFragment()));

        buttonInDepthStat.setOnClickListener(v ->
                navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToInDeptStatFragment()));
//        toolCallAmbulance.setOnClickListener(v ->
//        {
//
//        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void addFaqList()
    {
        ArrayList<Faq> faqList = new ArrayList<>();

        mViewModel.getAllFAQ().observe(requireActivity(), faqs ->
        {
            for(FAQ faq: faqs)
            {
                faqList.add(new Faq(faq.question, faq.answer));
            }
            loadFaqItems(recyclerViewFaq, faqList);
        });

    }

    private void loadFaqItems(RecyclerView recyclerView, ArrayList<Faq> faqList)
    {
        FaqAdapter faqAdapter = new FaqAdapter(faqList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(faqAdapter);
    }
}