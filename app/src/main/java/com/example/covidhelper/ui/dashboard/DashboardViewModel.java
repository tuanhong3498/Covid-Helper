package com.example.covidhelper.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.DailyNewCases;
import com.example.covidhelper.database.table.DailyNewDeaths;
import com.example.covidhelper.database.table.FAQ;
import com.example.covidhelper.ui.dashboard.DashboardRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DashboardViewModel extends AndroidViewModel
{
    private final DashboardRepository dashboardRepository;

    public DashboardViewModel(@NonNull Application application)
    {
        super(application);
        dashboardRepository = new DashboardRepository(application);
    }

    LiveData<List<FAQ>> getAllFAQ()
    {
        return dashboardRepository.getAllFAQ();
    }

    LiveData<List<DailyNewCases>> getLatestNewCase()
    {
        return dashboardRepository.getLatestNewCases();
    }

    LiveData<List<DailyNewDeaths>> getLatestNewDeath()
    {
        return dashboardRepository.getLatestNewDeaths();
    }

    LiveData<Float> getAccumulatedDose1()
    {
        return dashboardRepository.getAccumulatedDose1();
    }

    LiveData<Float> getAccumulatedDose2()
    {
        return dashboardRepository.getAccumulatedDose2();
    }

    String getEmergencyHotline(String state) throws ExecutionException, InterruptedException
    {
        return dashboardRepository.getEmergencyHotline(state);
    }
}
