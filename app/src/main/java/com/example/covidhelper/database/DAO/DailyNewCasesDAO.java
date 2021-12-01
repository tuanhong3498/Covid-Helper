package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.DailyNewCases;

public interface DailyNewCasesDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DailyNewCases dailyNewCases);
}
