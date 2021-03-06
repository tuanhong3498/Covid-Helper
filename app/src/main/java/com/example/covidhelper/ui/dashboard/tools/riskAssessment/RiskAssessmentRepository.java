package com.example.covidhelper.ui.dashboard.tools.riskAssessment;

import android.app.Application;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;

public class RiskAssessmentRepository
{
    private final UserDAO userDAO;

    RiskAssessmentRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
    }

    void updateRiskStatus(String riskStatus,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> userDAO.updateRiskStatus(riskStatus,userID));
    }

    void updateSymptomStatus(String symptomStatus,int userID) {
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> userDAO.updateSymptomStatus(symptomStatus,userID));
    }
}
