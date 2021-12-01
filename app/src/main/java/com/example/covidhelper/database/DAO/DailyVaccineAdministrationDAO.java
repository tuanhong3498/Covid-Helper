package com.example.covidhelper.database.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.covidhelper.database.table.DailyVaccineAdministration;

@Dao
public interface DailyVaccineAdministrationDAO
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DailyVaccineAdministration dailyVaccineAdministration);
}
