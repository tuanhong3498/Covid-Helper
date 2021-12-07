package com.example.covidhelper.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.FAQ;
import com.example.covidhelper.ui.dashboard.DashboardRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel
{
    private DashboardRepository dashboardRepository;

    public DashboardViewModel(@NonNull Application application)
    {
        super(application);
        dashboardRepository = new DashboardRepository(application);
    }

    LiveData<List<FAQ>> getAllFAQ()
    {
        return dashboardRepository.getAllFAQ();
    }
}
