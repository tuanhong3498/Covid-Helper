package com.example.covidhelper.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.CovidTestsConducted;

import java.util.List;

@Dao
public interface CovidTestsConductedDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CovidTestsConducted covidTestsConducted);

    @Query("SELECT * FROM CovidTestsConducted ORDER BY date DESC LIMIT :days")
    LiveData<List<CovidTestsConducted>> getRecentTestData(int days);
}
