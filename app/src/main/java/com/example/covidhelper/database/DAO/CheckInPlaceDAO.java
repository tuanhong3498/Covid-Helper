package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.CheckInPlace;
import com.example.covidhelper.database.table.CheckInRecord;

import java.util.List;

@Dao
public interface CheckInPlaceDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CheckInPlace checkInPlace);

    @Query("SELECT placeID FROM CheckInPlace WHERE recordPlace=:recordPlace AND recordAddress=:recordAddress")
    LiveData<Integer> getCheckInPlaceID(String recordPlace, String recordAddress);
}
