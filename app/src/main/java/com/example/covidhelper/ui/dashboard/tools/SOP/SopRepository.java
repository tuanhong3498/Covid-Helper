package com.example.covidhelper.ui.dashboard.tools.SOP;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.SOPContentDAO;
import com.example.covidhelper.database.DAO.SOPDAO;
import com.example.covidhelper.database.table.SOPContent;

public class SopRepository
{
    private SOPDAO sopdao;
    private SOPContentDAO sopContentDAO;

    SopRepository(Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        sopdao = covidHelperDatabase.getSOPDAO();
        sopContentDAO = covidHelperDatabase.getSOPContentDAO();
    }

    LiveData<SOPContent> getSOP(String state)
    {
        return sopContentDAO.getSOP(state);
    }
}
