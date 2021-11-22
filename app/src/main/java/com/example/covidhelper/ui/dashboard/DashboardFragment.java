package com.example.covidhelper.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.covidhelper.R;
import com.example.covidhelper.model.Faq;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment
{
    // UI elements
    private ImageView toolVaccination;
    private ImageView toolHotspot;
    private ImageView toolReportSelfTest;
    private ImageView toolRiskAssessment;
    private ImageView toolCallAmbulance;
    private ImageView toolSop;
    private MaterialButton buttonInDepthStat;
    private RecyclerView recyclerViewFaq;

    private NavController navController;

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
        buttonInDepthStat = root.findViewById(R.id.dashboard_button_in_depth_statistic);
        recyclerViewFaq = (RecyclerView) root.findViewById(R.id.dashboard_faq_recycler_view);

        addFaqList();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        navController = Navigation.findNavController(view);

        // set onclick listener
        toolVaccination.setOnClickListener(v ->
        {
            navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToVaccinationFragment());
        });
        toolHotspot.setOnClickListener(v ->
        {
            navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToHotspotFragment());
        });
        toolReportSelfTest.setOnClickListener(v ->
        {
            navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToReportSelfTestFragment());
        });
        toolRiskAssessment.setOnClickListener(v ->
        {
            navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToRiskAssessmentFragment());
        });
        toolSop.setOnClickListener(v ->
        {
            navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToSopFragment());
        });
        buttonInDepthStat.setOnClickListener(v ->
        {
            navController.navigate(DashboardFragmentDirections.actionDashboardFragmentToInDeptStatFragment());
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void addFaqList()
    {
        ArrayList<Faq> faqList = new ArrayList<>();

        for (int i = 0; i < 10; ++i)
        {
            faqList.add(new Faq("Do we need to wear mask?", "Sure"));
        }

        loadFaqItems(recyclerViewFaq, faqList);
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