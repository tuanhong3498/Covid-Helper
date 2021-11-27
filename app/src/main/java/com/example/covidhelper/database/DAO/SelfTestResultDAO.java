package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.SelfTestResult;

public interface SelfTestResultDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SelfTestResult selfTestResult);
}
