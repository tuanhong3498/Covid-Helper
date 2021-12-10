package com.example.covidhelper.ui.dashboard.tools.vaccine;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.DAO.VaccineRegistrationRecordDAO;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccineRegistrationRecord;

public class VaccinationRepository
{
    private UserDAO userDAO;
    private VaccineRegistrationRecordDAO vaccineRegistrationRecordDAO;

    VaccinationRepository (Application application)
    {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
        vaccineRegistrationRecordDAO = covidHelperDatabase.getVaccineRegistrationRecordDAO();
    }

    LiveData<User> getUserInfo(int userID)
    {
        return userDAO.getUser(userID);
    }

    LiveData<VaccineRegistrationRecord> getVaccineRegistrationRecord(int userID)
    {
        return vaccineRegistrationRecordDAO.getRegistrationRecord(userID);
    }
}
