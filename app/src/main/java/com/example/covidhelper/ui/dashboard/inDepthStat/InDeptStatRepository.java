package com.example.covidhelper.ui.dashboard.inDepthStat;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.CovidTestsConductedDAO;
import com.example.covidhelper.database.DAO.DailyNewCasesDAO;
import com.example.covidhelper.database.DAO.DailyNewDeathsDAO;
import com.example.covidhelper.database.DAO.DailyVaccineAdministrationDAO;
import com.example.covidhelper.database.table.CovidTestsConducted;
import com.example.covidhelper.database.table.DailyNewCases;
import com.example.covidhelper.database.table.DailyNewDeaths;
import com.example.covidhelper.database.table.DailyVaccineAdministration;
import com.example.covidhelper.ui.dashboard.DashboardRepository;

import java.util.List;

public class InDeptStatRepository
{
    private final DailyNewCasesDAO casesDAO;
    private final DailyNewDeathsDAO deathsDAO;
    private final DailyVaccineAdministrationDAO vaccineDAO;
    private final CovidTestsConductedDAO testDao;

    public InDeptStatRepository(Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);

        casesDAO = covidHelperDatabase.getDailyNewCasesDAO();
        deathsDAO = covidHelperDatabase.getDailyNewDeathsDAO();
        vaccineDAO = covidHelperDatabase.getDailyVaccineAdministrationDAO();
        testDao = covidHelperDatabase.getCovidTestsConductedDAO();
    }

    public LiveData<List<DailyNewCases>> getLatestNewCases()
    {
        return casesDAO.getRecentNewCases(14);
    }

    public LiveData<List<DailyNewDeaths>> getLatestNewDeaths()
    {
        return deathsDAO.getRecentDeaths(14);
    }

    public LiveData<List<DailyVaccineAdministration>> getLatestVaccineAdministrated()
    {
        return vaccineDAO.getRecentVacData(14);
    }

    public LiveData<List<CovidTestsConducted>> getLatestTestData()
    {
        return testDao.getRecentTestData(14);
    }
}
