package com.example.covidhelper.ui.dashboard.tools;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.User;

import java.util.List;

public class RiskAssessmentRepository
{
    private UserDAO userDAO;

    RiskAssessmentRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
    }


    void updateSymptomStatus(String symptomStatus,int userID) {
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.updateSymptomStatus(symptomStatus,userID);
        });
    }
}
