package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.DailyNewDeaths;

import java.util.List;

@Dao
public interface DailyNewDeathsDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DailyNewDeaths dailyNewDeaths);

    @Query("SELECT * FROM DailyNewDeaths ORDER BY date DESC LIMIT :days")
    LiveData<List<DailyNewDeaths>> getRecentDeaths(int days);
}
