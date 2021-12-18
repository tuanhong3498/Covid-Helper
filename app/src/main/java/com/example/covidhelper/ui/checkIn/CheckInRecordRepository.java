package com.example.covidhelper.ui.checkIn;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.CovidHelperDatabase;
import com.example.covidhelper.database.DAO.CheckInPlaceDAO;
import com.example.covidhelper.database.DAO.CheckInRecordDAO;
import com.example.covidhelper.database.DAO.CheckInRecordDetailsDAO;
import com.example.covidhelper.database.DAO.UserDAO;
import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.CheckInRecordDetails;
import com.example.covidhelper.database.table.User;

import java.util.List;

public class CheckInRecordRepository
{
    private final CheckInRecordDAO checkInRecordDAO;
    private final CheckInRecordDetailsDAO checkInRecordDetailsDAO;
    private final CheckInPlaceDAO checkInPlaceDAO;
    private final UserDAO userDAO;

    CheckInRecordRepository(Application application) {
        CovidHelperDatabase covidHelperDatabase = CovidHelperDatabase.getDatabase(application);
        checkInRecordDAO = covidHelperDatabase.getCheckInRecordDAO();
        checkInRecordDetailsDAO = covidHelperDatabase.getCheckInRecordDetailsDAO();
        checkInPlaceDAO = covidHelperDatabase.getCheckPlaceDAO();

        userDAO = covidHelperDatabase.getUserDAO();
    }

    // Room executes all queries on a separate thread.
    void insertCheckInDate(CheckInRecord checkInRecord){
        new Thread(() -> checkInRecordDAO.insert(checkInRecord)).start();
    }

    LiveData<List<User>> getUserInfo(int userID) {
        return userDAO.getUserInfo(userID);
    }

    LiveData<List<CheckInRecord>> getCheckInDate(int userID) {
        return checkInRecordDAO.getCheckInDate(userID);
    }

    LiveData<List<CheckInRecordDetails>> getDailyCheckInDate(int userID, String recordDate) {
        return checkInRecordDetailsDAO.getDailyCheckInDate(userID, recordDate);
    }

    LiveData<CheckInRecordDetails> getLatestCheckIn(int userID){
        return checkInRecordDetailsDAO.getLatestCheckIn(userID);
    }

    LiveData<Integer> getCheckInPlaceID(String recordPlace, String recordAddress){
        return checkInPlaceDAO.getCheckInPlaceID(recordPlace, recordAddress);
    }
}
