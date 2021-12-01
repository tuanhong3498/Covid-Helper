package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.DailyNewDeaths;

@Dao
public interface DailyNewDeathsDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DailyNewDeaths dailyNewDeaths);
}
