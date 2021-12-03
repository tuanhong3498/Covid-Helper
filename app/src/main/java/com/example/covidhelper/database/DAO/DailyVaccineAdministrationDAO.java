package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.covidhelper.database.table.DailyVaccineAdministration;

@Dao
public interface DailyVaccineAdministrationDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DailyVaccineAdministration dailyVaccineAdministration);

    @Query("SELECT * FROM DailyVaccineAdministration ORDER BY date DESC LIMIT :days")
    DailyVaccineAdministration[] getRecentVacData(int days);
}
