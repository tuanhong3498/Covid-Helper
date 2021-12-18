package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.covidhelper.database.table.CheckInRecord;
import com.example.covidhelper.database.table.CheckInRecordDetails;

import java.util.List;

@Dao
public interface CheckInRecordDetailsDAO
{
    @Query("SELECT CheckInRecord.recordID,CheckInRecord.userID,CheckInRecord.recordDate,CheckInPlace.recordPlace, CheckInPlace.recordAddress,CheckInRecord.recordTime from CheckInRecord, CheckInPlace WHERE CheckInRecord.placeID=CheckInPlace.placeID AND CheckInRecord.userID=:userID AND CheckInRecord.recordTime IN (SELECT MAX(CheckInRecord.recordTime) FROM CheckInRecord);")
    LiveData<CheckInRecordDetails> getLatestCheckIn(int userID);

    @Query("SELECT CheckInRecord.recordID,CheckInRecord.userID,CheckInRecord.recordDate,CheckInPlace.recordPlace, CheckInPlace.recordAddress,CheckInRecord.recordTime from CheckInRecord, CheckInPlace WHERE CheckInRecord.placeID=CheckInPlace.placeID AND CheckInRecord.userID=:userID AND CheckInRecord.recordDate = :recordDate ORDER BY CheckInRecord.recordDate*100000000+CheckInRecord.recordTime DESC")
    LiveData<List<CheckInRecordDetails>> getDailyCheckInDate(int userID, String recordDate);
}
