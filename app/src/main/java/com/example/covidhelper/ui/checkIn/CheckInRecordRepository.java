package com.example.covidhelper.ui.checkIn;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.CheckInRecordDAO;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.User;

import java.util.List;

public class CheckInRecordRepository
{
    private CheckInRecordDAO checkInRecordDAO;
    private final UserDAO userDAO;

    CheckInRecordRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        checkInRecordDAO = covidHelperDatabase.getCheckInRecordDAO();
        userDAO = covidHelperDatabase.getUserDAO();
    }

    // Room executes all queries on a separate thread.
    void insertCheckInDate(CheckInRecord checkInRecord){
        new Thread(() -> {
            checkInRecordDAO.insert(checkInRecord);
        }).start();
    }

    LiveData<List<User>> getUserInfo(int userID) {
        return userDAO.getUserInfo(userID);
    }

    LiveData<List<CheckInRecord>> getCheckInDate(int userID) {
        return checkInRecordDAO.getCheckInDate(userID);
    }

    LiveData<List<CheckInRecord>> getDailyCheckInDate(int userID, String recordDate) {
        return checkInRecordDAO.getDailyCheckInDate(userID, recordDate);
    }

    LiveData<List<CheckInRecord>> getLatestCheckIn(int userID){
        return checkInRecordDAO.getLatestCheckIn(userID);
    }
}
