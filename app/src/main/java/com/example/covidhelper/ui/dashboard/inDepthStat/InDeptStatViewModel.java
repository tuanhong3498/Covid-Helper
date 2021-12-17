package com.example.covidhelper.ui.dashboard.inDepthStat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.covidhelper.database.table.CovidTestsConducted;
import com.example.covidhelper.database.table.DailyNewCases;
import com.example.covidhelper.database.table.DailyNewDeaths;
import com.example.covidhelper.database.table.DailyVaccineAdministration;

import java.util.List;

public class InDeptStatViewModel extends AndroidViewModel
{
    private final InDeptStatRepository inDeptStatRepository;

    public InDeptStatViewModel(@NonNull Application application)
    {
        super(application);
        inDeptStatRepository = new InDeptStatRepository(application);
    }

    public LiveData<List<DailyNewCases>> getLatestNewCases()
    {
        return inDeptStatRepository.getLatestNewCases();
    }

    public LiveData<List<DailyNewDeaths>> getLatestNewDeaths()
    {
        return inDeptStatRepository.getLatestNewDeaths();
    }

    public LiveData<List<DailyVaccineAdministration>> getLatestVaccineAdministrated()
    {
        return inDeptStatRepository.getLatestVaccineAdministrated();
    }

    public LiveData<List<CovidTestsConducted>> getLatestTestData()
    {
        return inDeptStatRepository.getLatestTestData();
    }
}