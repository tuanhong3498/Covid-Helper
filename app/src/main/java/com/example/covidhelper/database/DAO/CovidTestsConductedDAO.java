package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.CovidTestsConducted;

@Dao
public interface CovidTestsConductedDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CovidTestsConducted covidTestsConducted);
}
