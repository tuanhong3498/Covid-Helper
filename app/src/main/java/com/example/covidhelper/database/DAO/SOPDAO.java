package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.SOP;

@Dao
public interface SOPDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SOP sop);

    @Query("SELECT phaseType from SOP where livingState = :state")
    String getSOPphase(String state);
}
