package com.example.covidhelper.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.FaqDAO;
import com.example.covidhelper.database.table.FAQ;

import java.util.List;

public class DashboardRepository
{
    private FaqDAO faqDAO;

    public DashboardRepository(Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        faqDAO = covidHelperDatabase.getFaqDAO();
    }

    public LiveData<List<FAQ>> getAllFAQ()
    {
        return faqDAO.getAllFaq();
    }
}
