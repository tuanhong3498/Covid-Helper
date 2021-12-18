package com.example.covidhelper.ui.checkIn;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.CheckInRecordDetails;
import com.example.covidhelper.database.table.User;

import java.util.List;

public class CheckInViewModel extends AndroidViewModel
{
    private final CheckInRecordRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    public CheckInViewModel(Application application) {
        super(application);
        mRepository = new CheckInRecordRepository(application);
    }

    void insertCheckInDate(CheckInRecord checkInRecord){
        mRepository.insertCheckInDate(checkInRecord);
    }

    LiveData<List<User>> getUserInfo(int userID) {
        return mRepository.getUserInfo(userID);
    }

    LiveData<List<CheckInRecord>> getCheckInDate(int userID) {
        return mRepository.getCheckInDate(userID);
    }

    LiveData<List<CheckInRecordDetails>> getDailyCheckInDate(int userID, String recordDate) {
        return mRepository.getDailyCheckInDate(userID, recordDate);
    }

    LiveData<CheckInRecordDetails> getLatestCheckIn(int userID){
        return mRepository.getLatestCheckIn(userID);
    }

    LiveData<Integer> getCheckInPlaceID(String recordPlace, String recordAddress){
        return mRepository.getCheckInPlaceID(recordPlace, recordAddress);
    }
}
