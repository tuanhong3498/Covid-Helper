package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.EmergencyHotline;
import com.example.covidhelper.database.table.SOP;

@Dao
public interface EmergencyHotlineDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EmergencyHotline emergencyHotline);

    @Query("SELECT hotlineNumber FROM EmergencyHotline WHERE state = :state")
    String getHotlineNumber(String state);
}
