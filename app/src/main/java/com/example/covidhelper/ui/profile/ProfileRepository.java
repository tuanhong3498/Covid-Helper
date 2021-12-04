package com.example.covidhelper.ui.profile;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.DAO.VaccineDose1RecordDAO;
import com.example.covidhelper.database.DAO.VaccineDose2RecordDAO;
import com.example.covidhelper.database.table.User;
import com.example.covidhelper.database.table.VaccineDose1Record;
import com.example.covidhelper.database.table.VaccineDose2Record;

import java.util.List;

public class ProfileRepository
{
    private UserDAO userDAO;
    private VaccineDose1RecordDAO vaccineDose1RecordDAO;
    private VaccineDose2RecordDAO vaccineDose2RecordDAO;

    ProfileRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
        vaccineDose1RecordDAO = covidHelperDatabase.getVaccineDose1RecordDAO();
        vaccineDose2RecordDAO = covidHelperDatabase.getVaccineDose2RecordDAO();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<User>> getUserInfo(int userID) {
        return userDAO.getUserInfo(userID);
    }

    LiveData<List<VaccineDose1Record>> getVaccineDose1Record(int userID) {
        return vaccineDose1RecordDAO.getVaccineDose1Record(userID);
    }

    LiveData<List<VaccineDose2Record>> getVaccineDose2Record(int userID) {
        return vaccineDose2RecordDAO.getVaccineDose2Record(userID);
    }
}
