package com.example.covidhelper.ui.dashboard.tools.SOP;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.SOPContentDAO;
import com.example.covidhelper.database.DAO.SOPDAO;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.SOPContent;
import com.example.covidhelper.database.table.User;

public class SopRepository
{
    private SOPContentDAO sopContentDAO;
    private UserDAO userDAO;

    SopRepository(Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        sopContentDAO = covidHelperDatabase.getSOPContentDAO();
        userDAO = covidHelperDatabase.getUserDAO();
    }

    LiveData<SOPContent> getSOP(String state)
    {
        return sopContentDAO.getSOP(state);
    }

    LiveData<User> getUser(int userID)
    {
        return userDAO.getUser(userID);
    }
}
