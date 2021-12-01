package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.DailyNewCases;

@Dao
public interface DailyNewCasesDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DailyNewCases dailyNewCases);
}
