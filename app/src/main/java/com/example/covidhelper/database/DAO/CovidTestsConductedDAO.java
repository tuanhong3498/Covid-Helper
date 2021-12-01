package com.example.covidhelper.database.DAO;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.CovidTestsConducted;

public interface CovidTestsConductedDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CovidTestsConducted covidTestsConducted);
}
