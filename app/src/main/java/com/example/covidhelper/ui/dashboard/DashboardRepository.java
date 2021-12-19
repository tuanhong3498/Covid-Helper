package com.example.covidhelper.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.DailyNewCasesDAO;
import com.example.covidhelper.database.DAO.DailyNewDeathsDAO;
import com.example.covidhelper.database.DAO.DailyVaccineAdministrationDAO;
import com.example.covidhelper.database.DAO.EmergencyHotlineDAO;
import com.example.covidhelper.database.DAO.FaqDAO;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.DailyNewCases;
import com.example.covidhelper.database.table.DailyNewDeaths;
import com.example.covidhelper.database.table.FAQ;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DashboardRepository
{
    private final FaqDAO faqDAO;
    private final DailyNewCasesDAO casesDAO;
    private final DailyNewDeathsDAO deathsDAO;
    private final DailyVaccineAdministrationDAO vaccineDAO;
    private final EmergencyHotlineDAO emergencyHotlineDAO;
    private final UserDAO userDAO;

    public DashboardRepository(Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);

        faqDAO = covidHelperDatabase.getFaqDAO();
        casesDAO = covidHelperDatabase.getDailyNewCasesDAO();
        deathsDAO = covidHelperDatabase.getDailyNewDeathsDAO();
        vaccineDAO = covidHelperDatabase.getDailyVaccineAdministrationDAO();
        emergencyHotlineDAO = covidHelperDatabase.getEmergencyHotlineDAO();
        userDAO = covidHelperDatabase.getUserDAO();
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

    public String getEmergencyHotline(String state) throws ExecutionException, InterruptedException
    {
        Callable<String> callable = () -> emergencyHotlineDAO.getHotlineNumber(state);

        Future<String> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }

    public String getUserLivingState(int userID) throws ExecutionException, InterruptedException
    {
        Callable<String> callable = () -> userDAO.getUserLivingState(userID);

        Future<String> future = Executors.newSingleThreadExecutor().submit(callable);

        return future.get();
    }
}
