package com.example.covidhelper.ui.checkIn;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.CheckInRecordDAO;
import com.example.covidhelper.database.table.CheckInRecord;

import java.util.List;

public class CheckInRecordRepository
{
    private CheckInRecordDAO checkInRecordDAO;

    CheckInRecordRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        checkInRecordDAO = covidHelperDatabase.getCheckInRecordDAO();
    }

    // Room executes all queries on a separate thread.

    void insertCheckInDate(CheckInRecord checkInRecord){
        new Thread(() -> {
            checkInRecordDAO.insert(checkInRecord);
        }).start();
    }

    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<CheckInRecord>> getCheckInDate(int userID) {
        return checkInRecordDAO.getCheckInDate(userID);
    }

    LiveData<List<CheckInRecord>> getDailyCheckInDate(int userID, int recordDate) {
        return checkInRecordDAO.getDailyCheckInDate(userID, recordDate);
    }

    LiveData<List<CheckInRecord>> getLatestCheckIn(int userID){
        return checkInRecordDAO.getLatestCheckIn(userID);
    }
}
