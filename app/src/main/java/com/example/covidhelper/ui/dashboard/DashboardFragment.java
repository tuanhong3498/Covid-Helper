package com.example.covidhelper.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class DashboardFragment extends Fragment
{
    private String state;

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

        SharedPreferences sp = sp = requireContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int userID = sp.getInt("userID", -1);
        try
        {
            state = mViewModel.getUserLivingState(userID);
        }
        catch (ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }

        initializeCovidData();
        addFaqList();

        return root;
    }

    private void initializeCovidData()
    {
        // get vaccination data from room database
        mViewModel.getAccumulatedDose1().observe(requireActivity(), accumulatedDose1 ->
        {
            textViewPartialVaccinated.setText(accumulatedVacToPercentage(accumulatedDose1));
        });
        mViewModel.getAccumulatedDose2().observe(requireActivity(), accumulatedDose2 ->
        {
            textViewFullVaccinated.setText(accumulatedVacToPercentage(accumulatedDose2));
        });

        // get number of cases & deaths from API
        fetchDataFromAPI();
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
    private void fetchDataFromAPI()
    {
        int numDay = 8;
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        // the url of the API to get the data of last 8 days
        String url = "https://disease.sh/v3/covid-19/historical/Malaysia?lastdays=" + numDay;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // process the response from the API
                        try
                        {
                            // get the time series data
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectData = jsonObject.getJSONObject("timeline");
                            JSONObject jsonObjectCases = jsonObjectData.getJSONObject("cases");
                            JSONObject jsonObjectDeath = jsonObjectData.getJSONObject("deaths");

                            // get the number of new cases and deaths over the last 7 days
                            textViewCases.setText(String.valueOf((int)getTotal(jsonObjectCases, numDay-7, numDay-1)/7));
                            textViewDeath.setText(String.valueOf((int)getTotal(jsonObjectDeath, numDay-7, numDay-1)));

                            // compare the number of new cases & deaths of the last 7 days to that of the previous last 7 days
                            setTrendIncreased(imageViewCaseTrend, getTotal(jsonObjectCases, numDay-7, numDay-1) >= getTotal(jsonObjectCases, 0, numDay-2));
                            setTrendIncreased(imageViewDeathTrend, getTotal(jsonObjectDeath, numDay-7, numDay-1) >= getTotal(jsonObjectDeath, 0, numDay-2));

                            // display the date that the data was updated
                            JSONArray updateDates = jsonObjectCases.names();
                            String latestUpdateDate = null;
                            if(updateDates != null)
                            {
                                latestUpdateDate = updateDates.getString(numDay-1);
                                textViewDataUpdateDate.setText(dataAsOf(getUnixTime(latestUpdateDate)));
                            }
                            else
                            {
                                textViewDataUpdateDate.setText(dataAsOf(0));
                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(
                                getContext(),
                                error.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });

        // add the request to the queue
        queue.add(stringRequest);
    }

    // convert from string to unix time
    private long getUnixTime(String dateString)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.UK);
            Date date = sdf.parse(dateString);
            assert date != null;
            long epoch = date.getTime();
            return epoch/1000;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return 0;
        }
    }

    private int getTotal(JSONObject jsonObject, int indexFrom, int indexTo)
    {
        return getItemFromJsonObject(jsonObject, indexTo) - getItemFromJsonObject(jsonObject, indexFrom);
    }

    private int getItemFromJsonObject(JSONObject jsonObject, int index)
    {
        JSONArray keys = jsonObject.names();
        try
        {
            if (keys != null)
            {
                return jsonObject.getInt(keys.getString(index));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return -1;
    }

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
        toolCallAmbulance.setOnClickListener(v ->
        {
            String phoneNumber;
            try
            {
                phoneNumber = mViewModel.getEmergencyHotline(state);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));

                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Call Emergency Hotline")
                        .setMessage("You will be calling the emergency hotline of " + state + ", please ensure that you are only calling it in event of an emergency.")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Call", (dialog, which) ->
                        {
                            startActivity(intent);
                        })
                        .show();
            }
            catch (ExecutionException | InterruptedException e)
            {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Failed to get hotline number")
                        .setMessage("The application has failed to retrieve the hotline number in your state. You may need to call 999 for help.")
                        .setPositiveButton("Got it", null)
                        .show();
                e.printStackTrace();
            }
        });

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