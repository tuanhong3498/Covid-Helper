package com.example.covidhelper.ui.profile;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.User;

import java.util.List;

public class ProfileRepository
{
    private final UserDAO userDAO;
//    private VaccineDose1RecordDAO vaccineDose1RecordDAO;
//    private VaccineDose2RecordDAO vaccineDose2RecordDAO;

    ProfileRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        userDAO = covidHelperDatabase.getUserDAO();
//        vaccineDose1RecordDAO = covidHelperDatabase.getVaccineDose1RecordDAO();
//        vaccineDose2RecordDAO = covidHelperDatabase.getVaccineDose2RecordDAO();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<User>> getUserInfo(int userID) {
        return userDAO.getUserInfo(userID);
    }

//    LiveData<List<VaccineDose1Record>> getVaccineDose1Record(int userID) {
//        return vaccineDose1RecordDAO.getVaccineDose1Record(userID);
//    }
//
//    LiveData<List<VaccineDose2Record>> getVaccineDose2Record(int userID) {
//        return vaccineDose2RecordDAO.getVaccineDose2Record(userID);
//    }

    void updateUserState(String state,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.updateUserState(state,userID);
        });
    }

    void  updateUserPhone(String phone,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.updateUserPhone(phone,userID);
        });
    }

    void  updateUserEmail(String email,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.updateUserEmail(email,userID);
        });
    }

    void  updateUserPassword(String password,int userID){
        CovidHelperDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.updateUserPassword(password,userID);
        });
    }
}
