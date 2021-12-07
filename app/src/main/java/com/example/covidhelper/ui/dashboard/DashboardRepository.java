package com.example.covidhelper.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.DailyNewCasesDAO;
import com.example.covidhelper.database.DAO.DailyNewDeathsDAO;
import com.example.covidhelper.database.DAO.DailyVaccineAdministrationDAO;
import com.example.covidhelper.database.DAO.FaqDAO;
import com.example.covidhelper.database.table.DailyNewCases;
import com.example.covidhelper.database.table.DailyNewDeaths;
import com.example.covidhelper.database.table.FAQ;

import java.util.List;

public class DashboardRepository
{
    private FaqDAO faqDAO;
    private DailyNewCasesDAO casesDAO;
    private DailyNewDeathsDAO deathsDAO;
    private DailyVaccineAdministrationDAO vaccineDAO;

    public DashboardRepository(Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        faqDAO = covidHelperDatabase.getFaqDAO();

        casesDAO = covidHelperDatabase.getDailyNewCasesDAO();
        deathsDAO = covidHelperDatabase.getDailyNewDeathsDAO();
        vaccineDAO = covidHelperDatabase.getDailyVaccineAdministrationDAO();
    }

    public LiveData<List<FAQ>> getAllFAQ()
    {
        return faqDAO.getAllFaq();
    }

    public LiveData<List<DailyNewCases>> getLatestNewCases()
    {
        return casesDAO.getRecentNewCases(2);
    }

    public LiveData<List<DailyNewDeaths>> getLatestNewDeaths()
    {
        return deathsDAO.getRecentDeaths(2);
    }

    public LiveData<Float> getAccumulatedDose1()
    {
        return vaccineDAO.getAccumulativeDose1();
    }

    public LiveData<Float> getAccumulatedDose2()
    {
        return vaccineDAO.getAccumulativeDose2();
    }
}
